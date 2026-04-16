package com.hospital.hospitalMngtSys.controller;


import com.hospital.hospitalMngtSys.service.AppointmentService;
import com.hospital.hospitalMngtSys.service.DoctorService;
import com.hospital.hospitalMngtSys.service.PatientService;
import com.hospital.hospitalMngtSys.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final PrescriptionService prescriptionService;

    public DashBoardController(DoctorService doctorService,
                               PatientService patientService,
                               AppointmentService appointmentService,
                               PrescriptionService prescriptionService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("doctorCount", doctorService.getDoctors().size());
        model.addAttribute("patientCount", patientService.getAllPatients().size());
        model.addAttribute("appointmentCount", appointmentService.getAllAppointments().size());
        model.addAttribute("prescriptionCount", prescriptionService.getAllPrescription().size());

        return "dashboard";
    }
}