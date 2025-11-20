package com.anoop.Service;

import java.util.List;

import com.anoop.DTO.AddEnquiryDTO;
import com.anoop.DTO.DashboardDTO;
import com.anoop.DTO.ViewEnquiryDTO;
import com.anoop.Entity.CoursesEntity;
import com.anoop.Entity.EnquriryStatusEntity;

public interface EnquiryService {

    public DashboardDTO getDashboardData(Integer userId);

    public List<CoursesEntity> getCourses();

    public List<EnquriryStatusEntity> getStatus();

    public List<ViewEnquiryDTO> getEnquiries(Integer userId);

    public ViewEnquiryDTO getEnquiry(Integer userId);

    public String addEnquriy(AddEnquiryDTO addEnquiryDTO, Integer userId);
}
