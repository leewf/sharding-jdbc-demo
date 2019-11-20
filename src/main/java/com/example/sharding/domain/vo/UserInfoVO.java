package com.example.sharding.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/19
 */
@Data
@Builder
public class UserInfoVO {

    private Long id;
    private String userName;
    private Integer account;

}
