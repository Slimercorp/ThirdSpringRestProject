package ru.slimercorp.springcourse.util;

public class MeasurementNotAddedException extends RuntimeException{
    public MeasurementNotAddedException(String msg) {
        super(msg);
    }
}
