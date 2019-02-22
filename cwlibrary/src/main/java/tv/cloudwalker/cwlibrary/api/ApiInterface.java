package tv.cloudwalker.cwlibrary.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import tv.cloudwalker.cwlibrary.models.MovieResponse;

/**
 * Created by cognoscis on 6/1/18.
 */

public interface ApiInterface {
    @GET("data1.json")
    Call<MovieResponse> getHomeScreenData();

//    @Headers({"Accept-Version: 1.0.0"})
//    @GET("cards/{tileId}")
//    Call<ArrayList<MovieTile>> getMovieTileDetails(@Path("tileId") String tileId);
//
//    @Headers({"Accept-Version: 1.0.0"})
//    @GET("related/{tileId}")
//    Call<RecommendationsResponse> getRecommendations(@Path("tileId") String tileId);
//
//    @Headers({"Accept-Version: 1.4.1"})
//    @GET("tv-la-update")
//    Call<List<UpdateList>> checkForUpdate();
//
//    @Headers({"Accept-Version: 1.0.0"})
//    @POST("add-fav")
//    Call<AddFavouriteResponse> addFavourite(@Body Addfav addfav);
//
//    @Headers({"Accept-Version: 1.0.0"})
//    @DELETE("del-fav/{userName}/{tileId}")
//    Call<DeleteFavouriteResponse> deleteFavourite(@Path("userName") String name, @Path("tileId") String tileId);
//
//    @Headers({"Accept-Version: 1.0.0"})
//    @POST("search")
//    Call<SearchResponse> getSearchResponse(@Body Search search);
//
//    @Headers({"Accept-Version: 1.0.0"})
//    @POST("tv-config")
//    Call<AddDeviceInfoResponse> postDeviceInfo(@Body AddDeviceInfo deviceInfo);
}

