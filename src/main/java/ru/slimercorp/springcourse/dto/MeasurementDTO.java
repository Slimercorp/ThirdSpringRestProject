package ru.slimercorp.springcourse.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Neil Alishev
 */
public class MeasurementDTO {
    @NotNull(message = "Temperature field should not be empty")
    @Min(value = -100, message = "Temperature should be greater than -100")
    @Max(value = 100, message = "Temperature should be less than 100")
    private Double value;

    @NotNull(message = "Raining field should not be empty")
    private Boolean raining;
    @NotNull(message = "Sensor field should not be empty")
    private SensorDTO sensor;

    public MeasurementDTO() {

    }

    public MeasurementDTO(Double value, Boolean raining, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
