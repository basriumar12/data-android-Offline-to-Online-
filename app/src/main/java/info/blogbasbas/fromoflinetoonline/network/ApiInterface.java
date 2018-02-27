package info.blogbasbas.fromoflinetoonline.network;


import info.blogbasbas.fromoflinetoonline.pojo.ResponseInsert;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("send.php")
    Call<ResponseInsert> insertName(@Field("name") String nama);

}
