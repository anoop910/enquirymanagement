package com.anoop.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentEnqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String studentName;
    private String phone;
    private String classMode;
    private String course;
    private String enquiryStatus;
    private LocalDate createdData;
    private LocalDate updatedData;

    @ManyToOne
    private UserDetailsEntity userDetailsEntity;
}
