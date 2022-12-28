package ru.slimercorp.springcourse.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.slimercorp.springcourse.dto.MeasurementDTO;
import ru.slimercorp.springcourse.models.Measurement;
import ru.slimercorp.springcourse.services.MeasurementService;
import ru.slimercorp.springcourse.services.SensorService;
import ru.slimercorp.springcourse.util.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fe : errors) {
                errorMsg.append(fe.getField())
                        .append(" - ").append(fe.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotAddedException(errorMsg.toString());
        }

        if (!sensorService.checkSensorExist(measurementDTO.getSensor().getName())) {
            throw new SensorDontExistException();
        }

        measurementService.save(convertToModel(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> measurements() {
        return measurementService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount() {
        return measurementService.rainyDays();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(SensorDontExistException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Sensor is not registered in system!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);

    }

    private Measurement convertToModel(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);

    }
}
