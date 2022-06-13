package com.qq.commonutils;

//定义一个返回的状态码枚举类
public enum ResultCode {
    //1.提供当前枚举类的对象，多个对象之间用","隔开，末尾对象";"结束
    success("sussess", 20000),
    error("error", 20001);

    //2.声明Season对象的属性:private final修饰
    private final String resultName;
    private final int resultCode;

    //3.私化类的构造器,并给对象属性赋值
    private ResultCode(String resultName, int resultCode) {
        this.resultName = resultName;
        this.resultCode = resultCode;
    }

    //4.其他诉求1：获取枚举类对象的属性
    public String getResultName() {
        return resultName;
    }

    public int getResultCode() {
        return resultCode;
    }
}
