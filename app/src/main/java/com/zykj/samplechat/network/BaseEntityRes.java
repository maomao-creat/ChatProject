package com.zykj.samplechat.network;

public class BaseEntityRes<T> {

    /**
     * 状态码
     */
    public int code;
    /**
     * 数据
     */
    public T data;

    /**
     * 数据
     */
    public T data1;

    public int recordCount;// 总条数

    public String Token;

    public String error;

    public String message;

    public int BuilderId;//数据
    public int data4;
}
