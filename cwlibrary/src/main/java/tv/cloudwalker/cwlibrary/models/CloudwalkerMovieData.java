package tv.cloudwalker.cwlibrary.models;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.cloudwalker.cwlibrary.CloudwalkerCardPresenter;
import tv.cloudwalker.cwlibrary.Utils.NetworkUtils;
import tv.cloudwalker.cwlibrary.api.ApiInterface;

import static tv.cloudwalker.cwlibrary.Utils.Utils.getSeperatedValuesWithHeader;
import static tv.cloudwalker.cwlibrary.Utils.Utils.isPackageInstalled;

public class CloudwalkerMovieData
{
    private ArrayList<ListRow> listRowArrayList;
    public static final String TAG = CloudwalkerMovieData.class.getSimpleName();

    public CloudwalkerMovieData(Context context)
    {
        initCloudwalkerMovieData(context);
    }


    private boolean initCloudwalkerMovieData(final Context context)
    {
        final boolean[] result = {false};
        if(NetworkUtils.getConnectivityStatus(context) > 0)
        {
            new Retrofit.Builder()
                    .baseUrl("http://192.168.1.143:9876/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface.class).getHomeScreenData().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
                {
                    if(response.code() ==200 && response.body() != null){
                        result[0] = true ;
                        MovieResponse movieResponse = response.body();
                        listRowArrayList = preparingMovieData(movieResponse, context);
                    } else if (response.code() == 401) {
                        result[0] = false;
                    }else{
                        result[0] = false ;
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    result[0] = false ;
                }
            });
        }
        return result[0] ;
    }


    public ArrayList<ListRow> getListRowArrayList() {
        return listRowArrayList;
    }

    private ArrayList<ListRow> preparingMovieData(MovieResponse movieResponse, Context context) {
        Log.d(TAG, "preparingMovieData: ");
        ArrayList<ListRow> listRowArrayList = new ArrayList<>();
        for(MovieRow movieRow : movieResponse.getRows())
        {
            ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(new CloudwalkerCardPresenter());
            for(MovieTile movieTile : movieRow.getRowItems())
            {
                movieTile.setRowLayout(movieRow.getRowLayout());
                setTileContent(movieTile);
                setTileDrawable(movieTile, context);
                rowAdapter.add(rowAdapter);
            }
            ListRow listRow = new ListRow(movieRow.getRowIndex(), new HeaderItem(movieRow.getRowIndex(), movieRow.getRowHeader()), rowAdapter);
            listRowArrayList.add(listRow);
        }
        if (listRowArrayList.size() > 0) {
            return listRowArrayList;
        } else {
            return null;
        }
    }


    private void setTileContent(MovieTile movieTile){
        ArrayList<String> subText = new ArrayList<>();
        if (movieTile.getRuntime() != null && movieTile.getRuntime().length() > 0) {
            subText.add(movieTile.getRuntime());
        }
        if (movieTile.getYear() != null && movieTile.getYear().length() > 0) {
            subText.add(movieTile.getYear());
        }
        if (movieTile.getGenre() != null) {
            subText.addAll(movieTile.getGenre());
            movieTile.setTileContentText(getSeperatedValuesWithHeader(" | ", "", subText));
        }
    }

    private void setTileDrawable(MovieTile movie, Context context)
    {
        if (movie.getPackageName() != null && !movie.getPackageName().equalsIgnoreCase("cloudwalker.webview")) {
            try {
                Drawable icon;
                if (movie.getPackageName().equalsIgnoreCase("com.google.android.youtube")) {
                    if (isPackageInstalled(movie.getPackageName() + ".tv", context.getPackageManager())) {
                        icon = context.getPackageManager().getApplicationIcon(movie.getPackageName() + ".tv");
                    } else {
                        icon = context.getPackageManager().getApplicationIcon(movie.getPackageName());
                    }
                } else {
                    icon = context.getPackageManager().getApplicationIcon(movie.getPackageName());
                }
                movie.setTileBadgeIcon(icon);
            }catch (PackageManager.NameNotFoundException e) {
                e.getMessage();
            }catch (NullPointerException e) {
                e.getMessage();
            }
        }
    }

}
