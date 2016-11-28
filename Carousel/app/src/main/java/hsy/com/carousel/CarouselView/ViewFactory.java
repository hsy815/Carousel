package hsy.com.carousel.CarouselView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import hsy.com.carousel.R;
import hsy.com.carousel.util.GlideImg;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	static GlideImg glideImg;
	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		glideImg = GlideImg.getInstance();
		glideImg.getGlide(url,imageView,R.mipmap.icon_stub,R.mipmap.icon_error);
		return imageView;
	}
}
