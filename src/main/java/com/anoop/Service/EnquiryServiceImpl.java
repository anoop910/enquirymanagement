package com.anoop.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anoop.DTO.AddEnquiryDTO;
import com.anoop.DTO.DashboardDTO;
import com.anoop.DTO.ViewEnquiryDTO;
import com.anoop.Entity.CoursesEntity;
import com.anoop.Entity.EnquriryStatusEntity;
import com.anoop.Entity.StudentEnqEntity;
import com.anoop.Entity.UserDetailsEntity;
import com.anoop.Repository.CoursesRepo;
import com.anoop.Repository.EnquriryStatusRepo;
import com.anoop.Repository.StudentEnqRepo;
import com.anoop.Repository.UserDetailsRepo;

import jakarta.transaction.Transactional;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private StudentEnqRepo studentEnqRepo;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private CoursesRepo coursesRepo;

    @Autowired
    private EnquriryStatusRepo enquriryStatusRepo;

    @Override
    public DashboardDTO getDashboardData(Integer userId) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepo.findById(userId);
        List<StudentEnqEntity> studentEnqEntities = userDetailsEntity.get().getStudentEnqEntity();

        DashboardDTO dashboardDTO = new DashboardDTO();

        if (studentEnqEntities != null) {

            Integer toalEnquiry = studentEnqEntities.size();

            Integer enrolledEnquiry = studentEnqEntities.stream().filter(e -> e.getEnquiryStatus().equals("ENROLLED"))
                    .toList().size();

            Integer lostEnquiry = studentEnqEntities.stream().filter(e -> (e.getEnquiryStatus().contains("LOST")))
                    .toList().size();

            dashboardDTO.setTotalEnquiryCont(toalEnquiry);
            dashboardDTO.setEnrolledEnqCont(enrolledEnquiry);
            dashboardDTO.setLostEnqCont(lostEnquiry);
        }
        return dashboardDTO;

    }

    @Override
    public List<CoursesEntity> getCourses() {
        return coursesRepo.findAll();

    }

    @Override
    public List<EnquriryStatusEntity> getStatus() {
        return enquriryStatusRepo.findAll();

    }

    @Override
    public List<ViewEnquiryDTO> getEnquiries(Integer userId) {

        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepo.findById(userId);
        List<ViewEnquiryDTO> viewEnquiryDTOs = new ArrayList<>();
        if (userDetailsEntity != null) {
            List<StudentEnqEntity> studentEnqEntities = userDetailsEntity.get().getStudentEnqEntity();
            studentEnqEntities.forEach(e -> {
                ViewEnquiryDTO viewEnquiryDTO = new ViewEnquiryDTO();
                viewEnquiryDTO.setClassMode(e.getClassMode());
                viewEnquiryDTO.setCourse(e.getCourse());
                viewEnquiryDTO.setEnquiryStatus(e.getEnquiryStatus());
                viewEnquiryDTO.setPhone(e.getPhone());
                viewEnquiryDTO.setStudentName(e.getStudentName());
                viewEnquiryDTOs.add(viewEnquiryDTO);
            });
        }
        return viewEnquiryDTOs;
    }

    @Override
    public ViewEnquiryDTO getEnquiry(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEnquiry'");
    }

    @Override
    @Transactional
    public String addEnquriy(AddEnquiryDTO addEnquiryDTO, Integer userId) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepo.findById(userId);
        StudentEnqEntity studentEnqEntity = new StudentEnqEntity();
        if (userDetailsEntity.get() == null) {
            return "User Not Found";
        }

        BeanUtils.copyProperties(addEnquiryDTO, studentEnqEntity);
        studentEnqEntity.setCreatedData(LocalDate.now());
        studentEnqEntity.setUserDetailsEntity(userDetailsEntity.get());
        studentEnqRepo.save(studentEnqEntity);
        return "Enquiry add Successfully !";

    }

}
