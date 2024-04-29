/*
 Navicat Premium Data Transfer

 Source Server         : hzh
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : interview

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 29/04/2024 15:22:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_carriage
-- ----------------------------
DROP TABLE IF EXISTS `order_carriage`;
CREATE TABLE `order_carriage`  (
                                   `id` int(0) NOT NULL COMMENT '主键',
                                   `order_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单号id',
                                   `carriage_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '运单号',
                                   `carriage_status` int(0) NOT NULL DEFAULT 0 COMMENT '运单状态1.生成中 2.生成成功 3.同步中 4生成失败',
                                   `carriage_track_status` int(0) NOT NULL DEFAULT 0 COMMENT '物流状态 1.创建 2.发货 3.揽收 4.中转 5.签收 6.其他 跟踪超期或无需跟踪',
                                   `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `track_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'api.51tracking 获取的物流信息',
                                   `carriage_version` int(0) NOT NULL DEFAULT 0 COMMENT '运单版本号，申请运单信息记录在物流处',
                                   `logistics_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '物流运营信息id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单物流运输信息表,次要表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
                               `id` bigint(0) NOT NULL COMMENT '主键',
                               `buyer_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '买家id',
                               `merchant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '卖家id',
                               `store_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '店铺id',
                               `trade_order_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '交易订单号',
                               `status` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单状态',
                               `pay_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '支付时间',
                               `finish_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '完结时间',
                               `order_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单生成时间',
                               `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                               `total_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '支付总金额',
                               `coupon_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
                               `pay_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `idx_buyer_id`(`buyer_id`, `order_time`) USING BTREE,
                               INDEX `idx_merchant_id`(`merchant_id`, `order_time`) USING BTREE,
                               INDEX `idx_store_id`(`store_id`, `order_time`) USING BTREE,
                               INDEX `idx_order_time`(`order_time`) USING BTREE,
                               INDEX `idx_trade_order_id`(`trade_order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_info_item
-- ----------------------------
DROP TABLE IF EXISTS `order_info_item`;
CREATE TABLE `order_info_item`  (
                                    `id` int(0) NOT NULL COMMENT '主键',
                                    `store_id` int(0) NOT NULL DEFAULT 0 COMMENT '店铺id',
                                    `spu_id` int(0) NOT NULL DEFAULT 0 COMMENT 'spuId',
                                    `sku_id` int(0) NOT NULL DEFAULT 0 COMMENT 'sku',
                                    `buy_num` int(0) NOT NULL DEFAULT 0 COMMENT '购买数量',
                                    `order_id` int(0) NOT NULL DEFAULT 0 COMMENT '订单id',
                                    `product_snapshot_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品快照id',
                                    `status` int(0) NOT NULL DEFAULT 0 COMMENT '订单状态',
                                    `refund_status` int(0) NOT NULL DEFAULT 0 COMMENT '退款状态',
                                    `coupon_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
                                    `pay_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
                                    `total_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
                                    `refund_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '退款金额',
                                    `refund_num` int(0) NOT NULL DEFAULT 0 COMMENT '退货数量',
                                    `add_num` int(0) NOT NULL DEFAULT 0 COMMENT '加购数量',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单商品明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item_track
-- ----------------------------
DROP TABLE IF EXISTS `order_item_track`;
CREATE TABLE `order_item_track`  (
                                     `id` bigint(0) NOT NULL COMMENT '主键',
                                     `sku_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'sku',
                                     `order_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单id',
                                     `action` int(0) NOT NULL DEFAULT 0 COMMENT '1下单 2支付 3修改 4申请取消 5取消成功 6申请加购 7加购成功 8过期  9完结 10 售后',
                                     `source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作的来源客户端',
                                     `op_num` int(0) NOT NULL DEFAULT 0 COMMENT '操作数量',
                                     `remaining_num` int(0) NOT NULL DEFAULT 0 COMMENT '剩余数量',
                                     `op_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '操作用户id',
                                     `ext_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '扩展信息存储',
                                     `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for order_track
-- ----------------------------
DROP TABLE IF EXISTS `order_track`;
CREATE TABLE `order_track`  (
                                `id` bigint(0) NOT NULL COMMENT '主键',
                                `sku_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'sku',
                                `order_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单id',
                                `action` int(0) NOT NULL DEFAULT 0 COMMENT '1下单 2支付 3修改 4申请取消 5取消成功 6申请加购 7加购成功 8过期  9完结 10 售后',
                                `source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作的来源客户端',
                                `op_num` int(0) NOT NULL DEFAULT 0 COMMENT '操作数量',
                                `remaining_num` int(0) NOT NULL DEFAULT 0 COMMENT '剩余数量',
                                `op_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '操作用户id',
                                `ext_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '扩展信息存储',
                                `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order`  (
                                `id` bigint(0) NOT NULL COMMENT '主键',
                                `buyer_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '买家id',
                                `status` int(0) NOT NULL DEFAULT 0 COMMENT '订单状态',
                                `pay_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '支付时间 0未支付',
                                `order_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，下单时间',
                                `finish_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单完结时间',
                                `pay_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '支付方式 WX 等等',
                                `order_channel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '下单渠道',
                                `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单更新时间',
                                `total_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
                                `coupon_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠总金额',
                                `pay_amount` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `idx_buyer_id`(`buyer_id`) USING BTREE,
                                INDEX `idx_order_time`(`order_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易订单表 trade_order  1:N order_info' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
