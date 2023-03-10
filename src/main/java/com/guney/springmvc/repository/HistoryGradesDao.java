package com.guney.springmvc.repository;

import com.guney.springmvc.models.HistoryGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryGradesDao extends JpaRepository<HistoryGrade, Integer> {

    Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
