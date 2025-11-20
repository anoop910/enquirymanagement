package com.anoop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoop.Entity.StudentEnqEntity;
import com.anoop.Entity.UserDetailsEntity;

import java.util.List;


@Repository
public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {
    
    List<StudentEnqEntity> findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);
}
