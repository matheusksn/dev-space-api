package com.matheusksn.devspaceapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.entities.Lab;
import com.matheusksn.devspaceapi.repositories.LabRepository;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    public List<Lab> getAllLabs() {
        return labRepository.findAll();
    }

    public Optional<Lab> getLabById(Long id) {
        return labRepository.findById(id);
    }

    public Lab createLab(Lab lab) {
        if (labRepository.existsByNameAndFloor(lab.getName(), lab.getFloor())) {
            throw new RuntimeException("Já existe um LAB cadastrado no mesmo andar com o mesmo name.");
        }
        return labRepository.save(lab);
    }

    public Lab updateLab(Long id, Lab lab) {
        if (labRepository.existsById(id)) {
            lab.setId(id);
            return labRepository.save(lab);
        }
        return null; 
    }

    public void deleteLab(Long id) {
        labRepository.deleteById(id);
    }
}
