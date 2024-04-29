package com.hixtrip.sample.domain.filter;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;

/**
 * 过滤器链
 *
 * @author hzh
 * @date 2023/06/08
 */
public interface FilterChain {

    void doFilter(TradeOrderBO myContent);

}
