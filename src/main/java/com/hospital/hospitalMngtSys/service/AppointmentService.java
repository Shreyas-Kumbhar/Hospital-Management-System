package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Appointment;

import java.util.List;

public interface AppointmentService {
Appointment createAppointment(Appointment appointment);
Appointment getAppointmentById(Long id);
List<Appointment> getAllAppointments();
Appointment updateAppointment(Long id,Appointment appointment);
List<Appointment> getAppointmentByDoctorId(Long doctorId);
List<Appointment> getAppointmentByPatientId(Long patientId);
void deleteAppointmentById(Long id);
}
