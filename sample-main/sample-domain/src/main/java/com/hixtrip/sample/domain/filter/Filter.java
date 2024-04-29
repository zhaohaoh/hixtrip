package com.hixtrip.sample.domain.filter;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;

public interface Filter {
    void doFilter(TradeOrderBO myContent, FilterChain myFilterChain);
}
