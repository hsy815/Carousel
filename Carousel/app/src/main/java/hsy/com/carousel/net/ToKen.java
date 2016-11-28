package hsy.com.carousel.net;

import java.util.List;
import java.util.Map;

import hsy.com.carousel.entity.Ball;
import hsy.com.carousel.entity.Content;
import hsy.com.carousel.entity.ResultModel;
import hsy.com.carousel.entity.Ronghub;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by hsy on 16/8/3.
 */

public class ToKen {

    public static Call<ResultModel<Ronghub>> getCode(Map<String, RequestBody> map){
        return RetrofitNet.request().getCode(map);
    }

    public static Call<ResultModel<Content>> getball(String map){
        return RetrofitNet.request().getBall(map);
    }
}
