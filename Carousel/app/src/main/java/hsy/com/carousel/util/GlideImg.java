package hsy.com.carousel.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import hsy.com.carousel.MyApplication;

/**
 * Created by hsy on 16/7/8.
 */
public class GlideImg {

    private static GlideImg glideImg;
    private Context context;

    public GlideImg() {

    }

    public static GlideImg getInstance(){
        if(glideImg == null ){
            glideImg = new GlideImg();
            return glideImg;
        }
        return glideImg;
    }

    public void getGlide(String imgUrl, ImageView imageView, int placeholder, int error) {
        context = MyApplication.getContext();
        Glide.with(context)
                .load(imgUrl)
                .placeholder(placeholder)
                .error(error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }
}
