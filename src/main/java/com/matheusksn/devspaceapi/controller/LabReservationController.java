package com.matheusksn.devspaceapi.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.dtos.AvailableLabDTO;
import com.matheusksn.devspaceapi.dtos.LabReservationRequestDTO;
import com.matheusksn.devspaceapi.entities.LabReservation;
import com.matheusksn.devspaceapi.services.LabReservationService;

@RestController
@RequestMapping("api/lab-reservations")
public class LabReservationController {

    @Autowired
    private LabReservationService labReservationService;

    @GetMapping
    public ResponseEntity<List<LabReservation>> getAllLabReservations() {
        List<LabReservation> labReservations = labReservationService.getAllLabReservations();
        return new ResponseEntity<>(labReservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabReservation> getLabReservationById(@PathVariable Long id) {
        LabReservation labReservation = labReservationService.getLabReservationById(id);
        if (labReservation != null) {
            return new ResponseEntity<>(labReservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createLabReservation(@RequestBody LabReservation labReservation) {
        String reservationStatus = labReservationService.createLabReservation(labReservation);
        if ("approved".equals(reservationStatus)) {
            return new ResponseEntity<>("Lab reservation created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Lab reservation could not be created. Please try again later.", HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/available-labs")
    public ResponseEntity<List<AvailableLabDTO>> getAvailableLabs(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
        List<AvailableLabDTO> availableLabs = labReservationService.getAvailableLabsWithAvailability(startDate, endDate);
        return new ResponseEntity<>(availableLabs, HttpStatus.OK);
    }
    
    @PostMapping("/reserve-lab")
    public String reserveLab(@RequestBody LabReservationRequestDTO request) {
        Long labId = request.labId();
        Long userId = request.userId();
        String date = request.date();
        Long userTypeId = request.userTypeId();

        // Verifica se o laboratório está disponível na data e hora especificadas
        boolean isLabAvailable = labReservationService.labStatus(labId, date);

        if (!isLabAvailable) {
            return "Lab not available at the specified date and time.";
        }

        // Verifica se o laboratório está ativo
        boolean isLabActive = checkLabActivity(labId);

        if (!isLabActive) {
            return "Lab is not active.";
        }

        if (userTypeId != null && userTypeId == 1) {
            // Se o userType for 1 (aluno), gera o boleto fictício
            String boleto = labReservationService.generateBoleto(userId);

        }

        return "Lab reservation created.";
    }

	private boolean checkLabActivity(Long labId) {
		return true;
	}

}
