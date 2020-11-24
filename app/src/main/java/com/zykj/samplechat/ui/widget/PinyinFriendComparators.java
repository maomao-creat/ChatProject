package com.zykj.samplechat.ui.widget;

import com.zykj.samplechat.model.MyHYLB;

import java.util.Comparator;

public class PinyinFriendComparators implements Comparator<MyHYLB> {
    public int compare(MyHYLB o1, MyHYLB o2) {
        if (o1.getTopc().equals("@") || o2.getTopc().equals("#")) {
            return -1;
        } else if (o1.getTopc().equals("#") || o2.getTopc().equals("@")) {
            return 1;
        } else {
            return o1.getTopc().compareTo(o2.getTopc());
        }
    }
}
