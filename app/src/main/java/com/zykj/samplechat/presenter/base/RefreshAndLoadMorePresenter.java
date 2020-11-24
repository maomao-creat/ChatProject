package com.zykj.samplechat.presenter.base;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

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
public abstract class RefreshAndLoadMorePresenter<V extends LoadMoreView> extends BasePresenterImp<V> {

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    public abstract void getData(int page, int count);

    /**
     * 设置数据状态
     *
     * @param page
     * @param count
     * @param res
     */
    public void setDataStatus(int page, int count, Res res) {
        if (page * count >= res.recordcount) {
            //没有更多了
            view.noMore();
        } else {
            //还有更多
            view.hasMore();
        }

    }
}
