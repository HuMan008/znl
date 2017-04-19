package cn.gotoil.znl.exception.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class AllErrorHandler implements ErrorViewResolver {

    private static Logger logger = LoggerFactory.getLogger(AllErrorHandler.class);


    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        logger.error("ErrorViewResolver.resolveErrorView:{}", model);

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        modelAndView.addObject("status", model.get("status"));
        modelAndView.addObject("message", model.get("error"));

        return modelAndView;

    }
}
