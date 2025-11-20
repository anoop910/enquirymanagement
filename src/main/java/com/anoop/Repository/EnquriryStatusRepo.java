package com.anoop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoop.Entity.EnquriryStatusEntity;

@Repository
public interface EnquriryStatusRepo extends JpaRepository<EnquriryStatusEntity, Integer> {
}
