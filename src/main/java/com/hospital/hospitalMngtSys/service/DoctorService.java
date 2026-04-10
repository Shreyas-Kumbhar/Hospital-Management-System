package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Doctor;
import com.hospital.hospitalMngtSys.repository.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    //create doctor
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    //get all doctors
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    //get doctor by id
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!"));
    }

    //get doctor by name
    public List<Doctor> getDoctorsByName(String name) {
        List<Doctor> doctors= doctorRepository.findByNameIgnoreCase(name);
        if(doctors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!!");
        }
        return doctors;
    }

    //get doctor by specialization
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> doctors =doctorRepository.findBySpecializationIgnoreCase(specialization);
        if(doctors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!!");
        }
        return doctors;
    }

    //update doctor
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor updatedDoctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!")) ;

        if(doctor.getName()!=null) {
            updatedDoctor.setName(doctor.getName());
        }
        if(doctor.getSpecialization()!=null) {
            updatedDoctor.setSpecialization(doctor.getSpecialization());
        }
        if(doctor.getPhone()!=null) {
            updatedDoctor.setPhone(doctor.getPhone());
        }
        return doctorRepository.save(updatedDoctor);
    }

    //delete doctor
    public void deleteDoctorById(Long id) {
        if(!doctorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!!");
        }
        doctorRepository.deleteById(id);
    }

    //delete all doctors
    public void deleteAllDoctors() {
        doctorRepository.deleteAll();
    }

    //delete by name
    public void deleteDoctorByName(String name) {
        List<Doctor> doctors = doctorRepository.findByNameIgnoreCase(name);
        if(doctors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found!!!");
        }
        doctorRepository.deleteByName(name);
    }

}
