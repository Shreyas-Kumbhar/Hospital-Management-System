package com.hospital.hospitalMngtSys.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String medicine;
    @NotEmpty
    private String dosage;
    @NotEmpty
    private String notes;

    @OneToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;
}
