package com.hospital.hospitalMngtSys.repository;

import com.hospital.hospitalMngtSys.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
}
