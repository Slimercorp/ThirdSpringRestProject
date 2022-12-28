package ru.slimercorp.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slimercorp.springcourse.models.Sensor;
import ru.slimercorp.springcourse.repositories.SensorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).stream().findFirst().orElse(null);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public boolean checkSensorExist(String name) {
        List<Sensor> sensors = sensorRepository.findByName(name);
        if (sensors.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
