package pers.codewld.iadmin.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.iadmin.common.model.param.QueryParam;
import pers.codewld.iadmin.common.model.vo.PageVO;
import pers.codewld.iadmin.common.util.CaseFormatUtils;
import pers.codewld.iadmin.common.util.ICollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 基本增删改查 接口
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
    public PageVO<T> page(
            @ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "当前页数最小为1") Integer pageNum,
            @ApiParam("每页条数") @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") Integer pageSize,
            @RequestBody(required = false) @Validated QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(queryParam);
        return new PageVO<>(baseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @ApiOperation("批量查询")
    @PostMapping("/list")
    public List<T> list(@RequestBody(required = false) @Validated QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(queryParam);
        return baseService.list(queryWrapper);
    }

    /**
     * 根据查询参数生成QueryWrapper
     * @param queryParam 查询参数
     * @return QueryWrapper
     */
    private QueryWrapper<T> getQueryWrapper(@Nullable QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        if (queryParam == null) {
            return queryWrapper;
        }

        // 查询条件
        ICollectionUtils.deNull(queryParam.getConditions()).forEach(item -> {
            QueryParam.Condition.Operator operator = item.getOperator();
            String field = item.getField();
            field = CaseFormatUtils.camelCase2UnderScoreCase(field);
            switch (operator) {
                case EQ:
                    queryWrapper.eq(field, item.getValue().get(0));
                    break;
                case NE:
                    queryWrapper.ne(field, item.getValue().get(0));
                    break;
                case GT:
                    queryWrapper.gt(field, item.getValue().get(0));
                    break;
                case GE:
                    queryWrapper.ge(field, item.getValue().get(0));
                    break;
                case LT:
                    queryWrapper.lt(field, item.getValue().get(0));
                    break;
                case LE:
                    queryWrapper.le(field, item.getValue().get(0));
                    break;
                case BETWEEN:
                    queryWrapper.between(field, item.getValue().get(0), item.getValue().get(1));
                    break;
                case NOT_BETWEEN:
                    queryWrapper.notBetween(field, item.getValue().get(0), item.getValue().get(1));
                    break;
                case LIKE:
                    queryWrapper.like(field, item.getValue().get(0));
                    break;
                case NOT_LIKE:
                    queryWrapper.notLike(field, item.getValue().get(0));
                    break;
                case LIKE_LEFT:
                    queryWrapper.likeLeft(field, item.getValue().get(0));
                    break;
                case LIKE_RIGHT:
                    queryWrapper.likeRight(field, item.getValue().get(0));
                    break;
                case IS_NULL:
                    queryWrapper.isNull(field);
                    break;
                case IS_NOT_NULL:
                    queryWrapper.isNotNull(field);
                    break;
                case IN:
                    queryWrapper.in(field, item.getValue().get(0));
                    break;
                case NOT_IN:
                    queryWrapper.notIn(field, item.getValue().get(0));
                    break;
                default:
            }
        });

        // 排序
        ICollectionUtils.deNull(queryParam.getOrders()).forEach(item -> {
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
