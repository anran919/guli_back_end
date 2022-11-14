package com.zar.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.cms.entity.CrmBanner;
import com.zar.cms.service.CrmBannerService;
import com.zar.commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-14
 */
@Api( description = "首页轮播图")
@RestController
@CrossOrigin
@RequestMapping("/service_cms/banner")
public class CrmBannerController {

    @Resource
    private CrmBannerService bannerService;

    @GetMapping("{pageNo}/{pageSize}")
    @ApiOperation("分页获取首页轮播图")
    public R page(@ApiParam(name = "pageNo",value = "当前页码",required = true) @PathVariable Long pageNo , @ApiParam(name ="pageSize",value = "每页记录数",required = true) @PathVariable Long pageSize  ){
        IPage<CrmBanner> page = new Page<>(pageNo, pageSize);
        IPage<CrmBanner> data = bannerService.page(page, new QueryWrapper<>());
        return R.ok().data("data",data);
    }

    @GetMapping("list")
    @ApiOperation("获取首页轮播图")
    public R list(){
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("data",list);
    }

    @GetMapping("{id}")
    @ApiOperation("获取首页轮播图")
    public R getBannerById(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("data",banner);
    }

    @PostMapping("add")
    @ApiOperation("新增首页轮播图")
    public R addBanner(@RequestBody CrmBanner banner){
        boolean save = bannerService.save(banner);
        return R.ok().data("data",save);
    }

    @PutMapping("update")
    @ApiOperation("修改首页轮播图")
    public R updateBanner(@RequestBody CrmBanner banner){
        boolean update = bannerService.updateById(banner);
        return R.ok().data("data",update);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("删除首页轮播图")
    public R deleteBannerById(@PathVariable String id){
        boolean remove = bannerService.removeById(id);
        return R.ok().data("data",remove);
    }
}

