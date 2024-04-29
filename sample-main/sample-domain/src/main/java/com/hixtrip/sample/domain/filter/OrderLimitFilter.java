package com.hixtrip.sample.domain.filter;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import org.springframework.stereotype.Component;

// 举例  下单限制过滤器
@Component
public class OrderLimitFilter implements Filter {
    
    @Override
    public void doFilter(TradeOrderBO myContent, FilterChain chain) {
        chain.doFilter(myContent);
    }
}
