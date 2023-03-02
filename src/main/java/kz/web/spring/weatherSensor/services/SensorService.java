package kz.web.spring.weatherSensor.services;

import kz.web.spring.weatherSensor.dto.SensorDTO;
import kz.web.spring.weatherSensor.models.Sensor;
import kz.web.spring.weatherSensor.repositories.MeasurementRepository;
import kz.web.spring.weatherSensor.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Sensor> get(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensorRepository.save(sensor);
    }

    @Transactional
    public void save(SensorDTO sensorDTO) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensor.setCreatedAt(LocalDateTime.now());
        sensorRepository.save(sensor);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
