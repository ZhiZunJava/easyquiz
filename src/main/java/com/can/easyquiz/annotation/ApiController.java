package com.can.easyquiz.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(TYPE)
@Retention(RUNTIME)
@Inherited
@Documented
@RestController
@RequestMapping
public @interface ApiController {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String value();
}