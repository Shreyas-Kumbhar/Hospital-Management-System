package com.hospital.hospitalMngtSys.service.impl;

import com.hospital.hospitalMngtSys.entity.Appointment;
import com.hospital.hospitalMngtSys.repository.AppointmentRepository;
import com.hospital.hospitalMngtSys.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentRepository appointmentRepository;
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    //create an appointment
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    //get appointment by id
    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not Found"));
    }

    //get all appointments
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    //update appointement by id
    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment updatedAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not Found"));

        if (appointment.getDate() != null) {
            updatedAppointment.setDate(appointment.getDate());
        }
        if (appointment.getStatus() != null) {
            updatedAppointment.setStatus(appointment.getStatus());
        }
        if (appointment.getPatient() != null) {
            updatedAppointment.setPatient(appointment.getPatient());
        }
        if (appointment.getDoctor() != null) {
            updatedAppointment.setDoctor(appointment.getDoctor());
        }
    return appointmentRepository.save(updatedAppointment);
    }
    //get appointment by doctorId
    @Override
    public List<Appointment> getAppointmentByDoctorId(Long doctorId){
            return appointmentRepository.findByDoctorId(doctorId);
        }
        //get patient by patientId
    @Override
    public List<Appointment> getAppointmentByPatientId(Long patientId){
            return appointmentRepository.findByPatientId(patientId);
        }

        //delete appointment by id
    @Override
    public void deleteAppointmentById(Long id) {
        Appointment appointment =appointmentRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not Found"));
        appointmentRepository.delete(appointment);
    }
    }

