package com.example.sharding.userinterface.controller;

import com.example.sharding.domain.vo.OrderVO;
import com.example.sharding.service.CsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/20
 */
@Controller
@RequestMapping("/order/")
public class CsOrderController {


    @Autowired
    CsOrderService csOrderService;


    @RequestMapping(value = {"/list"})
    @ResponseBody
    public List<OrderVO> findList() {
        return csOrderService.findList();
    }
}

