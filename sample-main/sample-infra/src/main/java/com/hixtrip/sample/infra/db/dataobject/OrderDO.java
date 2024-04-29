package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */

@Data
public class OrderDO {
    
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @ApiModelProperty("订单编号")
    @TableField("sn")
    private String sn;
    
    @ApiModelProperty("总金额")
    @TableField("total_money")
    private BigDecimal totalMoney;
    
    @ApiModelProperty("优惠金额")
    @TableField("discount_price")
    private BigDecimal discountPrice;
    
    @ApiModelProperty("支付金额")
    @TableField("price")
    private BigDecimal price;
    
    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;
    
    @ApiModelProperty("邮费")
    @TableField("postage")
    private BigDecimal postage;
    
    @ApiModelProperty("收货人地址")
    @TableField("receiver_address")
    private String receiverAddress;
    
    @ApiModelProperty("收货人电话")
    @TableField("receiver_phone")
    private String receiverPhone;
    
    @ApiModelProperty("收货人姓名")
    @TableField("receiver_name")
    private String receiverName;
    
    @ApiModelProperty("订单状态1未支付2已支付3已发货4交易完成5取消订单6申请退款7退款中8退款成功")
    @TableField("status")
    private Boolean status;
    
    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
    @ApiModelProperty("支付时间")
    @TableField("pay_time")
    private LocalDateTime payTime;
    
    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
    
    @ApiModelProperty("text")
    @TableField("text")
    private String text;
    
    @ApiModelProperty("商家id")
    @TableField("merchant_id")
    private Long merchantId;
    
    
}
