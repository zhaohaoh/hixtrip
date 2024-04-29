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
 * 交易订单表 trade_order  1:N order_info
 * </p>
 *
 * @author hzh
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("trade_order")
@ApiModel(value = "TradeOrder对象", description = "交易订单表 trade_order  1:N order_info")
public class TradeOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("买家id")
    @TableField("buyer_id")
    private Long buyerId;

    @ApiModelProperty("订单状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("支付时间 0未支付")
    @TableField("pay_time")
    private Long payTime;

    @ApiModelProperty("创建时间，下单时间")
    @TableField("order_time")
    private LocalDateTime orderTime;

    @ApiModelProperty("订单完结时间")
    @TableField("finish_time")
    private Long finishTime;

    @ApiModelProperty("支付方式 WX 等等")
    @TableField("pay_type")
    private String payType;

    @ApiModelProperty("下单渠道")
    @TableField("order_channel")
    private String orderChannel;

    @ApiModelProperty("订单更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("优惠总金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("实付金额")
    @TableField("pay_amount")
    private BigDecimal payAmount;
}
