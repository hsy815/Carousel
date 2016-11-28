package hsy.com.carousel.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hsy.com.carousel.R;
import hsy.com.carousel.adapter.RecyclerViewAdapter;
import hsy.com.carousel.view.MyRecyclerView;
import hsy.com.carousel.view.RecyclerDecoration;

public class ListViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    MyRecyclerView recyclerView;

    private RecyclerViewAdapter viewAdapter;
    private int lastVisibleItem;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        initview();
    }

    private void initview(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDecoration(this,OrientationHelper.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(this);
        viewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(viewAdapter);
        viewAdapter.addData(getData());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==viewAdapter.getItemCount()) {
                    viewAdapter.changeMoreStatus(RecyclerViewAdapter.LOADING_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> newDatas = new ArrayList<>();
                            for (int i = 0; i< 5; i++) {
                                int index = i +1;
                                newDatas.add("more item" + index);
                            }
                            viewAdapter.addItemData(newDatas);
                            viewAdapter.changeMoreStatus(RecyclerViewAdapter.PULLUP_LOAD_MORE);
                        }
                    }, 2500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =layoutManager.findLastVisibleItemPosition();
            }

        });
    }

    public List<String> getData(){
        List<String> list = new ArrayList<>();
        for (int i=1;i<=10;i++){
            list.add("item"+i);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                List<String> newDatas = new ArrayList<>();
                for (int i = 0; i <5; i++) {
                    int index = i + 1;
                    newDatas.add("new item" + index);
                }
                viewAdapter.addData(newDatas);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ListViewActivity.this, "更新了", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
}
