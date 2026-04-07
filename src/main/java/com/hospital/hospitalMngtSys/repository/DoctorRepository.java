package com.hospital.hospitalMngtSys.repository;

import com.hospital.hospitalMngtSys.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    //custom query in Spring Data JPA
    void deleteByName(String name);
    List<Doctor> findByNameIgnoreCase(String name);
    List<Doctor> findBySpecializationIgnoreCase(String specialization);
}
