package kz.web.spring.weatherSensor.client;

import kz.web.spring.weatherSensor.dto.SensorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementTemplate {
    private double value;
    private boolean raining;
    private String name;

    public MeasurementTemplate(double value, boolean raining, String name) {
        this.value = value;
        this.raining = raining;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ \"value\": " + value + ", \"raining\": " + raining + ", \"sensor\": { \"name\": \"" + name + "\" }";
    }
}
