package com.can.easyquiz.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@Accessors(chain = true)
@Slf4j
public class Json {

    private static ObjectMapper objectMapper = null;


}
