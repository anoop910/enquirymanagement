package com.anoop.Runner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import com.anoop.Entity.CoursesEntity;
import com.anoop.Entity.EnquriryStatusEntity;
import com.anoop.Repository.CoursesRepo;
import com.anoop.Repository.EnquriryStatusRepo;


public class AddStatusAndCourseRunner implements ApplicationRunner {

    @Autowired
    private CoursesRepo coursesRepo;

    @Autowired
    private EnquriryStatusRepo enquriryStatusRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CoursesEntity coursesEntity1 = new CoursesEntity();
        coursesEntity1.setCourseName("JAVA");
        CoursesEntity coursesEntity2 = new CoursesEntity();
        coursesEntity2.setCourseName("PYTHON");
        CoursesEntity coursesEntity3 = new CoursesEntity();
        coursesEntity3.setCourseName("DEVOPS");


        List<CoursesEntity> coursesEntities = new ArrayList<>();
        coursesEntities.add(coursesEntity1);
        coursesEntities.add(coursesEntity2);
        coursesEntities.add(coursesEntity3);

        coursesRepo.saveAll(coursesEntities);

        EnquriryStatusEntity enquriryStatusEntity1 = new EnquriryStatusEntity();
        enquriryStatusEntity1.setStatusName("NEW");
        EnquriryStatusEntity enquriryStatusEntity2 = new EnquriryStatusEntity();
        enquriryStatusEntity2.setStatusName("ENROLLED");
        EnquriryStatusEntity enquriryStatusEntity3 = new EnquriryStatusEntity();
        enquriryStatusEntity3.setStatusName("LOST");

       List<EnquriryStatusEntity> enquriryStatusEntities = new ArrayList<>();
       enquriryStatusEntities.add(enquriryStatusEntity1);
       enquriryStatusEntities.add(enquriryStatusEntity2);
       enquriryStatusEntities.add(enquriryStatusEntity3);

        enquriryStatusRepo.saveAll(enquriryStatusEntities);



    }

}
