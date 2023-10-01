package com.matheusksn.devspaceapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.entities.Lab;
import com.matheusksn.devspaceapi.services.LabService;

@RestController
@RequestMapping("api/labs")
public class LabController {

    @Autowired
    private LabService labService;

    @GetMapping
    public ResponseEntity<List<Lab>> getAllLabs() {
        List<Lab> labs = labService.getAllLabs();
        return new ResponseEntity<>(labs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lab> getLabById(@PathVariable Long id) {
        Optional<Lab> lab = labService.getLabById(id);
        return lab.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Lab> createLab(@RequestBody Lab lab) {
        Lab createdLab = labService.createLab(lab);
        return new ResponseEntity<>(createdLab, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lab> updateLab(@PathVariable Long id, @RequestBody Lab lab) {
        Lab updatedLab = labService.updateLab(id, lab);
        return updatedLab != null ?
                new ResponseEntity<>(updatedLab, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLab(@PathVariable Long id) {
        labService.deleteLab(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
