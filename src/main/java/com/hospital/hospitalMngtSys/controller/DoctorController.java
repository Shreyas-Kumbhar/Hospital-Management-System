package com.hospital.hospitalMngtSys.controller;

import com.hospital.hospitalMngtSys.entity.Doctor;
import com.hospital.hospitalMngtSys.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // SHOW add doctor page
    @GetMapping("/add")
    public String showAddDoctorPage(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "addDoctor";
    }

    // SAVE new doctor
    @PostMapping
    public String saveDoctor(@Valid @ModelAttribute("doctor") Doctor doctor,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctor);
            return "addDoctor";
        }

        doctorService.createDoctor(doctor);
        return "redirect:/doctors/list";
    }

    // LIST all doctors
    @GetMapping("/list")
    public String getAllDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getDoctors());
        return "doctors";
    }

    // GET doctor by id
    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "doctor";
    }

    // SEARCH by name
    @GetMapping("/name/{name}")
    public String getDoctorsByName(@PathVariable String name, Model model) {
        model.addAttribute("doctors", doctorService.getDoctorsByName(name));
        return "doctors";
    }

    // SEARCH by specialization
    @GetMapping("/specialization/{specialization}")
    public String getDoctorsBySpecialization(@PathVariable String specialization, Model model) {
        model.addAttribute("doctors",
                doctorService.getDoctorsBySpecialization(specialization));
        return "doctors";
    }

    // SHOW edit page
    @GetMapping("/edit/{id}")
    public String showEditDoctorPage(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "editDoctor";
    }

    // UPDATE doctor
    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id,
                               @ModelAttribute Doctor doctor) {

        doctorService.updateDoctor(id, doctor);
        return "redirect:/doctors/list";
    }

    // DELETE doctor by id
    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return "redirect:/doctors/list";
    }

    // DELETE doctor by name
    @PostMapping("/delete/name")
    public String deleteDoctorByName(@RequestParam String name) {
        doctorService.deleteDoctorByName(name);
        return "redirect:/doctors/list";
    }

    // DELETE all doctors
    @PostMapping("/deleteAll")
    public String deleteAllDoctors() {
        doctorService.deleteAllDoctors();
        return "redirect:/doctors/list";
    }
}