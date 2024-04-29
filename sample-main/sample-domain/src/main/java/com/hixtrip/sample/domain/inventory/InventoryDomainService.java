package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {
    @Autowired
    private InventoryRepository inventoryRepository;
  

    /**
     *    这里我不理解为什么要获取可售库存，如果从业务层面传可售库存和占用库存进行计算。这里是没有办法保证库存扣减的原子性的。
     *      计算库存的操作一定是在redis内部完成才能保证原子性。如果可售库存到了应用层面，
     *       那么应用层的库存值和redis的库存值存在一致性问题
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return null;
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     *
     *      这里我不理解为什么要传进来可售库存，如果从业务层面传可售库存和占用库存进行计算。这里是没有办法保证库存扣减的原子性的。
     *      计算库存的操作一定是在redis内部完成才能保证原子性。如果可售库存到了应用层面，
     *      那么应用层的库存值和redis的库存值存在一致性问题
     *
     *      按照架构这里的代码应该写在OrderRepository层。
     * @return
     */
    public Boolean changeInventory(Long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
    
        inventoryRepository.changeInventory(skuId);
        return true;
    }
    
    

}
