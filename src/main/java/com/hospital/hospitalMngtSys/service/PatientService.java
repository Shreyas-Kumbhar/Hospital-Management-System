package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Patient;
import com.hospital.hospitalMngtSys.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface PatientService {

    public Patient createPatient(Patient patient);

    public List<Patient> getAllPatients();

    public Patient getPatientById(Long id);

    public List<Patient> getPatientByName(String name);

    public List<Patient> getPatientByDisease(String disease);

    public Patient updatePatient(Long id, Patient patient);

    public void deletePatient(Long id);

    public void deleteAllPatients();

    public void deletePatientByName(String name);
}