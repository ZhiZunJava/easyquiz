package com.can.easyquiz.repository;

import com.can.easyquiz.domain.UserToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper extends BasicMapper<UserToken> {

    UserToken getToken(String token);

}