package com.example.sharding.domain.entity;


import lombok.Builder;
import lombok.Data;
/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/13
 */
@Data
@Builder
public class CsOrder {

    private Long id;
    private Long userId;

    private Integer amount;

    private Integer status;
    //分表节点
    private Integer node;

}
