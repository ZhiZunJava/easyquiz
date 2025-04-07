package com.can.easyquiz.service.impl;

import com.can.easyquiz.domain.ExamPaperAnswerUpdate;
import com.can.easyquiz.domain.ExamPaperQuestionCustomerAnswer;
import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.TextContent;
import com.can.easyquiz.enums.QuestionTypeEnum;
import com.can.easyquiz.repository.ExamPaperQuestionCustomerAnswerMapper;
import com.can.easyquiz.service.ExamPaperQuestionCustomerAnswerService;
import com.can.easyquiz.service.TextContentService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.ExamUtil;
import com.can.easyquiz.utils.JsonUtil;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl extends BaseServiceImpl<ExamPaperQuestionCustomerAnswer> implements ExamPaperQuestionCustomerAnswerService {

    private final ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper;
    private final TextContentService textContentService;

    @Autowired
    public ExamPaperQuestionCustomerAnswerServiceImpl(ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper, TextContentService textContentService) {
        super(examPaperQuestionCustomerAnswerMapper);
        this.examPaperQuestionCustomerAnswerMapper = examPaperQuestionCustomerAnswerMapper;
        this.textContentService = textContentService;
    }


    @Override
    public PageInfo<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperQuestionCustomerAnswerMapper.studentPage(requestVM)
        );
    }

    @Override
    public List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id) {
        return examPaperQuestionCustomerAnswerMapper.selectListByPaperAnswerId(id);
    }


    @Override
    public void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
        examPaperQuestionCustomerAnswerMapper.insertList(examPaperQuestionCustomerAnswers);
    }

    @Override
    public ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa) {
        ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
        examPaperSubmitItemVM.setId(qa.getId());
        examPaperSubmitItemVM.setQuestionId(qa.getQuestionId());
        examPaperSubmitItemVM.setDoRight(qa.getDoRight());
        examPaperSubmitItemVM.setItemOrder(qa.getItemOrder());
        examPaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(qa.getQuestionScore()));
        examPaperSubmitItemVM.setScore(ExamUtil.scoreToVM(qa.getCustomerScore()));
        setSpecialToVM(examPaperSubmitItemVM, qa);
        return examPaperSubmitItemVM;
    }

    @Override
    public Integer selectAllCount() {
        return examPaperQuestionCustomerAnswerMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = examPaperQuestionCustomerAnswerMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

    @Override
    public int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
        return examPaperQuestionCustomerAnswerMapper.updateScore(examPaperAnswerUpdates);
    }

    @Override
    public Map<String, Double> selectCorrectRateByGradeLevel() {
        List<Map<String, Object>> correctRateByGradeLevel = examPaperQuestionCustomerAnswerMapper.selectCorrectRateByGradeLevel();
        Map<String, Double> result = new HashMap<>();
        for (Map<String, Object> item : correctRateByGradeLevel) {
            Integer gradeLevelName = (Integer) item.get("level");
            Double correctRate = calculateCorrectRate(item);
            result.put(String.valueOf(gradeLevelName), correctRate);
        }
        return result;
    }
    
    @Override
    public Map<String, Double> selectCorrectRateByDifficulty() {
        List<Map<String, Object>> correctRateBySubject = examPaperQuestionCustomerAnswerMapper.selectCorrectRateByDifficulty();
        Map<String, Double> result = new HashMap<>();
        for (Map<String, Object> item : correctRateBySubject) {
            Number difficult = (Number) item.get("difficult");
            Double correctRate = calculateCorrectRate(item);
            result.put(String.valueOf(difficult), correctRate);
        }
        return result;
    }
    
    /**
     * 计算正确率
     * @param item 包含doRightCount和totalCount的Map
     * @return 正确率，保留两位小数
     */
    private Double calculateCorrectRate(Map<String, Object> item) {
        if (item == null) {
            return 0.0;
        }
        
        Number doRightCount = (Number) item.get("doRightCount");
        Number totalCount = (Number) item.get("totalCount");
        
        if (doRightCount == null || totalCount == null || totalCount.longValue() == 0) {
            return 0.0;
        }
        
        // 使用BigDecimal进行精确计算
        double rate = doRightCount.doubleValue() / totalCount.doubleValue();
        // 保留两位小数，并转换为百分比
        return Math.round(rate * 10000) / 100.0;
    }

    private void setSpecialToVM(ExamPaperSubmitItemVM examPaperSubmitItemVM, ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                examPaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(examPaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContent textContent = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JsonUtil.toJsonListObject(textContent.getContent(), String.class);
                examPaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(examPaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContent content = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                    examPaperSubmitItemVM.setContent(content.getContent());
                } else {
                    examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }
}