package ru.slimercorp.springcourse.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.slimercorp.springcourse.dto.SensorDTO;
import ru.slimercorp.springcourse.models.Sensor;
import ru.slimercorp.springcourse.services.SensorService;
import ru.slimercorp.springcourse.util.SensorErrorResponse;
import ru.slimercorp.springcourse.util.SensorExistException;
import ru.slimercorp.springcourse.util.SensorNotRegisteredException;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Neil Alishev
 */
@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fe : errors) {
                errorMsg.append(fe.getField())
                        .append(" - ").append(fe.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotRegisteredException(errorMsg.toString());
        }

        if (sensorService.checkSensorExist(sensorDTO.getName())) {
            throw new SensorExistException();
        }

        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);

        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotRegisteredException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorExistException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor already exist",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
