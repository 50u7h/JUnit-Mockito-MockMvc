package com.guney.springmvc.controller;

import com.guney.springmvc.models.*;
import com.guney.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeBookController {

    @Autowired
    private GradeBook gradeBook;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model) {
        return "index";
    }
}