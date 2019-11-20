package com.example.sharding.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/12
 */

@Data
@Builder
public class UserInfo implements Serializable {

    private Long id;

    private String userName;

    private Integer account;

    private String password;
}
