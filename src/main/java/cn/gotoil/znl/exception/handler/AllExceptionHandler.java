package cn.gotoil.znl.exception.handler;



import cn.gotoil.znl.exception.MicroServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AllExceptionHandler implements HandlerExceptionResolver, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AllExceptionHandler.class);


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("RestExceptionHandler.resolveException:{}", ex);
        if(ex instanceof BindException){
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(view);
            modelAndView.addObject("status", 666);
            List<ObjectError> list  =((BeanPropertyBindingResult) ((BindException) ex).getBindingResult()).getAllErrors();
            StringBuilder sb = new StringBuilder();
            list.stream().forEach(o->{
                sb.append(o.getDefaultMessage()) .append("\n</br>") ;
            });
            modelAndView.addObject("message", sb.toString());
            return modelAndView;
        }

        if (ex instanceof MicroServerException) {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(view);
            MicroServerException microServerException = (MicroServerException) ex;
            modelAndView.addObject("status", microServerException.getTickcode());
            modelAndView.addObject("message", microServerException.getMessage());
            return modelAndView;
        }

        if (ex instanceof NoHandlerFoundException) {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(view);
            modelAndView.addObject("status", 404);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        modelAndView.addObject("status", 500);
        modelAndView.addObject("message", ex.getMessage());

        return modelAndView;
    }


}