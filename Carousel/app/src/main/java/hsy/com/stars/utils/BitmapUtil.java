package hsy.com.carousel.com.stars.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hsy on 16/3/18.
 */
public class BitmapUtil {
    private static final float DISPLAY_WIDTH = 1000;
    private static final float DISPLAY_HEIGHT = 1000;
    private static int count = 10000;
    private static List<File> files = new ArrayList<>();
    public static final String PATH_FUNDBANG = Environment.getExternalStorageDirectory().getPath() + "/FundBang";

    private static String path = PATH_FUNDBANG + "/image/";

    public static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / DISPLAY_WIDTH);
        int hRatio = (int) Math.ceil(op.outHeight / DISPLAY_HEIGHT);
        //如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, op);
        return bmp;
    }


    public static File saveBitmapFile(Bitmap bitmap) {
        count++;
        if (bitmap != null) {
            File file = new File(path + count + ".png");//将要保存图片的路径
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            addFile(file);
            return file;
        }
        return null;
    }

    private static void addFile(File file) {
        files.add(file);
    }

    public static void delete() {
        for (File f : files) {
            f.delete();
        }
    }

    /**
     *  bitmap转file
     * @param b 被转bitmap
     * @param name 文件名
     * @return
     * @throws Exception
     */
    public static File saveImg(Bitmap b, String name) throws Exception {

        String path = Environment.getExternalStorageDirectory() + "/" + "DWXS/HSY";
        File dirFile = new File(path);
        File mediaFile = new File(path + File.separator + name);
        if (mediaFile.exists()) {
            mediaFile.delete();
        }
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        mediaFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(mediaFile);
        b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
       /* b.recycle();
        b = null;*/
        System.gc();
        return mediaFile;
    }

    /**
     * 缩放Bitmap图片
     **/
    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 创建图片不同的文件名
     **/
    private String createPhotoFileName() {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

}
