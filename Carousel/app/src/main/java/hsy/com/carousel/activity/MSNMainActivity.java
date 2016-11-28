package hsy.com.carousel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import hsy.com.carousel.R;
import hsy.com.carousel.net.HttpUrlConnection;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MSNMainActivity extends ActionBarActivity {

    @BindView(R.id.loading_img)
    ImageView loading_img;
    // private Map<String, RequestBody> map = new HashMap<>();
    //String Token = "ZcmYdJSJPqIsQgUCHJ9CppsdP1g5ZwEJ5jJs2b1quPx5TobjQA6b0l1ACA4f+cvPjuutlBAQ3fT9zSYrulEt1I5KA8W/ClTO";
    String Token = "JeTee2aoFh2ZML0+fEaNKZsdP1g5ZwEJ5jJs2b1quPx5TobjQA6b0q50aBQ/XoIVjkaQ+zXhJSabr5ZZbt+KtrTye1/0l/h/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msnmain);
        ButterKnife.bind(this);

        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null)
            loading_img.startAnimation(operatingAnim);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //String token = LonginPost("15311118888", "逗你玩", "http://touxiang.qqzhi.com/uploads/2012-11/1111105304979.jpg");
//                Log.i("token--------", token);
//            }
//        }).start();
//        String token = LonginPosts("15311118888", "逗你玩", "http://touxiang.qqzhi.com/uploads/2012-11/1111105304979.jpg");
  /*      map.put("userId", getBody("15311118888"));
        map.put("name", getBody("逗你玩"));
        map.put("portraitUri", getBody("http://touxiang.qqzhi.com/uploads/2012-11/1111105304979.jpg"));
        ToKen.getCode(map).enqueue(new Callback<ResultModel<Ronghub>>() {
            @Override
            public void onResponse(Call<ResultModel<Ronghub>> call, Response<ResultModel<Ronghub>> response) {
                if (response.body() != null) {
                    if (response.body().getData() != null) {
                        Token = response.body().getData().getToken();
                        Longin(Token);
                        Log.e("MSNMainActivity", "Token==" + Token);
                    } else {
                        Log.e("MSNMainActivity", "Token==空2");
                    }
                } else {
                    Log.e("MSNMainActivity", "Token==空1");
                }
            }

            @Override
            public void onFailure(Call<ResultModel<Ronghub>> call, Throwable t) {

            }
        });*/
        Longin(Token);

    }

    private void Longin(String token) {
        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                Log.e("MSNMainActivity", "Token失效，重新获取");
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MSNMainActivity", "——onSuccess—-" + userId);
                loading_img.clearAnimation();
                startActivity(new Intent(MSNMainActivity.this, MSNListActivity.class));
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MSNMainActivity", "——onError—-" + errorCode);
            }
        });
    }


    public String LonginPost(String userId, String name, String portraitUri) {
        Log.e("LonginPost", "——LonginPost—-");
        //String data = "userId = 15311118888&name = 逗你玩&portraitUri = http://touxiang.qqzhi.com/uploads/2012-11/1111105304979.jpg";
        String data = "userId = " + userId + "&name = " + name + "&portraitUri = " + portraitUri;
        try {
            URL url = new URL("https://api.cn.ronghub.com/user/getToken");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/*;charset=utf-8");
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
            if (connection == null) {
                return "";
            }
            if (connection.getResponseCode() == 200) {
                DataInputStream inputStream = (DataInputStream) connection.getInputStream();
                return "haha";
            } else {
                return "wuwu";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String LonginPosts(final String userId, final String name, final String portraitUri) {
        final String url = "https://api.cn.ronghub.com/user/getToken";
        final String[] token = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUrlConnection http = new HttpUrlConnection();
                try {
                    token[0] = http.doPost(url, userId + "&" + name + "&" + portraitUri, "utf-8", 5000, 5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Log.e("token", token.length + ","+token[0]);
        return "";
    }
}
