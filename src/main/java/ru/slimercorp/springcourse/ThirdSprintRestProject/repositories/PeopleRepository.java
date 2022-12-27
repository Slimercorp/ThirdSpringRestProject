package ru.slimercorp.springcourse.ThirdSprintRestProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slimercorp.springcourse.ThirdSprintRestProject.models.Person;

/**
 * @author Neil Alishev
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
