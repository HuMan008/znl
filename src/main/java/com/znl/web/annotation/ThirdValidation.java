/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.web.annotation.ThirdValidation
 *
 * Suyj <yizhishita@126.com>,  September 2016
 *
 * LastModified: 9/13/16 9:46 AM
 *
 */

package com.znl.web.annotation;



import com.znl.web.annotation.launcher.ThridValidationLauncher;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ThridValidationLauncher.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThirdValidation {
    String message() default "Validation Faild.";

    String className() default "";

    String methodName() default "";

    boolean staticMethod() default false;

    String regex() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
