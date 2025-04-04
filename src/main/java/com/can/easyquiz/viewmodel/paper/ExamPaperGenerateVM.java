package com.can.easyquiz.viewmodel.paper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class ExamPaperGenerateVM {
    @NotNull(message = "科目ID不能为空")
    private Integer subjectId;

    @Min(value = 0, message = "单选题数量不能小于0")
    private Integer singleChoiceCount;

    @Min(value = 0, message = "多选题数量不能小于0")
    private Integer multipleChoiceCount;

    @Min(value = 0, message = "判断题数量不能小于0")
    private Integer judgementCount;
    
    @NotNull(message = "难度不能为空")
    @Min(value = 1, message = "难度必须在1-5之间")
    @Max(value = 5, message = "难度必须在1-5之间")
    private Integer difficulty; // 1-入门, 2-简单, 3-中等, 4-困难, 5-专家

    private String name;

    private Integer suggestTime;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSingleChoiceCount() {
        return singleChoiceCount;
    }

    public void setSingleChoiceCount(Integer singleChoiceCount) {
        this.singleChoiceCount = singleChoiceCount;
    }

    public Integer getMultipleChoiceCount() {
        return multipleChoiceCount;
    }

    public void setMultipleChoiceCount(Integer multipleChoiceCount) {
        this.multipleChoiceCount = multipleChoiceCount;
    }

    public Integer getJudgementCount() {
        return judgementCount;
    }

    public void setJudgementCount(Integer judgementCount) {
        this.judgementCount = judgementCount;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
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
} 