package com.hixtrip.sample.domain.order.bo;

import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.utli.BeanStaticFactory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TradeOrderBO  {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "买家id")
    private Long buyerId;
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "支付时间 0未支付")
    private Long payTime;
    @ApiModelProperty(value = "创建时间，下单时间")
    private LocalDateTime orderTime;
    @ApiModelProperty(value = "订单完结时间")
    private Long finishTime;
    @ApiModelProperty(value = "支付方式 WX 等等")
    private String payType;
    @ApiModelProperty(value = "下单渠道")
    private String orderChannel;
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "优惠总金额")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal payAmount;
//    订单
    private OrderInfoBO orderInfo;
//    订单商品项
    private List<OrderInfoItemBO> orderInfoItems;
    
    //充血模型
    public void orderPaySuccess(Long orderId){
        TradeOrderBO order = new TradeOrderBO();
        //成功
        order.setStatus(2);
        order.setPayTime(System.currentTimeMillis());
        order.setId(orderId);
        OrderRepository orderRepository = BeanStaticFactory.get(OrderRepository.class);
        orderRepository.updateById(order);
    }
    
    
    //充血模型
    public void orderPayFail(Long orderId){
        TradeOrderBO order = new TradeOrderBO();
        //就当失败吧。枚举类不建了
        order.setStatus(3);
        order.setId(orderId);
        OrderRepository orderRepository = BeanStaticFactory.get(OrderRepository.class);
        orderRepository.updateById(order);
    }
}
