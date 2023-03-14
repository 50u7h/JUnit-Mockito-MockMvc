package com.guney.springmvc.repository;

import com.guney.springmvc.models.MathGrade;
import com.guney.springmvc.models.ScienceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceGradesDao extends JpaRepository<ScienceGrade, Integer> {

    public Iterable<ScienceGrade> findGradeByStudentId(int id);
}
