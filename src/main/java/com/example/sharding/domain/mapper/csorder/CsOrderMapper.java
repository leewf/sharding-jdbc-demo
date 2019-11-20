package com.example.sharding.domain.mapper.csorder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sharding.domain.entity.CsOrder;
import com.example.sharding.domain.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/12
 */
@Mapper
public interface CsOrderMapper extends BaseMapper<CsOrder> {
    OrderVO findOrderById(@Param("orderId") Long orderId);

    List<OrderVO> findList();
}
