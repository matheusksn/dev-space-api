package com.matheusksn.devspaceapi.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matheusksn.devspaceapi.entities.LabReservation;

@Repository
public interface LabReservationRepository extends JpaRepository<LabReservation, Long> {
    @Query("SELECT lr FROM LabReservation lr WHERE lr.lab.id = :labId AND lr.date BETWEEN :startDate AND :endDate")
    List<LabReservation> findOverlappingReservationsForLab(@Param("labId") Long labId,
                                                          @Param("startDate") Date startDate,
                                                          @Param("endDate") Date endDate);
}
