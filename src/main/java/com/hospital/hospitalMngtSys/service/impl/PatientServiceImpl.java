package com.hospital.hospitalMngtSys.service.impl;

import com.hospital.hospitalMngtSys.entity.Patient;
import com.hospital.hospitalMngtSys.repository.PatientRepository;
import com.hospital.hospitalMngtSys.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

        private final PatientRepository patientRepository;
        public PatientServiceImpl(PatientRepository patientRepository) {
            this.patientRepository = patientRepository;
        }

        //create patient
        @Override
        public Patient createPatient(Patient patient) {
            return patientRepository.save(patient);
        }

        //get all patients
    @Override
    public List<Patient> getAllPatients() {
            return patientRepository.findAll();
        }

        //get patient by id
    @Override
    public Patient getPatientById(Long id) {
            return patientRepository.findById(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found"));
        }

        //get patient by name
    @Override
    public List<Patient> getPatientByName(String name) {
            List<Patient> p= patientRepository.findByNameIgnoreCase(name);
            if(p.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
            }
            return p;
        }

        //get patient by disease
    @Override
    public List<Patient> getPatientByDisease(String disease) {
            List<Patient> patients= patientRepository.findByDiseaseIgnoreCase(disease);
            if(patients.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found");
            }
            return patients;
        }

        //update patient
    @Override
    public Patient updatePatient(Long id, Patient patient) {
            Patient updatedPatient = patientRepository.findById(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found"));

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
    @Override
    public void deletePatient(Long id) {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found"));

            patientRepository.delete(patient);
        }

        //delete all patients
    @Override
    public void deleteAllPatients() {
            patientRepository.deleteAll();
        }

        //delete patient by name
    @Override
    public void deletePatientByName(String name) {
            List<Patient> patients= patientRepository.findByNameIgnoreCase(name);
            if(patients.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found");
            }
            patientRepository.deleteByName(name);
        }
    }



