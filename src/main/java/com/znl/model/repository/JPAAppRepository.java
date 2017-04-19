package com.znl.model.repository;

import com.znl.model.domain.App;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAAppRepository extends JpaRepository<App, String> {


    Page<App> findAll(Specification<App> condition, Pageable pageable );

}
