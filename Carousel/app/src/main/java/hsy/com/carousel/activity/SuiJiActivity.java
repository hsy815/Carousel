package hsy.com.carousel.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hsy.com.carousel.R;
import hsy.com.carousel.entity.Ball;
import hsy.com.carousel.entity.Content;
import hsy.com.carousel.entity.ResultModel;
import hsy.com.carousel.net.ToKen;
import hsy.com.carousel.util.ThreadUtil;
import retrofit2.Call;
import retrofit2.Response;

public class SuiJiActivity extends AppCompatActivity {

    @BindView(R.id.look)
    TextView look;
    @BindView(R.id.ji1)
    Button ji1;
    @BindView(R.id.ji2)
    Button ji2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sui_ji);
        ButterKnife.bind(this);
        //getball();
    }

    @OnClick({R.id.ji1, R.id.ji2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ji1:
                String ball = getBall();
//                String ball = GetBall();
                SpannableString msp = new SpannableString(ball);
                msp.setSpan(new ForegroundColorSpan(Color.RED), 0, (ball.length() - 4), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色
                SpannableString msps = new SpannableString(msp);
                msps.setSpan(new ForegroundColorSpan(Color.BLUE), (ball.length() - 2), ball.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色
                look.setText(msps);


                break;
            case R.id.ji2:
                look.setText(getFiveBall());
                break;
        }
    }

    public String getFiveBall() {
        String fiveball = "";
        for (int i = 0; i < 5; i++) {
            fiveball = fiveball + getBall() + "\n";
        }
        return fiveball;
    }

    /**
     * @return 获取一组并做初始化
     */
    public String getBall() {
        String ball = "";
        int[] setred = randomCommon(1, 34, 6);
        int[] setblue = randomCommon(1, 17, 1);

        for (Integer integer : setred) {
            if (integer < 10) {
                String a = "0" + integer;
                ball = ball + a + " ";
            } else {
                ball = ball + integer + " ";
            }
        }

        if (setblue[0] < 10) {
            String a = "0" + setblue[0];
            ball = ball + " + " + a;
        } else {
            ball = ball + " + " + setblue[0];
        }

        return ball;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public int CommonCount(int[] a) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int b = a[i];
            if (!map.containsKey(b)) {
                map.put(b, 1);
            } else {
                int count = map.get(b);
                count++;
                map.put(b, count);
            }
        }

        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        int c = 0, key = 0;
        for (Map.Entry<Integer, Integer> entry : set) {
            if (c < entry.getValue()) {
                c = entry.getValue();
                key = entry.getKey();
            }
        }
        return key;
    }

    public String GetBall() {
        String ball = "";
        final int[] num = new int[6];
        final int[] numblue = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < num.length; i++) {
                        num[i] = CommonCount(randomCommon(1, 33, 100));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    numblue[0] = CommonCount(randomCommon(1, 16, 10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Integer integer : num) {
            if (integer < 10) {
                String a = "0" + integer;
                ball = ball + a + " ";
            } else {
                ball = ball + integer + " ";
            }
        }

        if (numblue[0] < 10) {
            String a = "0" + numblue[0];
            ball = ball + " + " + a;
        } else {
            ball = ball + " + " + numblue[0];
        }

        return ball;
    }
/*
    private Response<ResultModel<Content>> response;
    private void getball(){
        ThreadUtil.threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    response = ToKen.getball(1+"").execute();
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    message.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("ball","-----------"+response.body().getContent().getTotal()+"-------------");
                   *//* Log.e("ball", "-----------" + response.body().getContent().data.size() + "-------------");
                    List<String> blue = response.body().getContent().data.get(1).content.get(0).result.getBlue();
                    List<String> red = response.body().getContent().data.get(1).content.get(0).result.getRed();
                    String ball = "";
                    for (String s : red) {
                        ball = ball + s + " ";
                    }

                    ball = ball + " + " + blue.get(0);

                    Log.e("ball", "-----------" + ball + "-------------");*//*
                    break;
            }
        }
    };*/
}
