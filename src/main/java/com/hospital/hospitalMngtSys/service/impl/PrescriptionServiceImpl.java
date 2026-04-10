package com.hospital.hospitalMngtSys.service.impl;

import com.hospital.hospitalMngtSys.entity.Prescription;
import com.hospital.hospitalMngtSys.repository.PrescriptionRepository;
import com.hospital.hospitalMngtSys.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private PrescriptionRepository prescriptionRepository;
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    //create a prescription
    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    //get prescription by id
    @Override
    public Prescription getPrescriptionById(Long id){
        return prescriptionRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Prescription not found!!"));
    }

    //get all prescriptions
    @Override
    public List<Prescription> getAllPrescription(){
        return prescriptionRepository.findAll();
    }

    //get prescription by appointmentId
    @Override
    public Prescription getPrescriptionByAppointmentId(Long appointmentId){
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }

    //update the prescription
    @Override
    public Prescription updatePrescription(Long id,Prescription prescription){
        Prescription updatedPrescription = prescriptionRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Prescription not found!!"));
        if(prescription.getMedicine()!=null){
            updatedPrescription.setMedicine(prescription.getMedicine());
        }
        if(prescription.getAppointment()!=null){
            updatedPrescription.setAppointment(prescription.getAppointment());
        }
        if(prescription.getNotes()!=null){
            updatedPrescription.setNotes(prescription.getNotes());
        }
        if(prescription.getDosage()!=null){
            updatedPrescription.setDosage(prescription.getDosage());
        }
        return prescriptionRepository.save(updatedPrescription);
    }

    //delete prescription
    @Override
    public void deletePrescriptionById(Long id){
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Prescription not found!!!"));
        prescriptionRepository.delete(prescription);
    }

}
