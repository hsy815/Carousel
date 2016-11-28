package hsy.com.carousel.interfaces;

import java.util.Map;

import hsy.com.carousel.entity.Content;
import hsy.com.carousel.entity.ResultModel;
import hsy.com.carousel.entity.Ronghub;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/*
 * Created by hsy on 16/4/18.
 */

public interface APIService {

    @Multipart
    @POST("/user/getToken.json")
    Call<ResultModel<Ronghub>> getCode(@PartMap Map<String, RequestBody> map);
    //Call<ResultModel<Ronghub>> getCode(@Field("userId") String userId,@Field("name") String name,@Field("portraitUri") String portraitUri);

    @GET("/api/aladdin/queryAladdinModule")//?lotteryType=1
    Call<ResultModel<Content>> getBall(@Query("lotteryType") String code);

}
