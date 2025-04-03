package com.can.easyquiz.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExamPaperAnswerUpdate {
    private Integer id;
    private Integer customerScore;
    private Boolean doRight;
}