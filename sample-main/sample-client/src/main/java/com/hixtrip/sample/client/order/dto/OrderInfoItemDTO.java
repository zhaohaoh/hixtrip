package com.hixtrip.sample.client.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoItemDTO {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "店铺id")
    private Integer storeId;
    @ApiModelProperty(value = "spuId")
    private Integer spuId;
    @ApiModelProperty(value = "sku")
    private Integer skuId;
    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;
    @ApiModelProperty(value = "订单id")
    private Integer orderId;
    @ApiModelProperty(value = "商品快照id")
    private Long productSnapshotId;
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;
    @ApiModelProperty(value = "退货数量")
    private Integer refundNum;
    @ApiModelProperty(value = "加购数量")
    private Integer addNum;
 
/*
额外字段
*/
}