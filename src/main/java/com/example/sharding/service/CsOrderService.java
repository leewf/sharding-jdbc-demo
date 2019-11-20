package com.example.sharding.service;

import com.example.sharding.domain.entity.CsOrder;
import com.example.sharding.domain.entity.UserInfo;
import com.example.sharding.domain.vo.OrderVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/14
 */
public interface CsOrderService {
    public void addCsOrder(CsOrder csOrder);
    public List<CsOrder> getCsOrderList();


    public OrderVO findOrderById(Long orderId);

    List<OrderVO> findList();


}
