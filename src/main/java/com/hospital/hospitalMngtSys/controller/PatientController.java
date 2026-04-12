package com.hospital.hospitalMngtSys.controller;

import com.hospital.hospitalMngtSys.entity.Patient;
import com.hospital.hospitalMngtSys.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // SHOW add patient page
    @GetMapping("/add")
    public String showAddPatientPage(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    // SAVE patient
    @PostMapping
    public String savePatient(@Valid @ModelAttribute("patient") Patient patient,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            return "addPatient";
        }

        patientService.createPatient(patient);
        return "redirect:/patients/list";
    }

    // GET all patients
    @GetMapping("/list")
    public String getAllPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // GET patient by id
    @GetMapping("/{id}")
    public String getPatientById(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patient";
    }

    // SEARCH by name
    @GetMapping("/search/name/{name}")
    public String getPatientsByName(@PathVariable String name, Model model) {
        model.addAttribute("patients", patientService.getPatientByName(name));
        return "patients";
    }

    // SEARCH by disease
    @GetMapping("/search/disease/{disease}")
    public String getPatientsByDisease(@PathVariable String disease, Model model) {
        model.addAttribute("patients", patientService.getPatientByDisease(disease));
        return "patients";
    }

    // SHOW edit page
    @GetMapping("/edit/{id}")
    public String showEditPatientPage(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "editPatient";
    }

    // UPDATE patient
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id,
                                @Valid @ModelAttribute("patient") Patient patient,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            return "editPatient";
        }

        patientService.updatePatient(id, patient);
        return "redirect:/patients/list";
    }

    // DELETE by id
    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients/list";
    }

    // DELETE all patients
    @PostMapping("/deleteAll")
    public String deleteAllPatients() {
        patientService.deleteAllPatients();
        return "redirect:/patients/list";
    }

    // DELETE by name
    @PostMapping("/delete/name")
    public String deletePatientByName(@RequestParam String name) {
        patientService.deletePatientByName(name);
        return "redirect:/patients/list";
    }
}