package com.matheusksn.devspaceapi.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.dtos.AvailableLabDTO;
import com.matheusksn.devspaceapi.entities.Lab;
import com.matheusksn.devspaceapi.entities.LabReservation;
import com.matheusksn.devspaceapi.repositories.LabRepository;
import com.matheusksn.devspaceapi.repositories.LabReservationRepository;

@Service
public class LabReservationService {

    @Autowired
    LabReservationRepository labReservationRepository;

    @Autowired
    LabService labService;
    
    @Autowired
    LabRepository labRepository;


    public List<LabReservation> getAllLabReservations() {
        return labReservationRepository.findAll();
    }

    public LabReservation getLabReservationById(Long id) {
        return labReservationRepository.findById(id).orElse(null);
    }

    public String createLabReservation(LabReservation labReservation) {
        if (!isLabAvailable(labReservation.getLab(), labReservation.getDate())) {
            return "Lab not available at the specified date and time.";
        }

        if (!labReservation.getLab().isActive()) {
            return "Lab is not active.";
        }

        String paymentStatus = processPayment(labReservation.getUser().getId(), labReservation.getDate());

        if ("approved".equals(paymentStatus)) {
            labReservation.setPaymentStatus(paymentStatus);
            labReservationRepository.save(labReservation);
        }

        return paymentStatus;
    }

    private boolean isLabAvailable(Lab lab, Date date) {
       
        return true; 
    }

    private String processPayment(Long userId, Date date) {
        
        return "approved"; 
    }
    
    public List<AvailableLabDTO> getAvailableLabsWithAvailability(Date startDate, Date endDate) {
        List<Lab> allLabs = labRepository.findByIsActive(true);
        List<AvailableLabDTO> labsWithAvailability = new ArrayList<>();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 0);

        for (Lab lab : allLabs) {
            boolean isLabAvailable = true;
            List<Date> availableTimes = new ArrayList<>();

            List<LabReservation> overlappingReservations = labReservationRepository.findOverlappingReservationsForLab(lab.getId(), startDate, endDate);

            if (!overlappingReservations.isEmpty()) {
                isLabAvailable = false;
            }

            Calendar reservationStart = Calendar.getInstance();
            reservationStart.setTime(startDate);

            Calendar reservationEnd = Calendar.getInstance();
            reservationEnd.setTime(endDate);

            if (reservationStart.before(startCalendar) || reservationEnd.after(endCalendar)) {
                isLabAvailable = false;
            }

            if (isLabAvailable) {
                AvailableLabDTO labWithAvailability = new AvailableLabDTO(lab, availableTimes);
                labsWithAvailability.add(labWithAvailability);
            }
        }
        return labsWithAvailability;
    }
}
