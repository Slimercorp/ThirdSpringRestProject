package ru.slimercorp.springcourse.util;

public class SensorNotRegisteredException extends RuntimeException{
    public SensorNotRegisteredException(String msg) {
        super(msg);
    }
}
