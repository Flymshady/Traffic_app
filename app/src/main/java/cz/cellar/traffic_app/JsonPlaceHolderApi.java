package cz.cellar.traffic_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET(".")
    Call<List<Post>> getPosts();
}
