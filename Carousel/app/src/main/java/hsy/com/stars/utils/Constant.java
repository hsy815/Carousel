package hsy.com.carousel.com.stars.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HSY on 16/3/2.
 */
public class Constant {

    /**
     * 检查是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 验证手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
        }

    /**
     * 手机号码加短线
     * @param phone_num
     * @return
     */
    public static String Phone(String phone_num) {
        String phones = "";
        if (TextUtils.isEmpty(phone_num)) {
            return "-";
        }

        if(phone_num.contains("-")){
            return phone_num;
        }

        if (phone_num.length() >= 11) {
            phones = phone_num.substring(0, 3) + "-" + phone_num.substring(3, 7) + "-" + phone_num.substring(7, phone_num.length());
        } else if (phone_num.length() == 10) {
            phones = phone_num.substring(0, 3) + "-" + phone_num.substring(3, 6) + "-" + phone_num.substring(6, phone_num.length());
        } else if (phone_num.length() >= 7) {
            phones = phone_num.substring(0, 3) + "-" + phone_num.substring(3, phone_num.length());
        } else if (phone_num.length() >= 3) {
            phones = phone_num;
        }
        return phones;
    }


    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

}
