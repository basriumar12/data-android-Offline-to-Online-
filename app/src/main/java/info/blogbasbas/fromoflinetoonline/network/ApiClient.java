package info.blogbasbas.fromoflinetoonline.network;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://wisata-smg-basri.000webhostapp.com/oflinetoonline/oflinetoonline/")
                   // .baseUrl("http://192.168.1.16/oflinetoonline/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
