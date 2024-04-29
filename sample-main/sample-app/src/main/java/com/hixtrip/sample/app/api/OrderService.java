package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.TradeOrderDTO;
import com.hixtrip.sample.domain.order.bo.TradeOrderBO;

/**
 * 订单的service层
 */
public interface OrderService {
    
    
    
    TradeOrderBO createOrder(CommandOderCreateDTO commandOderCreateDTO);
    
}
