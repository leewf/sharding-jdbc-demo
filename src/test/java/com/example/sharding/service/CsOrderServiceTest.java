package com.example.sharding.service;

import com.example.sharding.domain.entity.CsOrder;
import com.example.sharding.domain.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CsOrderServiceTest {

    @Autowired
    CsOrderService csOrderService;

    @Test
    public void addCsOrder() {

        long i = 0L;

        for (;i<40;i++){
            CsOrder csOrder = CsOrder.builder().id(i).userId(1111L).amount(10).status(0).node((int)i%2).build();
//            CsOrder csOrder = CsOrder.builder().userId(1111L).amount(10).status(0).node(i<=19?0:1).build();
            csOrderService.addCsOrder(csOrder);
        }


    }

    @Test
    public void getCsOrderList() {
        System.out.println("csOrderList:"+csOrderService.getCsOrderList());
    }

    @Test
    public void findOrderById() {
        System.out.println("findOrderById:"+csOrderService.findOrderById(1194906498652708865L));
    }

    @Test
    public void findList() {
        System.out.println("findOrderById:"+csOrderService.findList());
    }
}