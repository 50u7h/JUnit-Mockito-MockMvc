package com.guney.springmvc;

import com.guney.springmvc.models.CollegeStudent;
import com.guney.springmvc.models.GradebookCollegeStudent;
import com.guney.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@TestPropertySource("/application.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class GradeBookControllerTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentCreateServiceMock;


    @BeforeEach
    public void setupDatabase() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Eric', 'Roby', 'eric.roby@guney.com')");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("DELETE FROM student");
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {

        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
                "eric_roby@guney.com");

        CollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby",
                "chad_darby@guney.com");

        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));

        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradeBook());

//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        ModelAndViewAssert.assertViewName(mav, "index");
    }
}
