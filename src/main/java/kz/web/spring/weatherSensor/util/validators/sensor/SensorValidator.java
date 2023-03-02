package kz.web.spring.weatherSensor.util.validators.sensor;

import kz.web.spring.weatherSensor.models.Sensor;
import kz.web.spring.weatherSensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensorToValidate = (Sensor) target;
        Optional<Sensor> sensorOptional = sensorService.get(sensorToValidate.getName());

        if (sensorOptional.isPresent()) {
            errors.rejectValue("name", "",
                    "Such sensor with name " + sensorToValidate.getName() + " already exists");
        }
    }
}
