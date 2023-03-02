package kz.web.spring.weatherSensor.util.validators.measurement;

import kz.web.spring.weatherSensor.models.Measurement;
import kz.web.spring.weatherSensor.models.Sensor;
import kz.web.spring.weatherSensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        Optional<Sensor> sensorOptional = sensorService.get(measurement.getSensor().getName());
        if (sensorOptional.isEmpty()) {
            errors.rejectValue("sensor", "", "No such sensor exists!");
        }
    }
}
