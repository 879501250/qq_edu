package com.qq.frontservice.utils;

import com.qq.frontservice.entity.Equations;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

/**
 * 四则运算表达式
 */
public class ArithmeticUtils {
    /**
     * 计算传入的表达式的结果，若结果符合要求，则存储结果，并返回true，否则返回false
     *
     * @param calculation 传入的表达式
     * @param results 结果存储的数组
     */
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    private static Boolean calculate(String expression, Equations equations) {
        try {
            // 计算结果，并转化为数字，若转化失败者报错，返回false
            int result = (int) jse.eval(expression);
            // 若结果小于0，返回false
            if (result < 0)
                return false;
            // 添加结果并返回true
            equations.addExpression(expression, result);
            return true;
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }


    /**
     * 随机生成指定要求的算术表达式
     * @param operatorNum    算式中包含的运算符数量（1-operatorNum）
     * @param calculationNum 算式的数量
     * @param maxNum         多少以内的算数
     * @param operators      算式中所包含的运算符种类
     */
    public static Equations arithmetic(int operatorNum, int calculationNum, int maxNum, int[] operators) {
        // 创建一个储存所有表达式及结果的对象
        Equations equations = new Equations(calculationNum);
        char[] operator = new char[]{'+', '-', '*', '/'};
        Random random = new Random();
        int n; // 表示运算符数量
        int[] number; // 用来储存表达式中所有的数字
        StringBuilder ex = new StringBuilder();// 定义一个算式
        while (equations.getCount() != calculationNum) {
            n = random.nextInt(operatorNum) + 1; //1-operatorNum个运算符
            number = new int[n + 1]; // 每个算式中数字的数量
            for (int j = 0; j <= n; j++) {
                number[j] = random.nextInt(maxNum) + 1; // 随机的数字
            }
            ex.delete(0, ex.length()); // 清空表达式
            for (int j = 0; j < n; j++) {
                //随机选择某个运算符
                int s = operators[random.nextInt(operators.length)];
                ex.append(number[j]).append(operator[s]); //5+4+6+
                // 使下一个数字可以被整除
                if (s == 3) {
                    number[j + 1] = decide(number[j], number[j + 1]);
                }
            }
            // 完善表达式
            ex.append(number[n]); //5+4+6+9
            // 判断表达式是否可行，若可行则储存结果
            calculate(ex.toString(), equations);
        }
        return equations;
    }

    /**
     * 随即取x,y为1-100之间，x可以整除y的y值
     *
     * @param x
     * @param y
     * @return
     */
    private static int decide(int x, int y) {//通过递归实现整除
        Random random = new Random();
        if (x % y != 0) {
            y = random.nextInt(y) + 1;
            return decide(x, y);
        } else {
            return y;
        }
    }
}

