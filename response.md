围绕订单，给出相关存储设计。 (库存、商品等无需考虑) \
背景: 存量订单10亿, 日订单增长百万量级。 \
主查询场景如下:  \
1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。 \
2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。 \
3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。 \
4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。 \
resources/sql中给出整体设计方案。包含存储基础设施选型, DDL（基础字段即可）, 满足上述场景设计思路。 \

背景: 存量订单10亿, 日订单增长百万量级。

 
解答：1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。 \

根据背景，日订单百万。1个月3000万。1年大约3亿左右,半年大约1亿5。应该优先考虑归档，半年归档，写入历史订单。商户买家查询完全可以通过时间段进行查询到指定库。
这样实时半年订单是1.5亿左右。每天200万的话那也就3亿。3亿订单，合理利用索引进行简单查询，完全可以满足需求，如果不满足，
可以利用mysql内部的分区，对买家id进行分区。分区后单买家数据隔离，并且走索引。是可以满足并发的。
方案2 如果就是存量订单10亿，也就是半年就有10亿，那么考虑买家id进行分库分表。分表后，单买家数据隔离，并且走索引。是可以满足并发要求和实时性要求的。

2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。 \

允许延迟，也有两个方案
方案1：由于主表根据买家id进行分库分表，所以卖家查询订单，也需要卖家id分库分表。优先保存买家id分库分表的数据，然后通过binlog等方式异步同步到卖家订单库表。
方案2: 这种需求场景直接上elasticsearch,可以实现各种复杂的查询。 此种方案优先级更高，复杂度更低。

3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。 \
方案1.这种场景直接上elasticsearch,可以实现各种复杂的查询。
方案2。如果不上es，那只需要对订单主表保存的时候异步保存到一个客诉订单表即可，这个表只保存半年的增量订单，定期删除历史订单即可。 

4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。 \
   resources/sql中给出整体设计方案。包含存储基础设施选型, DDL（基础字段即可）, 满足上述场景设计思路。 \
   
方案；   看stat.sql文件。

总结:通过主要es+mysql+redis实现,在买家频繁查询方面通过分库分表保证实时性。


4. 【40分】库存扣减只在缓存实现, 假设业务为秒杀场景，需要考虑高并发(100每秒)，避免超卖。要求无锁设计。

通过redis单线程lua脚本原子性操作扣减库存，这种方式是无锁的。并且redis内存操作支持高并发。且不会超卖，而且数据库层面肯定会加上乐观锁的机制。
所以超卖是不可能的。那么主要解决的就是并发，那就是通过redis的自减保证无锁。但是实际上的场景更加复杂，会有商家同步编辑商品库存的操作。
那想要无锁就很麻烦，但也不是不可能。
但是这里提出业务为秒杀场景。那么这里可以从业务层面入手考虑，而不是技术层面。因为秒杀活动，可以限制客户提前设置库存，并且在秒杀活动开始的时候
应该是不允许修改库存的。那可以解决商家并发修改库存的问题。那通过redis保障就没有问题。
但如果不是秒杀活动，而且库存还有针对日期，小时的规则，那么设计上会更加复杂，秒杀其实相对简单。