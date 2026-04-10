package com.hospital.hospitalMngtSys.repository;

import com.hospital.hospitalMngtSys.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    void deleteByName(String name);
    List<Patient> findByNameIgnoreCase(String name);
    List<Patient> findByDiseaseIgnoreCase(String specialization);
}
