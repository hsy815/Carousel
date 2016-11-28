package hsy.com.carousel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hsy.com.carousel.R;
import hsy.com.carousel.adapter.RecyclerAdapter;
import hsy.com.carousel.view.MyRecyclerView;
import hsy.com.carousel.view.PullDownLoadView;

public class ListViewTwoActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    PullDownLoadView swipeRefreshLayout;

    private RecyclerAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_two);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewAdapter = new RecyclerAdapter(this,getData());
        recyclerView.setAdapter(viewAdapter);
        swipeRefreshLayout.setOnRefreshListener(new PullDownLoadView.onRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayout.stopLoadMore();
            }
        });
    }

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add("item" + i);
        }
        return list;
    }

}
