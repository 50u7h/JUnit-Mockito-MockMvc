package com.guney.springmvc.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GradeBook {

    private List<GradebookCollegeStudent> students = new ArrayList<>();

    public GradeBook() {

    }

    public GradeBook(List<GradebookCollegeStudent> students) {
        this.students = students;
    }

    public List<GradebookCollegeStudent> getStudents() {
        return students;
    }

    public void setStudents(List<GradebookCollegeStudent> students) {
        this.students = students;
    }
}
