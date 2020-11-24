package com.zykj.samplechat.model;
/*
* 以下为接收数据的实体，可根据需求变更。
*
* 数据统一返回String类型，或者使用泛型
* */
public class RespBean {
    public String code; //返回码 正确=200
    public String error;    //错误信息
    public String message;    //错误信息
   // public String total;         //条数
   // public String key;        //0
//    public List list;       //list数据
  //  public Object list;       //适配
    public Object data;        //obj数据
  //  public Object info;

}
