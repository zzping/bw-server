package com.lantu.mapper;

import com.lantu.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Trig
 * @create 2019-04-12 23:57
 */
@Mapper
public interface UserMapper {

    User loadUserByUsername(String username);
}
