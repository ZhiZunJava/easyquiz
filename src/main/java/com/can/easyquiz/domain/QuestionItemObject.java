package com.can.easyquiz.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionItemObject {

    private String prefix;

    private String content;

    private Integer score;

    private String itemUuid;

}
