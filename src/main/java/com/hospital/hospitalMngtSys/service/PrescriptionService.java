package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.Prescription;

import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(Prescription prescription);
    Prescription getPrescriptionById(Long id);
    List<Prescription> getAllPrescription();
    Prescription updatePrescription(Long id,Prescription prescription);
    void  deletePrescriptionById(Long id);
    Prescription getPrescriptionByAppointmentId(Long appointmentId);
}
