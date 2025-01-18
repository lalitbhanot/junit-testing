package com.lalitbhanot.springmvc;

import com.lalitbhanot.springmvc.models.CollegeStudent;
import com.lalitbhanot.springmvc.models.GradebookCollegeStudent;
import com.lalitbhanot.springmvc.repository.StudentDao;
import com.lalitbhanot.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {

    private static MockHttpServletRequest request ;
    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbctemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    @BeforeAll
    public static void setRequest(){
request = new MockHttpServletRequest() ;
request.setParameter("lalit","bhanot","lal@gmail.com");
        request.setParameter("lalit2","bhanot2","lal2@gmail.com");
        request.setParameter("lalit3","bhanot3","lal3@gmail.com");
    }

    @BeforeEach
    public void setupData() {

        jdbctemplate.execute("insert into student (firstName,lastName,email_address) values ('lalit','bhanot','lal@gmail.com') ;");
    }

    @AfterEach
    public void AfterTranscation() {
        jdbctemplate.execute("DELETE FROM STUDENT");
        jdbctemplate.execute("ALTER TABLE STUDENT ALTER COLUMN ID RESTART WITH 1");
    }

    @Test
    public void getStdentHttpRequest() throws Exception {
        CollegeStudent collegeStudent1 = new GradebookCollegeStudent("fname1", "lname2", "email1");
        CollegeStudent collegeStudent2 = new GradebookCollegeStudent("fname2", "lname2", "email2");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(collegeStudent1, collegeStudent2));

        when(studentAndGradeServiceMock.getGradeBook()).thenReturn(collegeStudentList);
        assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("email", request.getParameterValues("email"))).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");

    }
}