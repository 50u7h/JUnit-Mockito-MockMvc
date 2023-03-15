package com.guney.springmvc;

import com.guney.springmvc.models.*;
import com.guney.springmvc.repository.HistoryGradesDao;
import com.guney.springmvc.repository.MathGradesDao;
import com.guney.springmvc.repository.ScienceGradesDao;
import com.guney.springmvc.repository.StudentDao;
import com.guney.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @BeforeEach
    public void setupDatabase() {
        CollegeStudent student = new CollegeStudent("test", "TEST", "test.TEST@guney.com");

        MathGrade mathGrade = new MathGrade(1, 1, 100.00);
        ScienceGrade scienceGrade = new ScienceGrade(1, 1, 100.00);
        HistoryGrade historyGrade = new HistoryGrade(1, 1, 100.00);

        student.setId(1);

        studentDao.save(student);
        mathGradeDao.save(mathGrade);
        scienceGradeDao.save(scienceGrade);
        historyGradeDao.save(historyGrade);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("ALTER TABLE math_grade ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("ALTER TABLE science_grade ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("DELETE FROM history_grade");
        jdbc.execute("ALTER TABLE history_grade ALTER COLUMN ID RESTART WITH 1");
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
        Optional<MathGrade> deletedMathGrade = mathGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradeDao.findById(1);

        assertTrue(deletedStudent.isPresent(), "Return True");
        assertTrue(deletedMathGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());
        assertTrue(deletedHistoryGrade.isPresent());

        studentAndGradeService.deleteStudent(1);

        deletedStudent = studentDao.findById(1);
        deletedMathGrade = mathGradeDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);
        deletedHistoryGrade = historyGradeDao.findById(1);

        assertFalse(deletedStudent.isPresent(), "Return False");
        assertFalse(deletedMathGrade.isPresent(), "Return False");
        assertFalse(deletedScienceGrade.isPresent(), "Return False");
        assertFalse(deletedHistoryGrade.isPresent(), "Return False");

    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {

        Iterable<CollegeStudent> iterableCollegeStudents = studentAndGradeService.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5, collegeStudents.size());
    }

    @Test
    public void createGradeService() {

        assertTrue(studentAndGradeService.createGrade(80.50, 1, "math"));
        assertTrue(studentAndGradeService.createGrade(80.50, 1, "science"));
        assertTrue(studentAndGradeService.createGrade(80.50, 1, "history"));


        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);


        assertEquals(2, ((Collection<MathGrade>) mathGrades).size(), "Student has math grades");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size(), "Student has science grades");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size(), "Student has history grades");
    }

    @Test
    public void createGradeServiceReturnFalse() {

        assertFalse(studentAndGradeService.createGrade(123, 1, "math"));
        assertFalse(studentAndGradeService.createGrade(-123, 1, "math"));
        assertFalse(studentAndGradeService.createGrade(61, 2, "math"));
        assertFalse(studentAndGradeService.createGrade(61, 1, "physics"));

    }

    @Test
    public void deleteGradeService() {

        assertEquals(1, studentAndGradeService.deleteGrade(1, "math"), "Returns student id after delete");

        assertEquals(1, studentAndGradeService.deleteGrade(1, "science"), "Returns student id after delete");

        assertEquals(1, studentAndGradeService.deleteGrade(1, "history"), "Returns student id after delete");
    }

    @Test
    public void deleteGradeServiceReturnStudentIdOfZero() {

        assertEquals(0, studentAndGradeService.deleteGrade(0, "science"), "No student should have 0 id");
        assertEquals(0, studentAndGradeService.deleteGrade(1, "literature"), "No student should have a literature class");

    }

    @Test
    public void studentInformation() {

        GradeBookCollegeStudent gradeBookCollegeStudent = studentAndGradeService.studentInformation(1);

        assertNotNull(gradeBookCollegeStudent);

        assertEquals(1, gradeBookCollegeStudent.getId());
        assertEquals("test", gradeBookCollegeStudent.getFirstname());
        assertEquals("TEST", gradeBookCollegeStudent.getLastname());
        assertEquals("test.TEST@guney.com", gradeBookCollegeStudent.getEmailAddress());

        assertEquals(1, gradeBookCollegeStudent.getStudentGrades().getMathGradeResults().size());
        assertEquals(1, gradeBookCollegeStudent.getStudentGrades().getScienceGradeResults().size());
        assertEquals(1, gradeBookCollegeStudent.getStudentGrades().getHistoryGradeResults().size());

    }

    @Test
    public void studentInformationServiceReturnNull() {

        GradeBookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }
}
