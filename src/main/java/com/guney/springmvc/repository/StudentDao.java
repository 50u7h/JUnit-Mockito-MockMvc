package com.guney.springmvc.repository;

import com.guney.springmvc.models.CollegeStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<CollegeStudent, Integer> {
    public CollegeStudent findByEmailAddress(String email);
}
