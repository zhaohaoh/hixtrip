package com.hixtrip.sample.domain.filter;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import org.springframework.stereotype.Component;

// 举例  商品校验过滤器
@Component
public class ProductVerifyFilter implements Filter {
    
    @Override
    public void doFilter(TradeOrderBO myContent, FilterChain chain) {
        chain.doFilter(myContent);
    }
}
