package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ninos on 2017/8/23.
 */

public class HongbaoList implements Serializable {
    public String NicName;
    public String NickName;
    public String PhotoPath;
    public String HeadUrl;
    public String Time;
    public int TotalCount;
    public int TotalMoney;
    public int Lei;
    public String Nums;
    public String Type;
    public String Description;
    public ArrayList<HongbaoUser> UserList;

    public class HongbaoUser implements Serializable {
        public String NicName;
        public String NickName;
        public String PhotoPath;
        public String HeadUrl;
        public String OpenTime;
        public String Amount;
        public int IsLuck;
        public String Flag;
        public String userid;


    }
}
