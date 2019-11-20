package com.example.sharding.domain.mapper.userinfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sharding.domain.entity.UserInfo;
import com.example.sharding.domain.vo.OrderVO;
import com.example.sharding.domain.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/12
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfoVO findUserById(@Param("userId") Long userId);

}
