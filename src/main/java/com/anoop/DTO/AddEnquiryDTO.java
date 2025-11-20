package com.anoop.DTO;


import lombok.Data;

@Data
public class AddEnquiryDTO {
    private String studentName;
    private String phone;
    private String classMode;
    private String course;
    private String enquiryStatus;
}