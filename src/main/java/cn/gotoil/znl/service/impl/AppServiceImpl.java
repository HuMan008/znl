package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.config.define.PageInfo;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.model.repository.JPAAppRepository;
import cn.gotoil.znl.service.AppService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by wh on 2017/4/19.
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private JPAAppRepository jpaAppRepository;




    public  App findOne(String recordID){
        return  jpaAppRepository.findOne(recordID);
    }

    public boolean updateStatus(String recordID){

        App app = jpaAppRepository.findOne( recordID );
        if( app.getState()== App.StateEnum.Enable.getCode() ){
            app.setState(App.StateEnum.Disable.getCode());
        }else if( app.getState()== App.StateEnum.Disable.getCode() ){
            app.setState(App.StateEnum.Enable.getCode());
        }

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
