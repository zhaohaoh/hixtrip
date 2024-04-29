package com.hixtrip.sample.client.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderInfoDTO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "买家id")
    private Long buyerId;
    @ApiModelProperty(value = "卖家id")
    private Long merchantId;
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "交易订单号")
    private Long tradeOrdeId;
    @ApiModelProperty(value = "订单状态")
    private Long status;
    @ApiModelProperty(value = "支付时间")
    private Long payTime;
    @ApiModelProperty(value = "完结时间")
    private Long finishTime;
    @ApiModelProperty(value = "订单生成时间")
    private LocalDateTime orderTime;
    @ApiModelProperty(value = "支付总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal payAmount;

 
/*
额外字段
*/
}