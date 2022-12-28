package ru.slimercorp.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slimercorp.springcourse.models.Measurement;

/**
 * @author Neil Alishev
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

}
