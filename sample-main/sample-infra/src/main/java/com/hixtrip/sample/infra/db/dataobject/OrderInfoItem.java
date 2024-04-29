package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单商品明细表
 * </p>
 *
 * @author hzh
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("order_info_item")
@ApiModel(value = "OrderInfoItem对象", description = "订单商品明细表")
public class OrderInfoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("店铺id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("spuId")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("sku")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("购买数量")
    @TableField("buy_num")
    private Integer buyNum;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty("商品快照id")
    @TableField("product_snapshot_id")
    private Long productSnapshotId;

    @ApiModelProperty("订单状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("退款状态")
    @TableField("refund_status")
    private Integer refundStatus;

    @ApiModelProperty("优惠金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("实付金额")
    @TableField("pay_amount")
    private BigDecimal payAmount;

    @ApiModelProperty("总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("退款金额")
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    @ApiModelProperty("退货数量")
    @TableField("refund_num")
    private Integer refundNum;

    @ApiModelProperty("加购数量")
    @TableField("add_num")
    private Integer addNum;
}
