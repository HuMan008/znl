package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.config.define.PageInfo;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.model.repository.JPAAppRepository;
import cn.gotoil.znl.service.AppService;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wh on 2017/4/19.
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private JPAAppRepository jpaAppRepository;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public  App findOne(String recordID){

        String appStr = stringRedisTemplate.opsForValue().get(recordID);
        if(StringUtils.isEmpty(appStr)){
            App app =  jpaAppRepository.findOne(recordID);
            stringRedisTemplate.opsForValue().set(recordID,new Gson().toJson(app),7200, TimeUnit.SECONDS);
            return  app;
        }else{
            return  new Gson().fromJson(appStr,App.class);
        }

    }

    public boolean updateStatus(String recordID){

        App app = jpaAppRepository.findOne( recordID );
        if( app.getState()== App.StateEnum.Enable.getCode() ){
            app.setState(App.StateEnum.Disable.getCode());
        }else if( app.getState()== App.StateEnum.Disable.getCode() ){
            app.setState(App.StateEnum.Enable.getCode());
        }

        stringRedisTemplate.delete(recordID);

        return  jpaAppRepository.save(app)==null?false:true;

    }

    public PageInfo<App> getAppList(int pageNum,int pageSize, App condition){

        PageInfo<App> pageInfo = new PageInfo<App>();

        // 根据 ** 排序
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        // 组装页面
        Pageable pageable = new PageRequest( pageNum -1 , pageSize , sort);

        Page<App> queryPageInfo = jpaAppRepository.findAll(new Specification<App>() {
            public Predicate toPredicate(Root<App> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if(null==condition){
                    return predicate;
                }
                //
                if( App.StateEnum.getNameByCode(condition.getState()) != null ){
                    expressions.add(cb.equal(root.<String>get("state"), condition.getState() ));
                }
                if(StringUtils.isNotEmpty(condition.getName())){
                    expressions.add(cb.like(root.<String>get("name"),"%"+ condition.getName() +"%"));
                }
                return predicate;
            }
        }, pageable);

        pageInfo.setList(queryPageInfo.getContent());
        pageInfo.setPageNum(pageNum);
        pageInfo.setTotalPages(queryPageInfo.getTotalPages());

        return pageInfo;

    }

    public  boolean  save(App app){

        if(StringUtils.isEmpty(app.getAppID())){
            app.setAppkey(  RandomStringUtils.randomAlphanumeric(24 )  );
        }
        return  null==jpaAppRepository.save(app)?false:true;

    }

}
