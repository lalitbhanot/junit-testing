package com.lalitbhanot.springmvc;

import com.lalitbhanot.springmvc.models.*;
import com.lalitbhanot.springmvc.repository.HistoryGradesDao;
import com.lalitbhanot.springmvc.repository.MathGradesDao;
import com.lalitbhanot.springmvc.repository.ScienceGradesDao;
import com.lalitbhanot.springmvc.repository.StudentDao;
import com.lalitbhanot.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbctemplate ;

    @Autowired
    MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent ;
    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade ;
    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade ;
    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade ;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent ;
    @Value("${sql.script.delete.math.grade}")
    private String sqlDeletMathGrade ;
    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade ;
    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade ;

    @BeforeEach
    public void setupDatabase() {
//        jdbctemplate.execute("insert into student(firstname, lastname, email_address) " +
//                "values ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");
//
//        jdbctemplate.execute("insert into math_grade(student_id,grade) values (1,100.00)");
//
//        jdbctemplate.execute("insert into science_grade(student_id,grade) values (1,100.00)");
//
//        jdbctemplate.execute("insert into history_grade(student_id,grade) values (1,100.00)");
        jdbctemplate.execute(sqlAddStudent) ;
        jdbctemplate.execute(sqlAddMathGrade) ;
        jdbctemplate.execute(sqlAddScienceGrade) ;
        jdbctemplate.execute(sqlAddHistoryGrade) ;


    }

    @AfterEach
    public  void AfterTranscation()
    {
        jdbctemplate.execute(sqlDeleteStudent);
        jdbctemplate.execute(sqlDeletMathGrade);
        jdbctemplate.execute(sqlDeleteScienceGrade);
        jdbctemplate.execute(sqlDeleteHistoryGrade);

    }
//        jdbctemplate.execute("DELETE FROM STUDENT");
//        jdbctemplate.execute("ALTER TABLE STUDENT ALTER COLUMN ID RESTART WITH 1");
//
//        jdbctemplate.execute("DELETE FROM math_grade");
//        jdbctemplate.execute("ALTER TABLE math_grade ALTER COLUMN ID RESTART WITH 1");
//
//        jdbctemplate.execute("DELETE FROM science_grade");
//        jdbctemplate.execute("ALTER TABLE science_grade ALTER COLUMN ID RESTART WITH 1");
//
//        jdbctemplate.execute("DELETE FROM history_grade");
//        jdbctemplate.execute("ALTER TABLE history_grade ALTER COLUMN ID RESTART WITH 1");
//    }
        @Test
        public void createStudentService() {

            studentService.createStudent("Chad", "Darby",
                    "chad.darby@luv2code_school.com");

            CollegeStudent student = studentDao.
                    findByEmailAddress("chad.darby@luv2code_school.com");

            assertEquals("chad.darby@luv2code_school.com",
                    student.getEmailAddress(), "find by email");
        }

    @Test
    public void isStudentNullCheck(){
    assertTrue(studentService.checkStudentIdisNull(1));
    }


    @Sql("/insertdata.sql")
    @Test
    public void getGradeBookService (){
        Iterable<CollegeStudent> studentsIterable = studentService.getGradeBook();
        List<CollegeStudent> collegeStudentList = new ArrayList<>();
        for(CollegeStudent collegeStudent:studentsIterable)
        {
            collegeStudentList.add(collegeStudent) ;
        }
        assertEquals(5,collegeStudentList.size());
    }


    @Test
    public void createGradeService() {

        // Create the grade
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        // Get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);

        // Verify there is grades
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,
                "Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2);
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2);
    }
    @Test
    public void createGradeServiceReturnFalse() {
        assertFalse(studentService.createGrade(105, 1, "math"));
        assertFalse(studentService.createGrade(-5, 1, "math"));
        assertFalse(studentService.createGrade(80.50, 2, "math"));
        assertFalse(studentService.createGrade(80.50, 1, "literature"));
    }
    @Test
    public void deleteGradeService() {
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Returns student id after delete");

        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "history"),
                "Returns student id after delete");
    }
    @Test
    public void deleteGradeServiceReturnStudentIdOfZero() {
        assertEquals(0, studentService.deleteGrade(0, "science"),
                "No student should have 0 id");
        assertEquals(0, studentService.deleteGrade(1, "literature"),
                "No student should have a literature class");
    }

    @Test
    public void deleteStudentService() {

        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        Optional<MathGrade> deletedMathGrade = mathGradeDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return True");

        studentService.deleteStudent(1);

        deletedCollegeStudent = studentDao.findById(1);
        deletedMathGrade = mathGradeDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);
        deletedHistoryGrade = historyGradeDao.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());
    }
    @Test
    public void studentInformation() {

        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("Eric", gradebookCollegeStudent.getFirstname());
        assertEquals("Roby", gradebookCollegeStudent.getLastname());
        assertEquals("eric.roby@luv2code_school.com", gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    public void studentInformationServiceReturnNull() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);
        assertNull(gradebookCollegeStudent);
    }



}

