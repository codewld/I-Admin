package pers.codewld.iadmin.crud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.iadmin.crud.model.param.QueryParam;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 基本增删改查接口
 */
@Validated
public class BaseController<T> {

    @Resource
    protected IService<T> baseService;

    @ApiOperation("添加")
    @PostMapping("")
    public boolean add(@RequestBody @Validated T t) {
        return baseService.save(t);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public boolean del(@PathVariable String id) {
        return baseService.removeById(id);
    }

    @ApiOperation("修改")
    @PutMapping("")
    public boolean update(@RequestBody @Validated T t) {
        return baseService.updateById(t);
    }

    @ApiOperation("查询")
    @GetMapping("/{id}")
    public T get(@PathVariable String id) {
        return baseService.getById(id);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public Page<T> page(
            @RequestParam @Min(value = 1, message = "当前页数最小为1") @ApiParam("当前页数") Integer pageNum,
            @RequestParam @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
            @RequestBody @Validated QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(queryParam);
        return baseService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @ApiOperation("批量查询")
    @PostMapping("/list")
    public List<T> list(@RequestBody @Validated QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(queryParam);
        return baseService.list(queryWrapper);
    }

    /**
     * 根据查询参数生成QueryWrapper
     * @param queryParam 查询参数
     * @return QueryWrapper
     */
    private QueryWrapper<T> getQueryWrapper(QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 查询条件
        queryParam.getConditions().forEach(item -> {
            QueryParam.Condition.Operator operator = item.getOperator();
            switch (operator) {
                case EQ:
                    queryWrapper.eq(item.getField(), item.getValue().get(0));
                    break;
                case NE:
                    queryWrapper.ne(item.getField(), item.getValue().get(0));
                    break;
                case GT:
                    queryWrapper.gt(item.getField(), item.getValue().get(0));
                    break;
                case GE:
                    queryWrapper.ge(item.getField(), item.getValue().get(0));
                    break;
                case LT:
                    queryWrapper.lt(item.getField(), item.getValue().get(0));
                    break;
                case LE:
                    queryWrapper.le(item.getField(), item.getValue().get(0));
                    break;
                case BETWEEN:
                    queryWrapper.between(item.getField(), item.getValue().get(0), item.getValue().get(1));
                    break;
                case NOT_BETWEEN:
                    queryWrapper.notBetween(item.getField(), item.getValue().get(0), item.getValue().get(1));
                    break;
                case LIKE:
                    queryWrapper.like(item.getField(), item.getValue().get(0));
                    break;
                case NOT_LIKE:
                    queryWrapper.notLike(item.getField(), item.getValue().get(0));
                    break;
                case LIKE_LEFT:
                    queryWrapper.likeLeft(item.getField(), item.getValue().get(0));
                    break;
                case LIKE_RIGHT:
                    queryWrapper.likeRight(item.getField(), item.getValue().get(0));
                    break;
                case IS_NULL:
                    queryWrapper.isNull(item.getField());
                    break;
                case IS_NOT_NULL:
                    queryWrapper.isNotNull(item.getField());
                    break;
                case IN:
                    queryWrapper.in(item.getField(), item.getValue().get(0));
                    break;
                case NOT_IN:
                    queryWrapper.notIn(item.getField(), item.getValue().get(0));
                    break;
                default:
            }
        });
        // 排序
        queryParam.getOrders().forEach(item -> {
            QueryParam.Order.Type type = item.getType();
            switch (type) {
                case ASC:
                    queryWrapper.orderByAsc(item.getField());
                    break;
                case DESC:
                    queryWrapper.orderByDesc(item.getField());
                    break;
                default:
            }
        });
        return queryWrapper;
    }

}
