package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 策略模式实现 支付成功，失败，支付中（重复支付）根据状态来执行不同的策略是不合理的。  应该根据不同的支付类型来执行不同的策略。
 *  根据状态没有必要用策略模式，没有意义，因为状态是固定的。  这里阐述为什么没有用支付状态使用策略模式的原因。反而用支付方式
 */
@Component
public class PayFactory implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static final Map<String, PayStrategy> USER_API_MAP = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //策略模式实现 支付成功，失败，支付中（重复支付）根据状态来执行不同的策略是不合理的。  应该根据不同的支付类型来执行不同的策略。
        //根据状态没有必要用策略模式，没有意义，因为状态是固定的
        Map<String, PayStrategy> userBeans = applicationContext.getBeansOfType(PayStrategy.class);
        userBeans.forEach((k, v) -> {
            USER_API_MAP.put(v.getType(), v);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public PayStrategy getStrategy(String type) {
        return USER_API_MAP.get(type);
    }

}