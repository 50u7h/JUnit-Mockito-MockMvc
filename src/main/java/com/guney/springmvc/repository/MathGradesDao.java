package com.guney.springmvc.repository;

import com.guney.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {

    Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
