package hsy.com.carousel.jpushdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import hsy.com.carousel.R;

public class DailogActivity extends Activity {

    private TextView content;
    private TextView ok;
    private String title;
    private String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            contents = bundle.getString(JPushInterface.EXTRA_ALERT);
        }
        initview();
    }

    private void initview(){
        content = (TextView) findViewById(R.id.content);
        ok = (TextView) findViewById(R.id.ok);
        content.setText("Title : " + title + "  " + "Content : " + contents);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
