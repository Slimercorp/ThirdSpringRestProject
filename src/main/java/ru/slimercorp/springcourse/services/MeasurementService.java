package ru.slimercorp.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slimercorp.springcourse.models.Measurement;
import ru.slimercorp.springcourse.models.Sensor;
import ru.slimercorp.springcourse.repositories.MeasurementRepository;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()));
        measurement.setTimestamp(new Date());
    }

    public int rainyDays() {
        List<Measurement> measurements = findAll();
        int counter = 0;
        for (Measurement measurement : measurements) {
            counter += measurement.getRaining() ? 1 : 0;
        }
        return counter;
    }
}
