package com.lalitbhanot.springmvc.service;

import com.lalitbhanot.springmvc.models.*;
import com.lalitbhanot.springmvc.repository.HistoryGradesDao;
import com.lalitbhanot.springmvc.repository.MathGradesDao;
import com.lalitbhanot.springmvc.repository.ScienceGradesDao;
import com.lalitbhanot.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private HistoryGradesDao historyGradeDao ;

    @Autowired
    StudentGrades studentGrades;

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

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }



    public boolean createGrade(double grade, int studentId, String gradeType) {

        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradeDao.save(mathGrade);
                return true;
            }
            if (gradeType.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradeDao.save(scienceGrade);
                return true;
            }
            if (gradeType.equals("history")) {
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradeDao.save(historyGrade);
                return true;
            }
        }

        return false;
    }


    public int deleteGrade(int id, String gradeType) {
        int studentId = 0;

        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradeDao.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradeDao.deleteById(id);
        }
        if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradeDao.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradeDao.deleteById(id);
        }
        if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradeDao.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradeDao.deleteById(id);
        }

        return studentId;
    }
    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
            mathGradeDao.deleteByStudentId(id);
            scienceGradeDao.deleteByStudentId(id);
            historyGradeDao.deleteByStudentId(id);
        }
    }

        public GradebookCollegeStudent studentInformation(int id) {

            if (!checkIfStudentIsNull(id)) {
                return null;
            }

            Optional<CollegeStudent> student = studentDao.findById(id);
            Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(id);
            Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(id);
            Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(id);

            List<Grade> mathGradesList = new ArrayList<>();
            mathGrades.forEach(mathGradesList::add);

            List<Grade> scienceGradesList = new ArrayList<>();
            scienceGrades.forEach(scienceGradesList::add);

            List<Grade> historyGradesList = new ArrayList<>();
            historyGrades.forEach(historyGradesList::add);

            studentGrades.setMathGradeResults(mathGradesList);
            studentGrades.setScienceGradeResults(scienceGradesList);
            studentGrades.setHistoryGradeResults(historyGradesList);

            GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(student.get().getId(),
                    student.get().getFirstname(), student.get().getLastname(), student.get().getEmailAddress(),
                    studentGrades);

            return gradebookCollegeStudent;
        }

    }

