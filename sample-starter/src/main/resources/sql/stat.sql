-- ----------------------------
-- Table structure for order_statistics
-- ----------------------------
DROP TABLE IF EXISTS `order_statistics`;
CREATE TABLE `order_statistics`  (
                                     `id` bigint unsigned NOT NULL COMMENT '主键',
                                     `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '买家id',
                                     `merchant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商户id',
                                     `num` bigint(0) NOT NULL DEFAULT 0 COMMENT '统计的数量',
                                     `statistics_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '统计的业务类型,用户,渠道,商品,地区等等',
                                     `statistics_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '统计的值 一般来说存储id',
                                     `day_time` int(0) NOT NULL DEFAULT 0 COMMENT '天时间',
                                     `hour_time` int(0) NOT NULL DEFAULT 0 COMMENT '小时时间',
                                     `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单统计表' ROW_FORMAT = Dynamic;

1.采用mysql+redis策略
做买家卖家订单或者商品排行榜首先要统计订单或者商品数量。这个表用来异步统计数量。可以指定不同的维度。
那么后续根据不同的维度来查询统计数量进行排序。这个表的数据在并发比较高的情况下，可以先操作redis自增数量，每分钟从redis中读取数据同步写入到mysql中。
后续给到用户的卖家卖家排行榜也可以把mysql的数据定时加载到redis中，这样查询排行榜数据的时候直接从redis中读取。

具体方案如下
1.下单成功异步发送mq
2.接受到mq通知把自增数量往redis中记录指定统计的key+日期（分钟）
3.每分钟定时任务轮询读取数据，写入mysql
4.定时任务轮询mysql数据加载商户id的key中，存储排行榜数据
5.查询商户id排行榜redis的key即可。排行榜一般来说数据量不大，无需分页直接返回100条。