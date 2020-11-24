package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/14.
 */
public class Team implements Serializable{
    public int Id;
    public String Name;
    public int Type;//1-后台创建的群 0-普通的群
    public int AreaId;
    public String BuildTime;
    public String ImagePath;
    public String Address;
    public String TeamIntroduce;
    public int TeamId;
    public int TeamLeaderId;
    public String TeamAnnouncement;
    public String Message;
    public int Count;
}
