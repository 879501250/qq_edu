package com.qq.frontservice.controller;

import com.qq.commonutils.Result;
import com.qq.frontservice.entity.Equations;
import com.qq.frontservice.utils.ArithmeticUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * <p>
 * 游戏前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/frontservice/game")
public class GameController {

    /**
     * 获取随机生成指定要求的算术表达式
     *
     * @param calculationNum 算式的数量
     * @param maxNum         多少以内的算数
     * @param operators      算式中所包含的运算符种类
     */
    @PostMapping("/getEquations/{maxNum}/{calculationNum}")
    public Result getEquations(@PathVariable int maxNum,
                               @PathVariable int calculationNum,
                               @RequestBody int[] operators) {
        Equations equations = ArithmeticUtils.arithmetic(2, calculationNum, maxNum, operators);
        return Result.success().data("equations", equations);
    }

    /**
     * 获取随机生成指定要求的一个算术表达式，并将结果随机修改
     *
     * @param maxNum    多少以内的算数
     * @param operators 算式中所包含的运算符种类
     */
    @PostMapping("/getEquation/{maxNum}")
    public Result getEquation(@PathVariable int maxNum,
                              @RequestBody int[] operators) {
        Equations equations = ArithmeticUtils.arithmetic(2, 1, maxNum, operators);
        Random random = new Random();
        // 获取表达式
        String expression = equations.getExpressions().get(0);
        boolean isTrue = random.nextBoolean(); // 随机返回一个布尔值
        if (isTrue) {
            expression += equations.getResults().get(0);
        } else { // 若为false，随机赋一个假值
            expression += random.nextInt(2 * equations.getResults().get(0));
        }
        return Result.success().data("expression", expression).data("isTrue", isTrue ? 1 : 0);
    }
}
