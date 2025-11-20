package com.anoop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoop.Entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer> {

    UserDetailsEntity findByEmail(String email);

    UserDetailsEntity findByEmailAndPassword(String email, String password);


    

}
