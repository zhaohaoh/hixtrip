package com.hixtrip.sample.domain.order;

import cn.hutool.core.util.IdUtil;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.filter.Filter;
import com.hixtrip.sample.domain.filter.FilterChainProxy;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.bo.OrderInfoBO;
import com.hixtrip.sample.domain.order.bo.OrderInfoItemBO;
import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.redismq.utils.RedisMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RedisMQTemplate redisMQTemplate;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private final List<Filter> filters = new ArrayList<>();
    /**
     * todo 需要实现
     * 创建待付款订单
     * 主要给出业务思路，大部分功能省略代码。
     * 1.校验下单商品信息,上下架,商品特殊属性，是否满足下单条件，商品限购等等特殊规则
     * 2.校验下单金额，商品金额是否发生改变,是否满足下单金额
     * 3.校验商品优惠券，优惠活动是否开启，是否可用优惠券，优惠券金额是否满足，
     * 4.用户限购校验
     * 5.下单渠道校验是否合法，
     * 6.校验库存和扣减库存,事务提交后异步扣减数据库库存
     * 7.组装下单数据
     * 8.保存订单
     * 9.记录交易账本流水
     * 10.发送下单消息到mq自动关闭订单
     * 11.下单成功后发送MQ时间驱动消息。
     *  备注： 1.2.3.4.5  通过责任链去做校验
     */
    public TradeOrderBO createOrder(TradeOrderBO order) {
        //责任链模式进行单一职责校验
        FilterChainProxy filterChainProxy = new FilterChainProxy(filters);
        filterChainProxy.doFilter(order);
    
        //后续下单流程
        
        OrderInfoBO orderInfo = order.getOrderInfo();
        List<OrderInfoItemBO> orderInfoItems = order.getOrderInfoItems();
        //需要你在infra实现, 自行定义出入参
        /**
         * 1.获取redis中的商品信息与库存信息,并进行判断
         * 2.执行redis的预扣减库存操作,并获取扣减之后的库存值
         * 3.如果扣减之后的库存值<=0,则删除redis中响应的商品信息与库存信息
         * 4.基于mq完成mysql的数据同步,进行异步下单并扣减库存(mysql)
         */
        
        //这里假设只买一个商品吧就不循环了
        Optional<OrderInfoItemBO> first = orderInfoItems.stream().findFirst();
        OrderInfoItemBO orderInfoItemBO = first.get();
        Long skuId = orderInfoItemBO.getSkuId();
        //模拟校验商品动作
//        PromotionLimitGoods seckillGoods = commodityDomainService.getBySkuIdAndType(skuId, PromotionType.SECKILL.ordinal());
//        if (seckillGoods == null) {
//            ExceptionPublish.publish("商品不存在");
//        }
        // 秒杀活动表模拟
//        PromotionSeckill seckill = RedisUtils.get(PROMOTION_SECKILL + seckillGoods.getPromotionId(), PromotionSeckill.class);
//        if (seckill==null||seckill.getStartTime().isAfter(LocalDateTime.now())){
//            throw new RuntimeException("活动尚未开始");
//        }
        // 防止用户恶意刷单 10秒只能点一次
//        duplicateCommit(seckillGoods.getSkuId(), userId);
    
        inventoryDomainService.changeInventory(skuId,1L,1L,1L);
        
        //TODO订单商品金额校验  还有获取商品各种信息填充到订单信息里 省略
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
    
        //创建秒杀订单
        order.setId(IdUtil.getSnowflakeNextId());
        //省略了商品金额+数量和传入的订单金额+数量进行校验 过程。直接取商品金额填充。
        order.setPayAmount(skuPrice.multiply(BigDecimal.valueOf(orderInfoItemBO.getBuyNum())));
      
        order.setOrderTime(LocalDateTime.now());
        //未支付和创建订单枚举
        order.setStatus(0);
   
        //秒杀订单延时1分钟后不付款自动关闭。秒杀特殊场景
//          orderRepository.saveOrder(order);
//        //订单详情  这里是模拟订单详情的数据， OrderInfoItemBO 和 OrderInfoBO的表的 这个动作省略不做
 
        
    
        //模拟付钱 动作 调用三方接口
//        SeckillOrderVo seckillOrderVo = new SeckillOrderVo();
//        seckillOrderVo.setId(seckillOrder.getId());
//        seckillOrderVo.setOrderNo(seckillOrder.getSn());
//        if (seckillOrderDto.getType().equals(PayTypeEnum.WX.getValue())) {
//            Object payOrder = orderService.createWxPayOrder(seckillOrder , seckillOrderDto.getPayType(), ip);
//            seckillOrderVo.setPayOrder(payOrder);
//        }
//        else if (seckillOrderDto.getType().equals(PayTypeEnum.ALI.getValue())) {
//            Object payOrder = orderService.createAliPayOrder(seckillOrder , seckillOrderDto.getPayType(), ip);
//            seckillOrderVo.setPayOrder(payOrder);
//        }
//        else {
//            throw new BussinessException("支付方式不存在");
//        }
        
        //TODO 交易账本记录  动作省略
        
        
        //发送mq  X分钟后自动关单，这里可以根据商品配置的属性进行自定义时长关闭订单  自研框架
        redisMQTemplate.sendDelayMessage(order,"queue",Duration.ofSeconds(30));
        //发送kafka或者rocketmq下单完成
        return order;
    }

    /**
     * todo 需要实现  已经用充血模型处理了 TradeOrder对象
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现  已经用充血模型处理了
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }
    
    
    private void duplicateCommit(Long goodsId, Long userId) {
//        String redisKey = "seckill.duplicate.goodsId.userId" + goodsId + "::" + userId;
//        long count = RedisUtils.increment(redisKey, 1);
//        if (count == 1) {
//            //代表当前用户是第一次访问.
//            //对当前的key设置一个五分钟的有效期
//            RedisUtils.expire(redisKey, 10, TimeUnit.SECONDS);
//        } else {
//            ExceptionPublish.publish("下单过于频繁");
//        }
    }
    
   
}
