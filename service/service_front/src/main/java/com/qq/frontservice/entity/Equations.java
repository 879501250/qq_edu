package com.qq.frontservice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个封装了所有表达式及结果的类
 */
public class Equations {
    private List<String> expressions; // 所有的表达式集合
    private List<Integer> results; // 所有的结果集合
    private int count; // 当前表达式数量

    /**
     * 有参构造器
     *
     * @param count 表达式数量
     */
    public Equations(int count) {
        this.expressions = new ArrayList<>(count);
        this.results = new ArrayList<>(count);
        this.count = 0;
    }

    // 添加表达式及结果
    public void addExpression(String expression, int result) {
        // 格式化运算表达式
        expression = expression.replaceAll("[*]", "×").replaceAll("/", "÷");
        expression += "=";
        this.expressions.add(expression);
        this.results.add(result);
        count++;
    }

    // 获取目前表达式数量
    public int getCount() {
        return count;
    }

    public List<String> getExpressions() {
        return expressions;
    }

    public List<Integer> getResults() {
        return results;
    }
}
