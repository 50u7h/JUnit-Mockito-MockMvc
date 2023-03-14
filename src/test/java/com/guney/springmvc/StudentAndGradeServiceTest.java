package com.guney.springmvc;

import com.guney.springmvc.models.CollegeStudent;
import com.guney.springmvc.models.HistoryGrade;
import com.guney.springmvc.models.MathGrade;
import com.guney.springmvc.models.ScienceGrade;
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

    @Sql("/insertData.sql")
    @Test
    public void getGradebookService() {

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


        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2, "Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2, "Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2, "Student has history grades");
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
    }
}
