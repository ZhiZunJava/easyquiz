package com.can.easyquiz.viewmodel.admin.dashboard;

import java.util.List;
import java.util.Map;

public class IndexVM {
    private Integer examPaperCount;
    private Integer questionCount;
    private Integer doExamPaperCount;
    private Integer doQuestionCount;
    
    // 按年级统计的题目数量
    private Map<String, Integer> questionCountByGradeLevel;
    
    // 按学科统计的题目数量
    private Map<String, Integer> questionCountBySubject;
    
    // 按题目类型统计的数量
    private Map<String, Integer> questionCountByType;
    
    // 按年级统计的正确率
    private Map<String, Double> correctRateByGradeLevel;
    
    // 按学科统计的正确率
    private Map<String, Double> correctRateBySubject;
    
    // 难度分布统计
    private Map<String, Integer> questionCountByDifficulty;
    
    // 热门题目TOP 5（被答题次数最多的题目）
    private List<Map<String, Object>> hotQuestions;

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

    public Map<String, Integer> getQuestionCountByGradeLevel() {
        return questionCountByGradeLevel;
    }

    public void setQuestionCountByGradeLevel(Map<String, Integer> questionCountByGradeLevel) {
        this.questionCountByGradeLevel = questionCountByGradeLevel;
    }

    public Map<String, Integer> getQuestionCountBySubject() {
        return questionCountBySubject;
    }

    public void setQuestionCountBySubject(Map<String, Integer> questionCountBySubject) {
        this.questionCountBySubject = questionCountBySubject;
    }

    public Map<String, Integer> getQuestionCountByType() {
        return questionCountByType;
    }

    public void setQuestionCountByType(Map<String, Integer> questionCountByType) {
        this.questionCountByType = questionCountByType;
    }

    public Map<String, Double> getCorrectRateByGradeLevel() {
        return correctRateByGradeLevel;
    }

    public void setCorrectRateByGradeLevel(Map<String, Double> correctRateByGradeLevel) {
        this.correctRateByGradeLevel = correctRateByGradeLevel;
    }

    public Map<String, Double> getCorrectRateBySubject() {
        return correctRateBySubject;
    }

    public void setCorrectRateBySubject(Map<String, Double> correctRateBySubject) {
        this.correctRateBySubject = correctRateBySubject;
    }

    public Map<String, Integer> getQuestionCountByDifficulty() {
        return questionCountByDifficulty;
    }

    public void setQuestionCountByDifficulty(Map<String, Integer> questionCountByDifficulty) {
        this.questionCountByDifficulty = questionCountByDifficulty;
    }

    public List<Map<String, Object>> getHotQuestions() {
        return hotQuestions;
    }

    public void setHotQuestions(List<Map<String, Object>> hotQuestions) {
        this.hotQuestions = hotQuestions;
    }
}
