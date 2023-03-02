package kz.web.spring.weatherSensor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {

    @Min(value = -100) @Max(value = 100) @NotNull(message = "Measurement value should not be null!")
    private Double value;

    @Column(name = "raining")
    @NotNull(message = "Measurement raining should not be null!")
    private Boolean raining;

    @NotNull(message = "Sensor must exist!")
    private SensorDTO sensor;

}
