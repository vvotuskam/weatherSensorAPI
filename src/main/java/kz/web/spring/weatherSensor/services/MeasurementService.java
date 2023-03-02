package kz.web.spring.weatherSensor.services;

import kz.web.spring.weatherSensor.dto.MeasurementDTO;
import kz.web.spring.weatherSensor.models.Measurement;
import kz.web.spring.weatherSensor.models.Sensor;
import kz.web.spring.weatherSensor.repositories.MeasurementRepository;
import kz.web.spring.weatherSensor.repositories.SensorRepository;
import kz.web.spring.weatherSensor.util.exceptions.sensor.SensorNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public int getRainyDaysCount() {
        int rainyDaysCount = getAll().stream().filter(Measurement::getRaining).toList().size();
        return rainyDaysCount;
    }

    private Sensor checkSensor(Measurement measurement) {
        Optional<Sensor> sensor = sensorRepository.findByName(measurement.getSensor().getName());
        if (sensor.isEmpty()) {
            throw new SensorNotFoundException();
        }
        return sensor.get();
    }

    @Transactional
    public boolean save(Measurement measurement) {
        Sensor sensor = checkSensor(measurement);
        measurement.setSensor(sensor);
        measurement.setMeasuredAt(LocalDateTime.now());
        measurementRepository.save(measurement);
        return true;
    }

    @Transactional
    public boolean save(MeasurementDTO measurementDTO) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        Sensor sensor = checkSensor(measurement);
        measurement.setSensor(sensor);
        measurement.setMeasuredAt(LocalDateTime.now());
        measurementRepository.save(measurement);
        return true;
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
