package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Map;

@Mapper
public interface UserMapper {
    
    /*
    * 根据openid查询数据
    */
    @Select("select * from User where openid = #{openid}")
    User getByOpenid(String openid);

    void insert(User user);

    /**
     * 根据id查询数据
         * @param userId
     * @return
     */
    @Select("select * from User where id = #{userId}")
    User getByid(Long userId);

    /**
     * 根据动态sql查询用户数量和新增用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
