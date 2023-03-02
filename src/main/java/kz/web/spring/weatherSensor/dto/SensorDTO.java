package kz.web.spring.weatherSensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {

    @NotEmpty(message = "Sensor name should not be empty!")
    @Size(min = 3, max = 30, message = "Sensor name length should be in range 3 and 30!")
    private String name;
}
