package com.can.easyquiz.service.impl;

import com.can.easyquiz.domain.*;
import com.can.easyquiz.enums.ActionEnum;
import com.can.easyquiz.enums.ExamPaperTypeEnum;
import com.can.easyquiz.enums.QuestionTypeEnum;
import com.can.easyquiz.repository.ExamPaperMapper;
import com.can.easyquiz.repository.QuestionMapper;
import com.can.easyquiz.service.ExamPaperService;
import com.can.easyquiz.service.QuestionService;
import com.can.easyquiz.service.SubjectService;
import com.can.easyquiz.service.TextContentService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.ExamUtil;
import com.can.easyquiz.utils.JsonUtil;
import com.can.easyquiz.utils.ModelMapperSingle;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.can.easyquiz.viewmodel.admin.question.QuestionEditItemVM;
import com.can.easyquiz.viewmodel.admin.question.QuestionEditRequestVM;
import com.can.easyquiz.viewmodel.student.dashboard.PaperFilter;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfo;
import com.can.easyquiz.viewmodel.student.exam.*;
import com.can.easyquiz.viewmodel.paper.ExamPaperGenerateVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final TextContentService textContentService;
    private final QuestionService questionService;
    private final SubjectService subjectService;

    @Autowired
    public ExamPaperServiceImpl(ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, TextContentService textContentService, QuestionService questionService, SubjectService subjectService) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
        this.questionMapper = questionMapper;
        this.textContentService = textContentService;
        this.questionService = questionService;
        this.subjectService = subjectService;
    }


    @Override
    public PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.page(requestVM));
    }

    @Override
    public PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.studentPage(requestVM));
    }


    @Override
    @Transactional
    public ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user) {
        ActionEnum actionEnum = (examPaperEditRequestVM.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItemsVM = examPaperEditRequestVM.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        ExamPaper examPaper;
        if (actionEnum == ActionEnum.ADD) {
            examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
            TextContent frameTextContent = new TextContent(frameTextContentStr, now);
            textContentService.insertByFilter(frameTextContent);
            examPaper.setFrameTextContentId(frameTextContent.getId());
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(user.getId());
            examPaper.setDeleted(false);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.insertSelective(examPaper);
        } else {
            examPaper = examPaperMapper.selectByPrimaryKey(examPaperEditRequestVM.getId());
            TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
            frameTextContent.setContent(frameTextContentStr);
            textContentService.updateByIdFilter(frameTextContent);
            modelMapper.map(examPaperEditRequestVM, examPaper);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.updateByPrimaryKeySelective(examPaper);
        }
        return examPaper;
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(id);
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
        vm.setLevel(examPaper.getGradeLevel());
        TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = null;
        if (examPaperTitleItemObjects != null) {
            questionIds = examPaperTitleItemObjects.stream()
                    .flatMap(t -> t.getQuestionItems().stream()
                            .map(ExamPaperQuestionItemObject::getId))
                    .collect(Collectors.toList());
        }
        List<Question> questions = questionMapper.selectByIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = null;
        if (examPaperTitleItemObjects != null) {
            examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
                ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
                List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                    Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                    QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                    questionEditRequestVM.setItemOrder(i.getItemOrder());
                    return questionEditRequestVM;
                }).collect(Collectors.toList());
                tTitleVM.setQuestionItems(questionItemsVM);
                return tTitleVM;
            }).collect(Collectors.toList());
        }
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<PaperInfo> indexPaper(PaperFilter paperFilter) {
        return examPaperMapper.indexPaper(paperFilter);
    }


    @Override
    public Integer selectAllCount() {
        return examPaperMapper.selectAllCount();
    }

    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer gradeLevel = subjectService.levelBySubjectId(examPaperEditRequestVM.getSubjectId());
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        examPaper.setGradeLevel(gradeLevel);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExamPaper generateSmartPaper(ExamPaperGenerateVM model, User user) {
        int totalRequested = model.getSingleChoiceCount() + model.getMultipleChoiceCount() + model.getJudgementCount();
        if (totalRequested == 0) {
            throw new IllegalArgumentException("必须至少选择一个题型的题目数量");
        }
        ExamPaper examPaper = new ExamPaper();
        examPaper.setName(model.getName() != null ? model.getName() :
                "智能训练试卷" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        examPaper.setSubjectId(model.getSubjectId());
        examPaper.setPaperType(ExamPaperTypeEnum.SmartTraining.getCode());
        examPaper.setGradeLevel(subjectService.levelBySubjectId(model.getSubjectId()));
        examPaper.setCreateUser(user.getId());
        examPaper.setCreateTime(new Date());
        examPaper.setDeleted(false);

        // 获取题目
        List<Question> singleChoiceQuestions = questionMapper.selectRandomQuestionsByTypeAndDifficulty(
            model.getSubjectId(), QuestionTypeEnum.SingleChoice.getCode(), model.getDifficulty(), model.getSingleChoiceCount());
        List<Question> multipleChoiceQuestions = questionMapper.selectRandomQuestionsByTypeAndDifficulty(
            model.getSubjectId(), QuestionTypeEnum.MultipleChoice.getCode(), model.getDifficulty(), model.getMultipleChoiceCount());
        List<Question> judgementQuestions = questionMapper.selectRandomQuestionsByTypeAndDifficulty(
            model.getSubjectId(), QuestionTypeEnum.TrueFalse.getCode(), model.getDifficulty(), model.getJudgementCount());

        // 计算总分
        int totalScore = singleChoiceQuestions.stream().mapToInt(Question::getScore).sum() +
                        multipleChoiceQuestions.stream().mapToInt(Question::getScore).sum() +
                        judgementQuestions.stream().mapToInt(Question::getScore).sum();
        
        int totalQuestions = singleChoiceQuestions.size() + multipleChoiceQuestions.size() + judgementQuestions.size();

        // 计算建议时间：基础时间 + 难度系数 * 题目数量
        int baseTime = 30; // 基础时间30分钟
        double difficultyFactor = model.getDifficulty() * 0.2; // 难度系数，每增加一级难度增加20%时间
        int suggestTime = (int) (baseTime + (totalQuestions * difficultyFactor));
        examPaper.setSuggestTime(model.getSuggestTime() != null ? model.getSuggestTime() : suggestTime);

        examPaper.setScore(totalScore);
        examPaper.setQuestionCount(totalQuestions);

        // 保存试卷
        examPaperMapper.insertSelective(examPaper);

        // 保存试卷框架内容
        TextContent frameTextContent = new TextContent();
        List<ExamPaperTitleItemVM> titleItems = new ArrayList<>();
        
        if (!singleChoiceQuestions.isEmpty()) {
            ExamPaperTitleItemVM singleChoiceTitle = new ExamPaperTitleItemVM();
            singleChoiceTitle.setName("单选题");
            singleChoiceTitle.setQuestionItems(singleChoiceQuestions.stream()
                .map(q -> {
                    QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(q.getId());
                    questionVM.setItemOrder(titleItems.size() + 1);
                    return questionVM;
                }).collect(Collectors.toList()));
            titleItems.add(singleChoiceTitle);
        }

        if (!multipleChoiceQuestions.isEmpty()) {
            ExamPaperTitleItemVM multipleChoiceTitle = new ExamPaperTitleItemVM();
            multipleChoiceTitle.setName("多选题");
            multipleChoiceTitle.setQuestionItems(multipleChoiceQuestions.stream()
                .map(q -> {
                    QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(q.getId());
                    questionVM.setItemOrder(titleItems.size() + 1);
                    return questionVM;
                }).collect(Collectors.toList()));
            titleItems.add(multipleChoiceTitle);
        }

        if (!judgementQuestions.isEmpty()) {
            ExamPaperTitleItemVM judgementTitle = new ExamPaperTitleItemVM();
            judgementTitle.setName("判断题");
            judgementTitle.setQuestionItems(judgementQuestions.stream()
                .map(q -> {
                    QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(q.getId());
                    questionVM.setItemOrder(titleItems.size() + 1);
                    return questionVM;
                }).collect(Collectors.toList()));
            titleItems.add(judgementTitle);
        }

        String frameTextContentStr = JsonUtil.toJsonStr(titleItems);
        frameTextContent.setContent(frameTextContentStr);
        frameTextContent.setCreateTime(new Date());
        textContentService.insertByFilter(frameTextContent);
        examPaper.setFrameTextContentId(frameTextContent.getId());
        examPaperMapper.updateByPrimaryKeySelective(examPaper);

        return examPaper;
    }

    @Override
    public ExamPaperStudentVM examPaperToStudentVM(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(id);
        ExamPaperStudentVM vm = modelMapper.map(examPaper, ExamPaperStudentVM.class);
        vm.setLevel(examPaper.getGradeLevel());
        TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = null;
        if (examPaperTitleItemObjects != null) {
            questionIds = examPaperTitleItemObjects.stream()
                    .flatMap(t -> t.getQuestionItems().stream()
                            .map(ExamPaperQuestionItemObject::getId))
                    .collect(Collectors.toList());
        }
        List<Question> questions = questionMapper.selectByIds(questionIds);
        List<ExamPaperTitleItemStudentVM> examPaperTitleItemVMS = null;
        if (examPaperTitleItemObjects != null) {
            examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
                ExamPaperTitleItemStudentVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemStudentVM.class);
                List<QuestionStudentVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                    Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                    QuestionStudentVM questionStudentVM = modelMapper.map(question, QuestionStudentVM.class);
                    TextContent questionInfoTextContent = textContentService.selectById(question.getInfoTextContentId());
                    QuestionObject questionObject = JsonUtil.toJsonObject(questionInfoTextContent.getContent(), QuestionObject.class);
                    questionStudentVM.setTitle(questionObject.getTitleContent());
                    questionStudentVM.setAnalyze(questionObject.getAnalyze());
                    questionStudentVM.setItems(questionObject.getQuestionItemObjects().stream().map(o -> {
                        QuestionItemStudentVM questionItemStudentVM = modelMapper.map(o, QuestionItemStudentVM.class);
                        if (o.getScore() != null) {
                            questionItemStudentVM.setScore(ExamUtil.scoreToVM(o.getScore()));
                        }
                        return questionItemStudentVM;
                    }).collect(Collectors.toList()));
                    questionStudentVM.setItemOrder(i.getItemOrder());
                    return questionStudentVM;
                }).collect(Collectors.toList());
                tTitleVM.setQuestionItems(questionItemsVM);
                return tTitleVM;
            }).collect(Collectors.toList());
        }
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }
}