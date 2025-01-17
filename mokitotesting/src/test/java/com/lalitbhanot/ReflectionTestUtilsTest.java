package com.lalitbhanot;

import com.lalitbhanot.MvcTestingExampleApplication;
import com.lalitbhanot.components.models.CollegeStudent;
import com.lalitbhanot.components.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;
   // CollegeStudent student =  new CollegeStudent() ;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach() {
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("new@gmail.com");
        student.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student, "id", 1);
        ReflectionTestUtils.setField(student, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(
                        100.0, 85.0, 76.50, 91.75))));

        System.out.println("student is: " + student);
    }

    @Test
    public void getPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(student, "id"));
    }

    @Test
    public void invokePrivateMethod() {
        assertEquals("1 Eric Roby",
                ReflectionTestUtils.invokeMethod(student, "getFullNameAndId"),
                "Fail private method not call");
    }
}
