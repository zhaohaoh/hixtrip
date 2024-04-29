package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {
    
    /**
     * 第三方订单号
     */
    private String outTradeNo;
    
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    
    /**
     *自定义的信息原路返回
     */
    private String attach;
    
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 自己应用的订单号
     */
    private String tradeNo;
    
    /**
     * 结果状态
     */
    private String tradeStatus;
    
    /**
     * 支付类型
     */
    private Integer type;


}
