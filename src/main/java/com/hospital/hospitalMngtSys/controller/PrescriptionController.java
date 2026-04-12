package com.hospital.hospitalMngtSys.controller;

import com.hospital.hospitalMngtSys.entity.Appointment;
import com.hospital.hospitalMngtSys.entity.Prescription;
import com.hospital.hospitalMngtSys.service.AppointmentService;
import com.hospital.hospitalMngtSys.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AppointmentService appointmentService;

    public PrescriptionController(PrescriptionService prescriptionService,
                                  AppointmentService appointmentService) {
        this.prescriptionService = prescriptionService;
        this.appointmentService = appointmentService;
    }

    // =========================
    // ADD PAGE
    // =========================
    @GetMapping("/add")
    public String showAddPrescriptionPage(Model model) {

        List<Appointment> validAppointments = appointmentService.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() != null && a.getDoctor() != null)
                .collect(Collectors.toList());

        model.addAttribute("appointments", validAppointments);

        return "addPrescription";
    }

    // =========================
    // SAVE PRESCRIPTION (FIXED)
    // =========================
    @PostMapping
    public String savePrescription(@RequestParam String medicine,
                                   @RequestParam String dosage,
                                   @RequestParam String notes,
                                   @RequestParam(required = false) Long appointment,
                                   Model model) {

        List<Appointment> validAppointments = appointmentService.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() != null && a.getDoctor() != null)
                .collect(Collectors.toList());

        if (appointment == null) {
            model.addAttribute("error", "Please select an appointment");
            model.addAttribute("appointments", validAppointments);
            return "addPrescription";
        }

        Appointment appt;
        try {
            appt = appointmentService.getAppointmentById(appointment);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid appointment selected");
            model.addAttribute("appointments", validAppointments);
            return "addPrescription";
        }

        Prescription p = new Prescription();
        p.setMedicine(medicine);
        p.setDosage(dosage);
        p.setNotes(notes);
        p.setAppointment(appt);

        prescriptionService.createPrescription(p);

        return "redirect:/prescription/list";
    }

    // =========================
    // LIST
    // =========================
    @GetMapping("/list")
    public String getAllPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllPrescription());
        return "prescriptions";
    }

    // =========================
    // VIEW
    // =========================
    @GetMapping("/search/{id}")
    public String getPrescriptionById(@PathVariable Long id, Model model) {
        model.addAttribute("prescription",
                prescriptionService.getPrescriptionById(id));
        return "prescription";
    }

    // =========================
    // EDIT PAGE
    // =========================
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {

        List<Appointment> validAppointments = appointmentService.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() != null && a.getDoctor() != null)
                .collect(Collectors.toList());

        model.addAttribute("prescription",
                prescriptionService.getPrescriptionById(id));

        model.addAttribute("appointments", validAppointments);

        return "editPrescription";
    }

    // =========================
    // UPDATE (SAFE)
    // =========================
    @PostMapping("/edit/{id}")
    public String updatePrescription(@PathVariable Long id,
                                     @RequestParam String medicine,
                                     @RequestParam String dosage,
                                     @RequestParam String notes,
                                     @RequestParam(required = false) Long appointment,
                                     Model model) {

        List<Appointment> validAppointments = appointmentService.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() != null && a.getDoctor() != null)
                .collect(Collectors.toList());

        if (appointment == null) {
            model.addAttribute("error", "Please select an appointment");
            model.addAttribute("appointments", validAppointments);
            return "editPrescription";
        }

        Appointment appt;
        try {
            appt = appointmentService.getAppointmentById(appointment);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid appointment selected");
            model.addAttribute("appointments", validAppointments);
            return "editPrescription";
        }

        Prescription p = new Prescription();
        p.setMedicine(medicine);
        p.setDosage(dosage);
        p.setNotes(notes);
        p.setAppointment(appt);

        prescriptionService.updatePrescription(id, p);

        return "redirect:/prescription/list";
    }

    // =========================
    // DELETE
    // =========================
    @PostMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescriptionById(id);
        return "redirect:/prescription/list";
    }
}