package com.cvicse.shopkill.dao;

import com.cvicse.shopkill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author luozhe73
 */
@Repository
@Mapper
public interface MiaoshaUerDao {

    @Select("select * from miaosha_user where id=#{id}")
    MiaoshaUser getById(@Param("id") long id);
}
