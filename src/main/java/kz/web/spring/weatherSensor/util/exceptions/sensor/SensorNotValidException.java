package kz.web.spring.weatherSensor.util.exceptions.sensor;

public class SensorNotValidException extends RuntimeException {
    public SensorNotValidException(String message) {
        super(message);
    }
}
