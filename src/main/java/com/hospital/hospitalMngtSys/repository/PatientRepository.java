package com.hospital.hospitalMngtSys.repository;

import com.hospital.hospitalMngtSys.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
