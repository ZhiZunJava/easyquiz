package com.can.easyquiz.viewmodel.admin.exam;

import com.can.easyquiz.annotation.BasePage;

public class ExamPaperPageRequestVM extends BasePage {
    private Integer id;
    private Integer subjectId;
    private Integer level;
    private Integer paperType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }
}
