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
public class OrderVO {
    private Long id;
    private Long userId;
    private Integer amount;
    private Integer status;
}
