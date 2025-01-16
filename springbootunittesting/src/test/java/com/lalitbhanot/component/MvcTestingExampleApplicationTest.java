package com.lalitbhanot.component;
import com.lalitbhanot.component.models.CollegeStudent;
import com.lalitbhanot.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MvcTestingExampleApplicationTest {
    private static int count = 0;
    @Value("${info.app.name}")
    private String appName ;
    @Value("${info.school.name}")
    private String schoolName ;
    @Value("${info.app.description}")
    private String desc ;
    @Value("${info.app.version}")
    private String version ;

    @Autowired
     CollegeStudent collegeStudent ;

    @Autowired
    StudentGrades studentGrades ;

    @Autowired
    ApplicationContext context;

    @Test
    void Test() {
        System.out.print(appName);
    }
    @BeforeEach
    public void beforeEach() {
            count = count + 1;
            System.out.println("Testing: " + appName + " which is " + desc +
                    "  Version: " + version + ". Execution of test method " + count);
            collegeStudent.setFirstname("Eric");
            collegeStudent.setLastname("Roby");
            collegeStudent.setEmailAddress("abc@luv2code_school.com");
            studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
            collegeStudent.setStudentGrades(studentGrades);
        }

    @DisplayName("Add grade results for student grades not equal")
    @Test
    public void addGradeResultsForStudentGradesAssertNotEquals() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Is grade greater")
    @Test
    public void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(90, 75),
                "failure - should be true");
    }

    @DisplayName("Is grade greater false")
    @Test
    public void isGradeGreaterStudentGradesAssertFalse() {
        assertFalse(studentGrades.isGradeGreater(89, 92),
                "failure - should be false");
    }

    @DisplayName("Check Null for student grades")
    @Test
    public void checkNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()),
                "object should not be null");
    }

    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradesInit() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Chad");
        studentTwo.setLastname("Darby");
        studentTwo.setEmailAddress("chad.darby@luv2code_school.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);

        assertNotSame(collegeStudent, studentTwo);
    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals",
                ()-> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                        collegeStudent.getStudentGrades().getMathGradeResults())),
                ()-> assertEquals(88.31, studentGrades.findGradePointAverage(
                        collegeStudent.getStudentGrades().getMathGradeResults()))
        );
    }
}

