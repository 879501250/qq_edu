package com.qq.demo;

import com.qq.frontservice.entity.Equations;
import com.qq.frontservice.utils.ArithmeticUtils;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Random;

public class Arithmetic {

    @Test
    public void newArithmetic(){
        Equations arithmetic = ArithmeticUtils.arithmetic(2, 100, 20, new int[]{0, 1, 2, 3});
        System.out.println(arithmetic.getExpressions());
        System.out.println(arithmetic.getResults());
    }

    /**
     * 计算每个等式的结果，并返回运算式
     * @param arrayList
     * @return arrayList
     */
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    private static ArrayList<String> calculate(ArrayList<String> arrayList){
        ArrayList<String> ArithExpress=new ArrayList<String>();
        for(String ax:arrayList){
            try {
                String result = jse.eval(ax)+"";
                ax=ax+"="+jse.eval(ax);
                System.out.println(ax);
                System.out.println(Integer.parseInt(result));
                ArithExpress.add(ax);
            } catch (ScriptException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ArithExpress;
    }
    @Test
    public void oldArithmetic() {

        char[] operator = new char[]{'+', '-', '*', '/'};
        Random random = new Random();
        ArrayList<String> expression = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            int n = random.nextInt(2) + 1; //1-2个运算符
            int[] number = new int[n + 1];
            String ex = new String();
            for (int j = 0; j <= n; j++) {
                number[j] = random.nextInt(100) + 1; //2-3个数字
            }
            for (int j = 0; j < n; j++) {
                int s = random.nextInt(4);//随机选择某个运算符
                ex += String.valueOf(number[j]) + String.valueOf(operator[s]);///5+4+6+9
                if (s == 3) {
                    number[j + 1] = decide(number[j], number[j + 1]);
                }
            }
            ex += String.valueOf(number[n]);
            expression.add(ex);
        }
        System.out.println(expression);
        System.out.println(calculate(expression));
    }

    /**
     * 随即取x,y为1-100之间，x可以整除y的y值
     * @param x
     * @param y
     * @return
     */
    private static int decide(int x,int y){//通过递归实现整除
        Random random=new Random();
        if(x%y!=0){
            y=random.nextInt(100)+1;
            return decide(x,y);
        }
        else{
            return y;
        }
    }
}
