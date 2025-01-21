package com.lalitbhanot.springmvc;

import com.lalitbhanot.springmvc.models.CollegeStudent;
import com.lalitbhanot.springmvc.repository.StudentDao;
import com.lalitbhanot.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
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

    @BeforeEach
    public void setupData()
    {
        jdbctemplate.execute("insert into student (firstName,lastName,email_address) values ('lalit','bhanot','lal@gmail.com') ;");
    }
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


    @AfterEach
    public  void AfterTranscation()
    {
        jdbctemplate.execute("DELETE FROM STUDENT");
        jdbctemplate.execute("ALTER TABLE STUDENT ALTER COLUMN ID RESTART WITH 1");
    }



    }

