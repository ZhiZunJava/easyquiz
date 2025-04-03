package com.can.easyquiz.viewmodel.admin.dashboard;

public class IndexVM {
    private Integer examPaperCount;
    private Integer questionCount;
    private Integer doExamPaperCount;
    private Integer doQuestionCount;

    public Integer getExamPaperCount() {
        return examPaperCount;
    }

    public void setExamPaperCount(Integer examPaperCount) {
        this.examPaperCount = examPaperCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getDoExamPaperCount() {
        return doExamPaperCount;
    }

    public void setDoExamPaperCount(Integer doExamPaperCount) {
        this.doExamPaperCount = doExamPaperCount;
    }

    public Integer getDoQuestionCount() {
        return doQuestionCount;
    }

    public void setDoQuestionCount(Integer doQuestionCount) {
        this.doQuestionCount = doQuestionCount;
    }
}
