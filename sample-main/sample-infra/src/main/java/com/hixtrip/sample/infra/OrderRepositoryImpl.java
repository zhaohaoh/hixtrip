package com.hixtrip.sample.infra;

import cn.hutool.core.bean.BeanUtil;
import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.dataobject.TradeOrder;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    
    @Override
    public TradeOrderBO saveOrder(TradeOrderBO seckillOrder) {
//        SampleDOConvertor.INSTANCE.doToDomain();
        //工具不同思路作用一样，性能差距而已   这里的代码完全可以用代码生成定义好模板处理。
        TradeOrder orderDO = BeanUtil.toBean(seckillOrder, TradeOrder.class);
        orderMapper.insert(orderDO);
        // 这个会new一个对象，没必要吧。
        seckillOrder.setId(orderDO.getId());
        return seckillOrder;
//        return SampleDOConvertor.INSTANCE.doToDomain();
    }
    
    @Override
    public void updateById(TradeOrderBO order) {
        TradeOrder orderDO = BeanUtil.toBean(order, TradeOrder.class);
        orderMapper.updateById(orderDO);
    }
    
    @Override
    public TradeOrderBO getOrderBySn(String outTradeNo) {
        return null;
    }
}
