package com.mjceo.transportation.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtil {

    private static RequestBuilder<Drawable> imageInitializer(Context context, String url) {
        return Glide.with(context).load(url).transition(DrawableTransitionOptions.withCrossFade());
    }

    public static void loadImage(Context context, ImageView image, String url) {
        imageInitializer(context, url).centerCrop().into(image);
    }

    public static void loadImageWithCustomSize(Context context, ImageView image, String url, int width, int height) {
        imageInitializer(context, url).apply(new RequestOptions().override(width, height).circleCrop()).into(image);
    }
}