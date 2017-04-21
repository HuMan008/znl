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

package cn.gotoil.znl.web.aspect;



import cn.gotoil.bill.aspect.ApiResponseAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ZnlApiResponseAspect extends ApiResponseAspect {

    @Around("execution(* cn.gotoil.znl.web.controller..*.*Action(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return aroundCall(point);
    }


}
