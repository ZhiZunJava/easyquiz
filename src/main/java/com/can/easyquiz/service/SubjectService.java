package com.can.easyquiz.service;

import com.can.easyquiz.domain.Subject;
import com.can.easyquiz.viewmodel.admin.education.SubjectPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SubjectService extends BasicService<Subject> {

    List<Subject> getSubjectByLevel(Integer level);

    List<Subject> allSubject();

    Integer levelBySubjectId(Integer id);

    PageInfo<Subject> page(SubjectPageRequestVM requestVM);
}