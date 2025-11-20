package com.anoop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anoop.Entity.CoursesEntity;

public interface CoursesRepo extends JpaRepository<CoursesEntity, Integer> {
}
