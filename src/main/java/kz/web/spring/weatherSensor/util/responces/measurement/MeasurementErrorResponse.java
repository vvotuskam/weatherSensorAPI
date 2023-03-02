package kz.web.spring.weatherSensor.util.responces.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeasurementErrorResponse {
    private String message;
    private long timestamp;
}
