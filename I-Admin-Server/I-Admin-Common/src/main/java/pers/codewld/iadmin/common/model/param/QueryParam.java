package pers.codewld.iadmin.common.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 查询参数
 */
@ApiModel("查询参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParam {

    @ApiModelProperty("条件列表")
    List<Condition> conditions = Collections.emptyList();

    @ApiModelProperty("排序列表")
    List<Order> orders = Collections.emptyList();

    /**
     * 查询参数-条件
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("查询参数-条件")
    public static class Condition {

        /**
         * 字段名
         */
        @ApiModelProperty("字段名")
        private String field;

        /**
         * 值
         */
        @ApiModelProperty("值")
        private List<String> value = Collections.emptyList();

        /**
         * 操作符
         */
        @ApiModelProperty("操作符")
        private Operator operator;

        /**
         * 操作符
         */
        @ApiModel("操作符")
        public enum Operator {

            EQ, NE,
            GT, GE,
            LT, LE,
            BETWEEN, NOT_BETWEEN,
            LIKE, NOT_LIKE, LIKE_LEFT, LIKE_RIGHT,
            IS_NULL, IS_NOT_NULL,
            IN, NOT_IN

        }

    }

    /**
     * 查询参数-排序
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("查询参数-排序")
    public static class Order {

        @ApiModelProperty("字段名")
        private String field;

        @ApiModelProperty("排序方式")
        private Type type;

        /**
         * 排序方式
         */
        @ApiModel("排序方式")
        public enum Type {

            ASC, DESC

        }

    }

}
