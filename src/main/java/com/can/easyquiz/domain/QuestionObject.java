package com.can.easyquiz.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionObject {

    private String titleContent;

    private String analyze;

    private List<QuestionItemObject> questionItemObjects;

    private String correct;

}