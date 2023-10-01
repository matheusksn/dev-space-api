package com.matheusksn.devspaceapi.dtos;

import java.util.Date;
import java.util.List;

import com.matheusksn.devspaceapi.entities.Lab;

public record AvailableLabDTO(Lab lab, List<Date> availableTimes) {

}
