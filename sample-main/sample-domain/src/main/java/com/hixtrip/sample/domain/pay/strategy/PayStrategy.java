package com.hixtrip.sample.domain.pay.strategy;


import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.CommonNotifyResult;

public interface PayStrategy {
    String invoke(CommandPay c);
    
    String getType();
 
}