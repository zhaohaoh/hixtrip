package com.hixtrip.sample.domain.mq;

import com.hixtrip.sample.domain.order.bo.TradeOrderBO;
import com.redismq.RedisListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@Slf4j
public class OrderMqListener {
 
    /**
     * 消息消费  正常不应该传整个消息的body，应该只传消息的id。这里为了打印出来方便看
     */
    @RedisListener(queue = "queue",delay = true)
    public void test1(TradeOrderBO test) {
      log.info("自动关闭订单执行 :"+test);
    }
}
