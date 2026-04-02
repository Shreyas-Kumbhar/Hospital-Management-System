package com.hospital.hospitalMngtSys.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="Name is required!!")
    private String name;
    @NonNull
    private int age;
    @NotEmpty(message="Address is required!!")
    private String address;
    @Email(message="Enter a valid email!!")
    private String email;
    @NotEmpty(message = "Address is required !")
    private String disease;
}
