package com.qq.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.commonutils.Result;
import com.qq.eduservice.entity.EduGame;
import com.qq.eduservice.service.EduGameService;
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
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/eduservice/game")
public class EduGameController {
    @Autowired
    private EduGameService gameService;

    // 将封装好了的游戏信息保存下来（传入的json数据）
    @PostMapping("/saveGame")
    public Result saveGame(@RequestBody EduGame game) {
        gameService.save(game);
        return Result.success();
    }

    // 根据游戏id修改游戏信息
    @PutMapping("/updateGame")
    public Result updateGame(@RequestBody EduGame game) {
        gameService.updateById(game);
        return Result.success();
    }

    // 根据游戏id获取游戏信息
    @GetMapping("/getGame/{id}")
    public Result getGame(@PathVariable String id) {
        EduGame game = gameService.getById(id);
        return Result.success().data("game", game);
    }

    /**
     * 分页查询（前后端一致）
     *
     * @param current 当前页
     * @param count   每页记录数
     * @return
     */
    @PostMapping("/pageGameList/{current}/{count}")
    public Result pageGameList(@PathVariable("current") int current,
                               @PathVariable("count") int count) {
        //创造page对象
        Page<EduGame> gamePage = new Page<>(current, count);
        //调用方法实现分页
        gameService.page(gamePage);
        // 将数据取出来放到map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("items", gamePage.getRecords());
        map.put("current", gamePage.getCurrent());
        map.put("pages", gamePage.getPages());
        map.put("size", gamePage.getSize());
        map.put("total", gamePage.getTotal());
        map.put("hasNext", gamePage.hasNext());
        map.put("hasPrevious", gamePage.hasPrevious());

        return Result.success().data(map);
    }

    // 根据游戏id删除游戏
    @DeleteMapping("/delGame/{id}")
    public Result delGame(@PathVariable String id) {
        boolean result = gameService.removeById(id);
        if (result) {
            return Result.success();
        }
        return Result.error().message("删除游戏失败~");
    }

}

