package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.commonutils.Result;
import com.qq.eduservice.entity.EduCourse;
import com.qq.eduservice.entity.EduMaterial;
import com.qq.eduservice.entity.vo.MaterialQuery;
import com.qq.eduservice.service.EduMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-04-01
 */
@RestController
@RequestMapping("/eduservice/material")
public class EduMaterialController {

    @Autowired
    private EduMaterialService materialService;

    // 将封装好了的资料信息保存下来（传入的json数据）
    @PostMapping("/saveMaterial")
    public Result saveMaterial(@RequestBody EduMaterial material) {
        materialService.save(material);
        return Result.success();
    }

    // 根据资料id修改资料信息
    @PutMapping("/updateMaterial")
    public Result updateMaterial(@RequestBody EduMaterial material) {
        materialService.updateById(material);
        return Result.success();
    }

    // 根据资料id获取资料信息
    @GetMapping("/getMaterial/{id}")
    public Result getMaterial(@PathVariable String id) {
        EduMaterial material = materialService.getById(id);
        return Result.success().data("material", material);
    }

    /**
     * 按照条件分页查询资料（前后端一致）
     *
     * @param current 当前页
     * @param count   每页记录数
     * @param query   查询条件，可以为空
     * @return
     */
    @PostMapping("/pageMaterialList/{current}/{count}")
    public Result pageMaterialList(@PathVariable("current") int current,
                                   @PathVariable("count") int count,
                                   @RequestBody(required = false) MaterialQuery query) {
        //创造page对象
        Page<EduMaterial> materialPage = new Page<>(current, count);
        //创建动态sql语句
        QueryWrapper<EduMaterial> queryWrapper = new QueryWrapper<>();
        if (query != null) {
            //下面是数据库字段名！！！不是属性名
            if (!StringUtils.isEmpty(query.getTitle())) //题目
                queryWrapper.like("title", query.getTitle());
            if (null != query.getType()) { //资源类型
                if (query.getType() == 1)
                    queryWrapper.eq("type", 1);
                else if (query.getType() == 0)
                    queryWrapper.eq("type", 0);
            }
            if (null != query.getIsAbroad()) { //国内外资源
                if (query.getIsAbroad() == 1)
                    queryWrapper.eq("is_abroad", 1);
                else if (query.getIsAbroad() == 0)
                    queryWrapper.eq("is_abroad", 0);
            }
        }
        //调用方法实现分页
        materialService.page(materialPage, queryWrapper);
        // 将数据取出来放到map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("items", materialPage.getRecords());
        map.put("current", materialPage.getCurrent());
        map.put("pages", materialPage.getPages());
        map.put("size", materialPage.getSize());
        map.put("total", materialPage.getTotal());
        map.put("hasNext", materialPage.hasNext());
        map.put("hasPrevious", materialPage.hasPrevious());

        return Result.success().data(map);
    }

    // 根据资料id删除资料
    @DeleteMapping("/delMaterial/{id}")
    public Result delMaterial(@PathVariable String id) {
        boolean result = materialService.removeById(id);
        if (result) {
            return Result.success();
        }
        return Result.error().message("删除资料失败~");
    }
}

