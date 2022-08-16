package pers.codewld.iadmin.crud.model.param;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 查询参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询参数")
public class QueryParam {

    /**
     * 查询条件列表
     */
    List<Condition> conditions = Collections.emptyList();

    /**
     * 排序列表
     */
    List<Order> orders = Collections.emptyList();

    /**
     * 查询条件
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Condition {

        /**
         * 字段名
         */
        private String field;

        /**
         * 操作符
         */
        private Operator operator;

        /**
         * 值
         */
        private List<String> value = Collections.emptyList();

        /**
         * 操作符
         */
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
     * 排序
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {

        /**
         * 字段名
         */
        private String field;

        /**
         * 排序方式
         */
        private Type type;

        /**
         * 排序方式
         */
        public enum Type {

            ASC, DESC

        }

    }
}
