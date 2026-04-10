package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Patient;
import com.hospital.hospitalMngtSys.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //create patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    //get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    //get patient by id
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found!!"));
    }

    //get patient by name
    public List<Patient> getPatientByName(String name) {
        List<Patient> p= patientRepository.findByNameIgnoreCase(name);
        if(p.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found!!");
        }
    return p;
    }

    //get patient by disease
    public List<Patient> getPatientByDisease(String disease) {
        List<Patient> p= patientRepository.findByDiseaseIgnoreCase(disease);
    if(p.isEmpty()) {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found!!");
    }
    return p;
    }

    //update patient
    public Patient updatePatient(Long id, Patient patient) {
        Patient updatedPatient = patientRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found!!"));

        if(patient.getName()!=null) {
            updatedPatient.setName(patient.getName());
        }
        if(patient.getAge()!= null){
            updatedPatient.setAge(patient.getAge());
        }
        if(patient.getAddress() != null) {
            updatedPatient.setAddress(patient.getAddress());
        }
        if(patient.getDisease()!=null) {
            updatedPatient.setDisease(patient.getDisease());
        }
        return patientRepository.save(updatedPatient);
    }

    //delete patient by id
    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found!!!");
        }
        patientRepository.deleteById(id);
    }

    //delete all patients
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    //delete patient by name
    public void deletePatientByName(String name) {
        List<Patient> patients= patientRepository.findByNameIgnoreCase(name);
        if(patients.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found!!!");
        }
        patientRepository.deleteByName(name);
    }
}
