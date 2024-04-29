package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.bo.OrderInfoBO;
import com.hixtrip.sample.domain.order.bo.OrderInfoItemBO;
import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import com.hixtrip.sample.infra.db.dataobject.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public TradeOrderBO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        //commandOderCreateDTO组装成order
        TradeOrderBO order = new TradeOrderBO();
        OrderInfoBO orderInfo = new OrderInfoBO();
        OrderInfoItemBO orderInfoItemBO = new OrderInfoItemBO();
        orderInfoItemBO.setSkuId(commandOderCreateDTO.getSkuId());
        orderInfoItemBO.setBuyNum(commandOderCreateDTO.getAmount());
    
        order.setBuyerId(commandOderCreateDTO.getUserId());
        order.setOrderInfo(orderInfo);
        order.setOrderInfoItems(Collections.singletonList(orderInfoItemBO));
        orderDomainService.createOrder(order);
        return order;
    }
}
