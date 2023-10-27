package com.matheusksn.devspaceapi.dtos;

public record LabReservationRequestDTO(Long labId, Long userId, String date, Long userTypeId) {
}
