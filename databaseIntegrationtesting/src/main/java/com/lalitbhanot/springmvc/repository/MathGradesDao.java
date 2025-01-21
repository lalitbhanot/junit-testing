package com.lalitbhanot.springmvc.repository;

import com.lalitbhanot.springmvc.models.MathGrade;
import com.lalitbhanot.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);
    public void deleteByStudentId(int id);
}
