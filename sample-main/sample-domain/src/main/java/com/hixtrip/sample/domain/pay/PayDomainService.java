package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付领域服务
 * todo 不需要具体实现, 直接调用即可
 */
@Component
public class PayDomainService {
    @Autowired
    private PayFactory payFactory;

    /**
     * 记录支付回调结果
     * 【高级要求】至少有一个功能点能体现充血模型的使用。 在TradeOrderBO中体现
     */
    public void payRecord(CommandPay commandPay) {
        PayStrategy strategy = payFactory.getStrategy(commandPay.getType().toString());
        strategy.invoke(commandPay);
        //无需实现，直接调用即可
        //        List<TransactionRecord> recordList=new ArrayList<>();
        //        for (OrderDetail orderDetail : orderDetails) {
        //            TransactionRecord record = new TransactionRecord();
        //            BeanUtils.copyProperties(order, record);
        //            record.setCreateTime(LocalDateTime.now());
        //            record.setItemId(orderDetail.getSkuId());
        //            record.setOrderId(order.getId());
        //            record.setTransactionId(result.getTradeNo());
        //            record.setType(TransactionRecord.Type.PAYMENT.getValue());
        //            record.setPayType(result.getType());
        //            recordList.add(record);
        //        }
        //        //异步写入交易记录表
        //        mq.sendMessage(MessageUtils.createWrapper(recordList,
        //                RabbitConfig.SHOP_BUSINESS_EXCHANGE, RabbitConfig.PAY_LOG_KEY));
    }
}
