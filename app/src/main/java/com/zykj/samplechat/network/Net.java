package com.zykj.samplechat.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
public class Net {
    private static OkHttpClient client = new OkHttpClient();

    private static OkHttpClient clients = new OkHttpClient();

    private static OkHttpClient clientss= new OkHttpClient();

    public static Retrofit retrofit;

    public static Retrofit retrofits;

    public static Retrofit retrofitss;

    public static ApiService getService() {
        if (retrofit == null) {
            client.setReadTimeout(20, TimeUnit.MINUTES);
            client.setWriteTimeout(30, TimeUnit.MINUTES);

            if(UserUtil.getUser()!=null&&!StringUtil.isEmpty(UserUtil.getToken())) {
                Log.e("TAG","TOKEN========="+UserUtil.getToken());
                Log.e("TAG","用户ID========="+UserUtil.getUser().getId());
                client.interceptors().add(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newBuilder = request.newBuilder()
                                .addHeader("token", UserUtil.getToken())
                                .addHeader("userid", UserUtil.getUser().getId());
                        Request request1 = newBuilder.build();
                        return chain.proceed(request1);
                    }
                });
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }

        return retrofit.create(ApiService.class);
    }

    public static ApiService getServices() {
        if (retrofits == null) {
            clients.setReadTimeout(20, TimeUnit.MINUTES);
            clients.setWriteTimeout(30, TimeUnit.MINUTES);
            retrofits = new Retrofit.Builder()
                    .baseUrl(Const.BASE)
                    .client(clients)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }

        return retrofits.create(ApiService.class);
    }

    public static ApiService getServicess() {
        if (retrofitss == null) {
            clientss.setReadTimeout(20, TimeUnit.MINUTES);
            clientss.setWriteTimeout(30, TimeUnit.MINUTES);

            if(UserUtil.getUser()!=null&&!StringUtil.isEmpty(UserUtil.getToken())) {
                Log.e("TAG","TOKEN========="+UserUtil.getToken());
                Log.e("TAG","用户ID========="+UserUtil.getUser().getId());
                clientss.interceptors().add(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newBuilder = request.newBuilder()
                                .addHeader("token", UserUtil.getToken())
                                .addHeader("userid", UserUtil.getUser().getId());
                        Request request1 = newBuilder.build();
                        return chain.proceed(request1);
                    }
                });
            }
            retrofitss = new Retrofit.Builder()
                    .baseUrl(Const.BASE)
                    .client(clientss)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }

        return retrofitss.create(ApiService.class);
    }
}
