package pers.codewld.iadmin.crud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基本增删改查接口
 */
public class BaseController<T> {

    @Resource
    protected IService<T> baseService;

    @ApiOperation("添加")
    @PostMapping("")
    public boolean add(@RequestBody T t) {
        return baseService.save(t);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public boolean del(@PathVariable String id) {
        return baseService.removeById(id);
    }

    @ApiOperation("修改")
    @PutMapping("")
    public boolean update(@RequestBody T t) {
        return baseService.updateById(t);
    }

    @ApiOperation("查询")
    @GetMapping("/{id}")
    public T get(@PathVariable String id) {
        return baseService.getById(id);
    }

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Page<T> page(
            @ApiParam("当前页数") Integer pageNum,
            @ApiParam("每页条数") Integer pageSize) {
        return baseService.page(new Page<>(pageNum, pageSize));
    }

}
