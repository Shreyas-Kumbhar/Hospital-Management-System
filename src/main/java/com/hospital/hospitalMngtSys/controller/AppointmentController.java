package com.hospital.hospitalMngtSys.controller;

import com.hospital.hospitalMngtSys.entity.Appointment;
import com.hospital.hospitalMngtSys.service.AppointmentService;
import com.hospital.hospitalMngtSys.service.DoctorService;
import com.hospital.hospitalMngtSys.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService,
                                 DoctorService doctorService,
                                 PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // CREATE appointment page
    @GetMapping("/add")
    public String showAddAppointmentPage(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "addAppointment";
    }

    // ✅ SAVE appointment (FIXED)
    @PostMapping
    public String saveAppointment(@Valid @ModelAttribute("appointment") Appointment appointment,
                                  BindingResult bindingResult,
                                  @RequestParam("patient") Long patientId,
                                  @RequestParam("doctor") Long doctorId,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "addAppointment";
        }

        // 🔥 Convert ID → Object
        appointment.setPatient(patientService.getPatientById(patientId));
        appointment.setDoctor(doctorService.getDoctorById(doctorId));

        appointmentService.createAppointment(appointment);
        return "redirect:/appointment/list";
    }

    // GET appointment by id
    @GetMapping("/search/{id}")
    public String getAppointmentById(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getAppointmentById(id));
        return "appointment";
    }

    // GET all appointments
    @GetMapping("/list")
    public String getAllAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments";
    }

    // SHOW edit page
    @GetMapping("/edit/{id}")
    public String showEditAppointmentPage(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getAppointmentById(id));
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "editAppointment";
    }

    // ✅ UPDATE appointment (FIXED)
    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id,
                                    @Valid @ModelAttribute("appointment") Appointment appointment,
                                    BindingResult bindingResult,
                                    @RequestParam("patient") Long patientId,
                                    @RequestParam("doctor") Long doctorId,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "editAppointment";
        }

        // 🔥 Convert ID → Object
        appointment.setPatient(patientService.getPatientById(patientId));
        appointment.setDoctor(doctorService.getDoctorById(doctorId));

        appointmentService.updateAppointment(id, appointment);
        return "redirect:/appointment/list";
    }

    // GET by doctor id
    @GetMapping("/doctor/{id}")
    public String getAppointmentsByDoctorId(@PathVariable Long id, Model model) {
        model.addAttribute("appointments", appointmentService.getAppointmentByDoctorId(id));
        return "appointments";
    }

    // GET by patient id
    @GetMapping("/patient/{id}")
    public String getAppointmentsByPatientId(@PathVariable Long id, Model model) {
        model.addAttribute("appointments", appointmentService.getAppointmentByPatientId(id));
        return "appointments";
    }

    // DELETE appointment
    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        return "redirect:/appointment/list";
    }
}