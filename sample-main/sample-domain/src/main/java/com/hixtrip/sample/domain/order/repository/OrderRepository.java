package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;

/**
 *
 */
public interface OrderRepository {
    
    TradeOrderBO saveOrder(TradeOrderBO seckillOrder);
    
    void updateById(TradeOrderBO order);
    
    TradeOrderBO getOrderBySn(String outTradeNo);
}
