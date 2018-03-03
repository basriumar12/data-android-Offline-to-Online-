package info.blogbasbas.fromoflinetoonline.network;






import info.blogbasbas.fromoflinetoonline.pojo.ResponseInsert;
import info.blogbasbas.fromoflinetoonline.pojo.getdata.ResponseGetData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("send.php")
    Call<ResponseInsert> insertName(@Field("name") String nama);

   /* //interface offline
    @GET("get.php")
    Call<ResponseGetData> getdata ();*/
//interface online //
    @GET("getdata.php")
    Call<ResponseGetData> getdata ();



}
