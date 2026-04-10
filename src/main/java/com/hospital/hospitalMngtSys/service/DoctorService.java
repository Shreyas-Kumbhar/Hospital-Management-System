package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Doctor;


import java.util.List;

public interface DoctorService {
    public Doctor createDoctor(Doctor doctor);
    public List<Doctor> getDoctors();

    Doctor getDoctorById(Long id);
    List<Doctor> getDoctorsByName(String name);
    List<Doctor> getDoctorsBySpecialization(String specialization);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctorById(Long id);
    void deleteAllDoctors();
    void deleteDoctorByName(String name);
}
