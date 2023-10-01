package com.matheusksn.devspaceapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusksn.devspaceapi.entities.Lab;

public interface LabRepository extends JpaRepository<Lab, Long> {
    boolean existsByNameAndFloor(String name, int floor);
    List<Lab> findByIsActive(boolean isActive);

}
