/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.web.aspect.AirjetResponseAspect
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/25/16 3:18 PM
 *
 */

package com.znl.web.aspect;



import com.znl.exception.WebExceptionEnum;
import com.znl.web.message.MicroServerResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/*@Aspect
@Component*/
public class MicroServerApiResponseAspect {

    private static Logger logger = LoggerFactory.getLogger(MicroServerApiResponseAspect.class);

    private static String formatBindingErrorMessages(BindingResult bindingResult) {

        /*
        return bindingResult.getAllErrors()
                .stream().map(err -> {
                    if (err instanceof FieldError && !err.getDefaultMessage().matches("<\\w*>")) {
                        return String.format("<%s> %s", ((FieldError) err).getField(), err.getDefaultMessage());
                    }
                    return err.getDefaultMessage();
                }).reduce("", (p, c) -> p + c + ", ");

        */


        StringBuilder builder = new StringBuilder(128);
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError && !error.getDefaultMessage().matches("<\\w*>")) {
                builder.append('<');
                builder.append(((FieldError) error).getField());
                builder.append("> ");
            }
            builder.append(error.getDefaultMessage());
            builder.append(',');
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    @Around("execution(* com.znl.web.controller.v1..*.*Action(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    MicroServerResponse response = new MicroServerResponse();
                    response.setStatus(WebExceptionEnum.ValidateError.getCode());
                    response.setMessage(formatBindingErrorMessages(bindingResult));
                    return response;
                }
            }
        }

        Object actionObject = point.proceed();

        if (actionObject instanceof MicroServerResponse) {
            return actionObject;
        } else {
            return new MicroServerResponse(actionObject);
        }
    }

}
