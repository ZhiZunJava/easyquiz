package com.can.easyquiz.viewmodel.student.exam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ExamPaperStudentVM {
    private Integer id;
    @NotNull
    private Integer level;
    @NotNull
    private Integer subjectId;
    @NotNull
    private Integer paperType;
    @NotBlank
    private String name;
    @NotNull
    private Integer suggestTime;

    private List<String> limitDateTime;

    @Size(min = 1,message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemStudentVM> titleItems;

    private String score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(Integer suggestTime) {
        this.suggestTime = suggestTime;
    }

    public List<String> getLimitDateTime() {
        return limitDateTime;
    }

    public void setLimitDateTime(List<String> limitDateTime) {
        this.limitDateTime = limitDateTime;
    }

    public List<ExamPaperTitleItemStudentVM> getTitleItems() {
        return titleItems;
    }

    public void setTitleItems(List<ExamPaperTitleItemStudentVM> titleItems) {
        this.titleItems = titleItems;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
} 