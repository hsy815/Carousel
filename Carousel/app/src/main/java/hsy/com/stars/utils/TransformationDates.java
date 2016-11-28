package hsy.com.carousel.com.stars.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hsy on 16/3/16.
 */
public class TransformationDates {

    private static TransformationDates transformationDates;

    private TransformationDates() {
    }

    public static TransformationDates getInstance() {
        if (transformationDates == null) {
            transformationDates = new TransformationDates();
        }
        return transformationDates;
    }

    public String DateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 返回 刚刚、 分、  时、  天、
     *
     * @param starttime
     * @return
     */
    public String Timedifference(String starttime) {

        if (TextUtils.isEmpty(starttime))
            return null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = null;
        Date date = null;
        try {
            now = df.parse(DateTime());
            date = df.parse(starttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");

        if (day > 10) {
            return starttime.substring(0, 10);
        } else if (day > 0 && day <= 10) {
            return day + "天前";
        } else if (day <= 0 && hour > 0) {
            return hour + "小时前";
        } else if (day <= 0 && hour <= 0 && min > 0) {
            return min + "分钟前";
        } else if (day <= 0 && hour <= 0 && min <= 0 && s >= 0) {
            return "刚刚";
        }
        return "刚刚";
    }

    /**
     * 时间差
     *
     * @param starttime
     * @return
     */
    public long DifferenceTime(String starttime) {
        if (TextUtils.isEmpty(starttime))
            return 0;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = null;
        Date date = null;
        try {
            now = df.parse(DateTime());
            date = df.parse(starttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = now.getTime() - date.getTime();

        return l;
    }

    /**
     * 时间截取（只要日期）
     * @param starttime
     * @return
     */
    public String Intercept(String starttime) {
        if (!TextUtils.isEmpty(starttime)) {
            String[] InterceptTime = starttime.split(" ");
            return InterceptTime[0];
        } else
            return starttime;
    }

    /**
     * 格式转换(”/“)
     *
     * @param starttime
     * @return
     */
    public String Transformation(String starttime){
        if(!TextUtils.isEmpty(starttime)){
            String Trans = starttime.replace("-","/");
            return Trans;
         }else
            return starttime;
    }

    /**
     * 格式转换("年 月 日")
     * @param starttime
     * @return
     */
    public String Transforday(String starttime){
        if(!TextUtils.isEmpty(starttime)){
            String Trans = starttime.substring(0,4)+"年"+starttime.substring(5,7)+"月"+starttime.substring(8,10)+"日";
            return Trans;
        }else
            return starttime;
    }

    /**格式转换("年 月 日"时分秒)
     * @param starttime
     * @return
     */
    public String Transforyue(String starttime){
        if(!TextUtils.isEmpty(starttime)){
            String Trans = starttime.substring(0,4)+"年"+starttime.substring(5,7)+"月"+starttime.substring(8,10)+"日  "+starttime.substring(11,starttime.length());
            return Trans;
        }else
            return starttime;
    }
}
