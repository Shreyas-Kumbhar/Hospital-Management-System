package com.hospital.hospitalMngtSys.service.impl;

import com.hospital.hospitalMngtSys.entity.Doctor;
import com.hospital.hospitalMngtSys.repository.DoctorRepository;
import com.hospital.hospitalMngtSys.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    //create doctor
    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    //get all doctors
    @Override
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    //get doctor by id
    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));
    }

    //get doctor by name
    @Override
    public List<Doctor> getDoctorsByName(String name) {
        List<Doctor> doctors= doctorRepository.findByNameIgnoreCase(name);
        if(doctors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found");
        }
        return doctors;
    }

    //get doctor by specialization
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> doctors =doctorRepository.findBySpecializationIgnoreCase(specialization);
        if(doctors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found");
        }
        return doctors;
    }

    //update doctor
    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor updatedDoctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found")) ;

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
    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        doctorRepository.delete(doctor);
    }

    //delete all doctors
    @Override
    public void deleteAllDoctors() {
        doctorRepository.deleteAll();
    }

    //delete by name
    @Override
    public void deleteDoctorByName(String name) {
        long deletedCount = doctorRepository.deleteByNameIgnoreCase(name);

        if (deletedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }
    }

}



