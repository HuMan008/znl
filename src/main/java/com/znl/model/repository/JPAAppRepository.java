package com.znl.model.repository;

import com.znl.model.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAAppRepository extends JpaRepository<App, String> {



}
