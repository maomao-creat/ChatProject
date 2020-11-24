package com.zykj.samplechat.presenter;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.UploadPhoto;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.SendTiXianView;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;

import java.io.File;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class TiXianPresenter extends BasePresenterImp<SendTiXianView>  {
    //private User user;
    public String qrcode;
    public void UpLoadPhoto(String path,String id){
            String fileStream = Encoder.bitmapToString(path);
            HttpUtils.UploadQrcode(new SubscriberRes<UploadPhoto>() {
                @Override
                public void onSuccess(UploadPhoto friend) {
                    view.successUploadPhoto(friend.Qrcode);
                }

                @Override
                public void completeDialog() {
                }
            },path,fileStream);
    }

    public void UploadQrCodeByFile(final String info) {
        File file = new File(info);
        RequestBody requestBody=new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        HttpUtils.UploadQrCodeByFile(new SubscriberRes<UploadPhoto>() {
                                      @Override
                                      public void onSuccess(UploadPhoto user) {
                                          view.dismissDialog();
                                          qrcode = user.Url;
                                      }

                                      @Override
                                      public void completeDialog() {
                                          view.dismissDialog();
                                      }
                                  }
                , requestBody);
    }

    public void CreateCash(int type,String amount){
        if(StringUtil.isEmpty(qrcode)){
            ToolsUtils.toast(view.getContext(),"请上传收款二维码");
        }else {
            HttpUtils.CreateCash(new SubscriberRes<String>() {
                @Override
                public void onSuccess(String friend) {
                    ToolsUtils.toast(view.getContext(),"发起提现成功");
                    view.finishActivity();
                }

                @Override
                public void completeDialog() {
                }
            }, type, amount, qrcode);
        }
    }

//{"code":200,"message":null,"data":{"HeadUrl":"UpLoad/Photo/05f3bae3-81ee-451a-a18e-7dfede50ffe6.jpg"},"error":null}
    public void getyue(String data) {
        Subscription subscription = Net.getService()
                .doWithdraw(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.toast("提现失败!");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Res res) {
                        try {
                            if(res.code==200){
                                view.success("");
                            }else{
                                view.toast("提现失败！");
                            }
                        } catch (Exception ex) {
                            view.toast(res.data.toString());
                        }
                    }
                });

        addSubscription(subscription);
    }
}
