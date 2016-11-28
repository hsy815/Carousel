package hsy.com.carousel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hsy.com.carousel.R;
/*import hsy.com.carousel.bean.A;
import hsy.com.carousel.util.DaggerComponet;*/

public class PassWordActivity extends AppCompatActivity {

    @BindView(R.id.edit_pass) EditText edit_pass;
    @BindView(R.id.edit_img1) ImageView edit_img1;
    @BindView(R.id.edit_img2) ImageView edit_img2;
    @BindView(R.id.edit_img3) ImageView edit_img3;
    @BindView(R.id.edit_img4) ImageView edit_img4;
    @BindView(R.id.test_text) TextView test_text;
    private String password;
/*
    @Inject
    A a;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initview();
    }

    private void initview() {

        edit_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s + "";
                switch (s.length()) {
                    case 0:
                        edit_img1.setImageResource(R.mipmap.icon_point);
                        edit_img2.setImageResource(R.mipmap.icon_point);
                        edit_img3.setImageResource(R.mipmap.icon_point);
                        edit_img4.setImageResource(R.mipmap.icon_point);
                        break;
                    case 1:
                        edit_img1.setImageResource(R.mipmap.icon_point_pre);
                        edit_img2.setImageResource(R.mipmap.icon_point);
                        edit_img3.setImageResource(R.mipmap.icon_point);
                        edit_img4.setImageResource(R.mipmap.icon_point);
                        break;
                    case 2:
                        edit_img1.setImageResource(R.mipmap.icon_point_pre);
                        edit_img2.setImageResource(R.mipmap.icon_point_pre);
                        edit_img3.setImageResource(R.mipmap.icon_point);
                        edit_img4.setImageResource(R.mipmap.icon_point);
                        break;
                    case 3:
                        edit_img1.setImageResource(R.mipmap.icon_point_pre);
                        edit_img2.setImageResource(R.mipmap.icon_point_pre);
                        edit_img3.setImageResource(R.mipmap.icon_point_pre);
                        edit_img4.setImageResource(R.mipmap.icon_point);
                        break;
                    case 4:
                        edit_img1.setImageResource(R.mipmap.icon_point_pre);
                        edit_img2.setImageResource(R.mipmap.icon_point_pre);
                        edit_img3.setImageResource(R.mipmap.icon_point_pre);
                        edit_img4.setImageResource(R.mipmap.icon_point_pre);
                        break;
                }
                Log.i("Password======>",password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*a = DaggerComponet.builder().build().a();
        test_text.setText("姓名:"+a.getName()+", 密码:"+a.getPassword());*/
    }
}
