package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.CommonNotifyResult;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WxPayStrategy implements PayStrategy {
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     *  1失败
     *  2重复
     *  3成功
     *  上面三种模式的处理可以定义一个模板方法，使用模板方式策略模式，每个支付方式都实现一个策略和上面3个方法的处理。这里先偷懒
     */
    @Override
    public String invoke(CommandPay commonNotifyResult) {
        //根据订单号获取订单
        TradeOrderBO order = orderRepository.getOrderBySn(commonNotifyResult.getOutTradeNo());
        // List<OrderDetail> orderDetails = orderService.ListOrderDetailBySn(result.getOutTradeNo());
        //失败
        if (commonNotifyResult.getTradeStatus() != null && "FAIL"
                .equalsIgnoreCase(commonNotifyResult.getTradeStatus())) {
            log.error("[微信支付] 支付失败的回调");
            //充血模型
            order.orderPayFail(order.getId());
        }
        //还有其他状态，这里就不处理了  就只处理成功  重复
        if (order.getPayTime() != null && order.getPayTime() > 0) {
            System.out.println("[支付服务] 订单已经支付");
        }
        //
        //        List<Long> skuIdList = orderDetails.stream().map(OrderDetail::getSkuId).collect(Collectors.toList());
        //        //获取促销商品
        //        List<Sku> skuList = skuService.listByIds(skuIdList);
        //        for (Sku sku : skuList) {
        //            for (OrderDetail orderDetail : orderDetails) {
        //                if (sku.getId().equals(orderDetail.getSkuId())) {
        //                    if (order.getStatus().equals(Order.Status.CANCEL.getValue())) {
        //                        //如果下单就减库存得话，延时队列关闭了未支付得订单回滚了库存.所以这里需要再次扣减库存
        //                        if ("order".equals(sku.getStockMode())) {
        //                            skuService.decrStock(orderDetail.getSkuId(),orderDetail.getNum());
        //                        }
        //                        log.info("订单被取消,但是用户已经支付成功。再次扣减库存");
        //                    }
        //                    if ("pay".equals(sku.getStockMode())) {
        //                        //如果扣减库存失败了
        //                        Boolean success = skuService.decrStock(orderDetail.getSkuId(),orderDetail.getNum());
        //                        if (!success){
        //                            log.error("付款减库存失败，超卖了商品的信息"+sku);
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }
        //充血模型  成功
        order.orderPaySuccess(order.getId());
        //增加商品销量
        //        productActivityService.increSkuListSale(orderDetails);
        
        return null;
    }
    
    @Override
    public String getType() {
        return "WX";
    }
}
