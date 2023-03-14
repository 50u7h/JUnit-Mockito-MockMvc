package com.guney.springmvc.repository;

import com.guney.springmvc.models.ScienceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceGradesDao extends JpaRepository<ScienceGrade, Integer> {

    Iterable<ScienceGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
