package com.guney.springmvc.repository;

import com.guney.springmvc.models.HistoryGrade;
import com.guney.springmvc.models.ScienceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryGradesDao extends JpaRepository<HistoryGrade, Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId(int id);
}
