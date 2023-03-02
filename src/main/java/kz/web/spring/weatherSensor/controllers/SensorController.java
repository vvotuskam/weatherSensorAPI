package kz.web.spring.weatherSensor.controllers;

import jakarta.validation.Valid;
import kz.web.spring.weatherSensor.dto.SensorDTO;
import kz.web.spring.weatherSensor.services.SensorService;
import kz.web.spring.weatherSensor.util.exceptions.sensor.SensorNotValidException;
import kz.web.spring.weatherSensor.util.responces.sensor.SensorErrorResponse;
import kz.web.spring.weatherSensor.util.validators.sensor.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult result) {

        sensorValidator.validate(sensorService.convertToSensor(sensorDTO), result);

        if (result.hasErrors()) {
            StringBuilder message = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }
            throw new SensorNotValidException(message.toString());
        }
        sensorService.save(sensorDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotValidException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
