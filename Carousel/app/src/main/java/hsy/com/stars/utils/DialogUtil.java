package hsy.com.carousel.com.stars.utils;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by hsy on 16/6/28.
 */
public class DialogUtil {

    private static DialogUtil dialogUtil;

    private ProgressDialog progressDialog;

    private DialogUtil(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
    }

    public static DialogUtil getInstance(Context context, String msg) {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(context, msg);
            return dialogUtil;
        } else {
            if (dialogUtil.progressDialog != null) {
                dialogUtil.progressDialog.cancel();
            }
            dialogUtil = new DialogUtil(context, msg);
            return dialogUtil;
        }
    }

    public ProgressDialog getDialog() {
        return progressDialog;
    }

}
