package kz.web.spring.weatherSensor.util.exceptions.measurement;

public class MeasurementNotValidException extends RuntimeException {
    public MeasurementNotValidException(String message) {
        super(message);
    }
}
