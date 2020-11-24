package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/11.
 */
public class Comment implements Serializable {
    public int Id;
    public int CommentedId;
    public String CommentedNicName;
    public String CommentedPhotoPath;
    public int CommenterId;
    public String Content;
    public String NicName;
    public int ParentId;
    public int MessageId;
    public String PhotoPath;
    public String PublishTime;
}
