package hsy.com.carousel.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivehundredpx.android.blur.BlurringView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import hsy.com.carousel.CarouselView.CycleViewPager;
import hsy.com.carousel.CarouselView.CycleViewPager.ImageCycleViewListener;
import hsy.com.carousel.CarouselView.ViewFactory;
import hsy.com.carousel.R;
import hsy.com.carousel.bean.ADInfo;
import hsy.com.carousel.jpushdemo.ExampleUtil;
import hsy.com.carousel.jpushdemo.PushSetActivity;


public class MainActivity extends InstrumentedActivity implements View.OnClickListener {

    private List<ImageView> views = new ArrayList<>();
    private List<ADInfo> infos = new ArrayList<>();
    private CycleViewPager cycleViewPager;

    private String[] imageUrls = {"http://img0.imgtn.bdimg.com/it/u=2799491813,588820357&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2414756802,531263205&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2280771936,3888134874&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2327585548,1525749689&fm=21&gp=0.jpg",
            "http://b.hiphotos.baidu.com/image/h%3D300/sign=f3d57219d03f8794ccff4e2ee21a0ead/728da9773912b31bd1c15c888218367adbb4e1e6.jpg"};
//--------以上为轮播-------------------------------------------------------------------------------------------------------

    private Button mInit;
    private Button mSetting;
    private Button mStopPush;
    private Button mResumePush;
    private Button mGetRid;
    private TextView mRegId;
    private EditText msgText;
    private BlurringView mBlurringView;

    public static boolean isForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configImageLoader();
        initialize(infos);

        initView();
        registerMessageReceiver();  // used for receive msg

        initBluury();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    //--------以下为轮播-------------------------------------------------------------------------------------------------------

    /**
     * 轮播图
     *
     * @param infos
     */
    @SuppressLint("NewApi")
    private void initialize(List<ADInfo> infos) {
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(MainActivity.this,
                        position + "-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
                mBlurringView.invalidate();
            }
        }
    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

//--------以上为轮播-------------------------------------------------------------------------------------------------------

    private void initView() {
        TextView mImei = (TextView) findViewById(R.id.tv_imei);
        String udid = ExampleUtil.getImei(getApplicationContext(), "");
        if (null != udid) mImei.setText("IMEI: " + udid);

        TextView mAppKey = (TextView) findViewById(R.id.tv_appkey);
        String appKey = ExampleUtil.getAppKey(getApplicationContext());
        if (null == appKey) appKey = "AppKey异常";
        mAppKey.setText("AppKey: " + appKey);

        mRegId = (TextView) findViewById(R.id.tv_regId);
        mRegId.setText("RegId:");

        String packageName = getPackageName();
        TextView mPackage = (TextView) findViewById(R.id.tv_package);
        mPackage.setText("PackageName: " + packageName);

        String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
        TextView mDeviceId = (TextView) findViewById(R.id.tv_device_id);
        mDeviceId.setText("deviceId:" + deviceId);

        String versionName = ExampleUtil.GetVersion(getApplicationContext());
        TextView mVersion = (TextView) findViewById(R.id.tv_version);
        mVersion.setText("Version: " + versionName);

        mInit = (Button) findViewById(R.id.init);
        mInit.setOnClickListener(this);

        mStopPush = (Button) findViewById(R.id.stopPush);
        mStopPush.setOnClickListener(this);

        mResumePush = (Button) findViewById(R.id.resumePush);
        mResumePush.setOnClickListener(this);

        mGetRid = (Button) findViewById(R.id.getRegistrationId);
        mGetRid.setOnClickListener(this);

        mSetting = (Button) findViewById(R.id.setting);
        mSetting.setOnClickListener(this);

        msgText = (EditText) findViewById(R.id.msg_rec);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init:
                init();
                break;
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, PushSetActivity.class);
                startActivity(intent);
                break;
            case R.id.stopPush:
                JPushInterface.stopPush(getApplicationContext());
                break;
            case R.id.resumePush:
                JPushInterface.resumePush(getApplicationContext());
                break;
            case R.id.getRegistrationId:
                String rid = JPushInterface.getRegistrationID(getApplicationContext());
                if (!rid.isEmpty()) {
                    mRegId.setText("RegId:" + rid);
                } else {
                    Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void Calendar(View view) {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void MSN(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.msn:
                intent = new Intent(MainActivity.this, MSNMainActivity.class);
                startActivity(intent);
                break;

            case R.id.suiji:
                intent = new Intent(MainActivity.this, SuiJiActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void ListView(View view) {
        switch (view.getId()) {
            case R.id.listview1:
                Snackbar.make(view, "O(∩_∩)O哈哈~", Snackbar.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case R.id.listview2:
                startActivity(new Intent(MainActivity.this, ListViewTwoActivity.class));
                break;
        }
    }

    public void Ba(View view) {
        startActivity(new Intent(MainActivity.this, Ba_Activity.class));
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg) {
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(View.VISIBLE);
        }
    }


    private void initBluury() {

        mBlurringView = (BlurringView) findViewById(R.id.blurring_view);
        View blurredView = findViewById(R.id.xixihaha);
        mBlurringView.setBlurredView(blurredView);
        mBlurringView.invalidate();
    }

}
