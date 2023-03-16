package com.guney.springmvc.repository;

import com.guney.springmvc.models.MathGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MathGradesDao extends JpaRepository<MathGrade, Integer> {

    Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
