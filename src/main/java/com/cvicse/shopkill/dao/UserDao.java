package com.cvicse.shopkill.dao;

import com.cvicse.shopkill.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author luozhe73
 */
@Repository
@Mapper
public interface UserDao {

    @Select("select * from User where id=#{id}")
    User getById(@Param("id") int id);
}
