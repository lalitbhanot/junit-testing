package com.lalitbhanot.springmvc.repository;

import com.lalitbhanot.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradesDao extends CrudRepository<ScienceGrade, Integer> {

    public Iterable<ScienceGrade> findGradeByStudentId(int id);
    public void deleteByStudentId(int id);
}
