package com.zykj.samplechat.ui.view;

import android.view.View;

import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public interface GroupView extends LoadMoreView {
    void topImage(String path);
    void success(ArrayList<Group> data,String path);
    void successLike(int pos);
    void successLikeCancel(int pos);
    void refresh(boolean refreshing);
    void showCommentInput(int id, int position, View v);
    void commentSuccess(int position, Comment comment);
    void showReplyInput(int id, String nicName, int commenterId, int position, View v);
    void replySuccess(int position, Comment comment);
    void deleteConditionSuccess(int position);
    void successDelComment();
    void successDelete();

}
