package kz.web.spring.weatherSensor.util.responces.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorErrorResponse {
    private String message;
    private long timestamp;
}
