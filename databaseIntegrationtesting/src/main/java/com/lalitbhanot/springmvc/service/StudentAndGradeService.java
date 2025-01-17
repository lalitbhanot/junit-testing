package com.lalitbhanot.springmvc.service;

import com.lalitbhanot.springmvc.models.CollegeStudent;
import com.lalitbhanot.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }


    public boolean checkStudentIdisNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id) ;
        return  (student.isPresent()) ? true :false ;

    }

    public Iterable<CollegeStudent> getGradeBook() {
        Iterable<CollegeStudent> studentsIterable = studentDao.findAll() ;
        return  studentsIterable ;
    }
}
