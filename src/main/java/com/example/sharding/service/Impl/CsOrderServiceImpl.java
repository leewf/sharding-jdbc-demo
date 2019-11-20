package com.example.sharding.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sharding.domain.entity.CsOrder;
import com.example.sharding.domain.entity.UserInfo;
import com.example.sharding.domain.mapper.csorder.CsOrderMapper;
import com.example.sharding.domain.mapper.userinfo.UserInfoMapper;
import com.example.sharding.domain.vo.OrderVO;
import com.example.sharding.service.CsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/14
 */
@Service
public class CsOrderServiceImpl implements CsOrderService {

    @Autowired
    private CsOrderMapper csOrderMapper;

    @Override
    public void addCsOrder(CsOrder csOrder) {
        csOrderMapper.insert(csOrder);

    }

    @Override
    public List<CsOrder> getCsOrderList() {
        List<CsOrder> csOrderList= csOrderMapper.selectList(new QueryWrapper<>());

        return csOrderList;
    }

    @Override
    public OrderVO findOrderById(Long orderId) {
        return csOrderMapper.findOrderById(orderId);
    }

    @Override
    public List<OrderVO> findList() {
        return csOrderMapper.findList();
    }
}
