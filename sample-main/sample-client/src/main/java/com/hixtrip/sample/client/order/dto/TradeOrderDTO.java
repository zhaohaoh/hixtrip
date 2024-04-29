package com.hixtrip.sample.client.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeOrderDTO {
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

 
/*
额外字段
*/
}