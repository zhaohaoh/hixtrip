package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单主表
 * </p>
 *
 * @author hzh
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("order_info")
@ApiModel(value = "OrderInfo对象", description = "订单主表")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("买家id")
    @TableField("buyer_id")
    private Long buyerId;

    @ApiModelProperty("卖家id")
    @TableField("merchant_id")
    private Long merchantId;

    @ApiModelProperty("店铺id")
    @TableField("store_id")
    private Long storeId;

    @ApiModelProperty("交易订单号")
    @TableField("trade_order_id")
    private Long tradeOrdeId;

    @ApiModelProperty("订单状态")
    @TableField("status")
    private Long status;

    @ApiModelProperty("支付时间")
    @TableField("pay_time")
    private Long payTime;

    @ApiModelProperty("完结时间")
    @TableField("finish_time")
    private Long finishTime;

    @ApiModelProperty("订单生成时间")
    @TableField("order_time")
    private LocalDateTime orderTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("支付总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("优惠金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("实付金额")
    @TableField("pay_amount")
    private BigDecimal payAmount;
}
