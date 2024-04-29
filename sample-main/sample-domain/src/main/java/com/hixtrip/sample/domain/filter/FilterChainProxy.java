package com.hixtrip.sample.domain.filter;


import com.hixtrip.sample.domain.order.bo.TradeOrderBO;

import java.util.List;

public class FilterChainProxy implements FilterChain {
    private final List<Filter> filters;
    private int currentPosition = 0;

    public FilterChainProxy(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(TradeOrderBO myContent) {
        if (filters==null ||filters.size()<=0 || currentPosition >= filters.size()) {
            return;
        }

        Filter filter = filters.get(currentPosition);
        currentPosition++;
        filter.doFilter(myContent, this);
    }
}
