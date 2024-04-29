package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {
    // 直接用常量
    private static final String SECKILL_GOODS_STOCK_KEY = "SECKILL_GOODS_STOCK_KEY";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    //只是先扣减库存，没做增加库存。  原理类似。  这里用redis绝对不会超卖。数据库层面还有数据库乐观锁的兜底方案
    @Override
    public void changeInventory(Long skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        //先模拟设置个库存  后续可以自己修改redis的值
        //redisTemplate.opsForValue().setIfAbsent(SECKILL_GOODS_STOCK_KEY + skuId, 10);
        redisTemplate.opsForValue().setIfAbsent(SECKILL_GOODS_STOCK_KEY + skuId, 10);
        String luaScript = buildLuaScript();
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        //lua判断库存 先get <=0直接返回   保证在一个事务内。不用lua的话多个线程会获得同一个库存值
        //因为不能引入redisson直接写lua了，redisson有api可以实现类似的    实际业务上还有编辑库存的操作。逻辑复杂。
        Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList(SECKILL_GOODS_STOCK_KEY + skuId));
    
        if (stock == null || stock < 0) {
            //等待3秒才返回被抢完
            throw new RuntimeException("很抱歉没有抢到");
        }
        
    }
    private String buildLuaScript() {
        //如果c大于传入的允许的请求次数，就返回这个数字。否则自增，增加完用户如果是1设置超时时长
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) <= 0 then" +
                "\nreturn -1;" +
                "\nend" +
                "\nc = redis.call('decr',KEYS[1])" +
                "\nreturn c;";
    }
}
