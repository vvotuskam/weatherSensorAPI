package kz.web.spring.weatherSensor.controllers;

import jakarta.validation.Valid;
import kz.web.spring.weatherSensor.dto.MeasurementDTO;
import kz.web.spring.weatherSensor.models.Measurement;
import kz.web.spring.weatherSensor.services.MeasurementService;
import kz.web.spring.weatherSensor.util.exceptions.measurement.MeasurementNotValidException;
import kz.web.spring.weatherSensor.util.exceptions.sensor.SensorNotFoundException;
import kz.web.spring.weatherSensor.util.responces.measurement.MeasurementErrorResponse;
import kz.web.spring.weatherSensor.util.responces.measurement.RainyDaysResponse;
import kz.web.spring.weatherSensor.util.validators.measurement.MeasurementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> getAll() {
        return measurementService.getAll().stream().map(measurementService::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public RainyDaysResponse getRainyDaysCount() {
        return new RainyDaysResponse(measurementService.getRainyDaysCount());
    }

    @GetMapping("/rainy")
    public List<MeasurementDTO> getRainyMeasurements() {
        return getAll().stream().filter(MeasurementDTO::getRaining).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult result) {

        measurementValidator.validate(measurementService.convertToMeasurement(measurementDTO), result);

        if (result.hasErrors()) {
            StringBuilder message = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";\n");
            }
            throw new MeasurementNotValidException(message.toString());
        }
        measurementService.save(measurementDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotValidException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(SensorNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Such sensor does not exist!", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
