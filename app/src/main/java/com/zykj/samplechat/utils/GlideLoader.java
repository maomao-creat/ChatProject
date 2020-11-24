package com.zykj.samplechat.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;

/**
 * Created by ninos on 2016/7/6.
 */
public class GlideLoader implements com.yancy.imageselector.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(R.mipmap.imageselector_photo))
                .into(imageView);
    }
}