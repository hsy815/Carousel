package hsy.com.carousel.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import hsy.com.carousel.R;
import hsy.com.carousel.测试.测试;
//import javax.inject.Inject;

//import hsy.com.carousel.bean.A;
//import hsy.com.carousel.bean.B;
//import hsy.com.carousel.util.DaggerComponet;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener{

    View main_view;

    /* @Inject
    B b;

    @Inject
    A a;
*/
    private MaterialCalendarView mcv;
    private MaterialCalendarView mcv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_view= getLayoutInflater().inflate(R.layout.activity_calendar, null);
        main_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        main_view.setOnClickListener(this);
        setContentView(main_view);
        initview();
    }

    private void initview() {
        mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2016, 6, 1))//实际时间2016,7,1
                .setMaximumDate(CalendarDay.from(2016, 6, 7))//实际时间2016,7,7
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        mcv2 = (MaterialCalendarView) findViewById(R.id.calendarView2);
        mcv2.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2016, 6, 8))
                .setMaximumDate(CalendarDay.from(2016, 6, 15))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        测试 测试 = new 测试();
        测试.打印一句话();

      /*  b = DaggerComponet.builder().build().b();
        b.b1();

        a = DaggerComponet.builder().build().a();
        a.setName("哈哈");
        a.setPassword("123456");
*/
    }

    public void PassWord(View view) {
       // Log.i("Password======>","姓名:"+a.getName()+", 密码:"+a.getPassword());
        startActivity(new Intent(CalendarActivity.this, PassWordActivity.class));
    }

    public void On_Snackbar(View view){

    }

    @Override
    public void onClick(View v) {
        int i = main_view.getSystemUiVisibility();
        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {//2
            main_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE) {//0
            main_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {//1
            main_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}
