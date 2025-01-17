package com.lalitbhanot;

import com.lalitbhanot.components.dao.ApplicationDao;
import com.lalitbhanot.components.models.CollegeStudent;
import com.lalitbhanot.components.models.StudentGrades;
import com.lalitbhanot.components.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MvcTestingExampleApplicationTest {
    private static int count = 0;
    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    // @Mock
    @MockitoBean
    private ApplicationDao applicationDao ;

  //  @InjectMocks
    @Autowired
    private ApplicationService applicationService ;

    // with @Mock ---> @InjectMocks
    // with @MockitoBean ---> @Autowired


    @BeforeEach
    public void beforeEach() {
        count = count + 1;
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        student.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student, "id", 1);
        ReflectionTestUtils.setField(student, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(
                        100.0, 85.0, 76.50, 91.75))));
    }


    @Test
    public void getPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(student, "id"));
    }
    @DisplayName("when and verify")
    @Test
    public void assertEqualsTestAddGrades(){
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);
        assertEquals(100,applicationService.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("runtime error")
    @Test
    public void throwsRuntimeError(){
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");
        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent) ;
        assertThrows(RuntimeException.class,() -> {
            applicationService.checkNull(nullStudent) ;
        }) ;
    }

    @DisplayName("Multiple Stubbing")
    @Test
    public void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Do not throw exception second time",
                applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }

}
