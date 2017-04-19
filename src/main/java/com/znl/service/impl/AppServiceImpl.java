package com.znl.service.impl;

import com.znl.model.domain.App;
import com.znl.model.repository.JPAAppRepository;
import com.znl.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wh on 2017/4/19.
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private JPAAppRepository jpaAppRepository;

    public  boolean  save(App app){

        return  null==jpaAppRepository.save(app)?false:true;

    }

}
