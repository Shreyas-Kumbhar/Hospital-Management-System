package com.hospital.hospitalMngtSys.repository;
import com.hospital.hospitalMngtSys.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long>
{
}