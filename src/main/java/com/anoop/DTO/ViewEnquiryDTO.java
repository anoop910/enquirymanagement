package com.anoop.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ViewEnquiryDTO {
    private String studentName;
    private String phone;
    private String classMode;
    private String course;
    private String enquiryStatus;
    private LocalDate lastIntractionDataDate;
}
