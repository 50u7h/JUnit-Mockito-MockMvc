package com.guney.springmvc;

import com.guney.springmvc.models.CollegeStudent;
import com.guney.springmvc.repository.StudentDao;
import com.guney.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private StudentDao studentDao;

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
    public void createStudentService() {
        studentAndGradeService.createStudent("test", "TEST", "tset@tset.com");

        CollegeStudent student = studentDao.findByEmailAddress("tset@tset.com");

        assertEquals("tset@tset.com", student.getEmailAddress(), "find by email");
    }

    @Test
    public void isStudentNullCheck() {
        assertTrue(studentAndGradeService.checkIfStudentIsNull(1));

        assertFalse(studentAndGradeService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deletedStudent = studentDao.findById(1);

        assertTrue(deletedStudent.isPresent(), "Return True");

        studentAndGradeService.deleteStudent(1);

        deletedStudent = studentDao.findById(1);

        assertFalse(deletedStudent.isPresent(), "Return False");
    }

    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> iterableStudents = studentAndGradeService.getGradeBook();

        List<CollegeStudent> students = new ArrayList<>();

        for (CollegeStudent student : iterableStudents) {
            students.add(student);
        }

        assertEquals(1, students.size());
    }
}
