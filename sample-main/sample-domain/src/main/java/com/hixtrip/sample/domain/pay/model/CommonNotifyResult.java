package com.hixtrip.sample.domain.pay.model;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hzh
 * @date 2021/1/25 17:35
 * 支付回调结果类封装
 */
@Data
public class CommonNotifyResult implements Serializable {
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
