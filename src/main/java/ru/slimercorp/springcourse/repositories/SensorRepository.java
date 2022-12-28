package ru.slimercorp.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slimercorp.springcourse.models.Sensor;

import java.util.List;

/**
 * @author Neil Alishev
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findByName(String name);
}
