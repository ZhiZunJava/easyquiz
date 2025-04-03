package com.can.easyquiz.repository;

import com.can.easyquiz.domain.Subject;
import com.can.easyquiz.viewmodel.admin.education.SubjectPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper extends BasicMapper<Subject> {

    List<Subject> getSubjectByLevel(Integer level);

    List<Subject> allSubject();

    List<Subject> page(SubjectPageRequestVM requestVM);
}