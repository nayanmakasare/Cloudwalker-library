package tv.cloudwalker.cwlibrary;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import tv.cloudwalker.cwlibrary.models.MovieTile;

public class CloudwalkerCardPresenter extends Presenter
{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        final View infoView = v.findViewById(R.id.infoView);
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    infoView.setVisibility(View.VISIBLE);
                }else {
                    view.findViewById(R.id.infoView).setVisibility(View.INVISIBLE);
                }
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object movieTileObject) {
        ImageView posterImageView, iconImageView;
        TextView titleTextView , contentTextView;
        posterImageView = (ImageView) viewHolder.view.findViewById(R.id.posterImageView);
        iconImageView = (ImageView) viewHolder.view.findViewById(R.id.iconImageView);
        titleTextView = (TextView) viewHolder.view.findViewById(R.id.titleText);
        contentTextView = (TextView) viewHolder.view.findViewById(R.id.contentText);

        if(movieTileObject instanceof MovieTile) {
            MovieTile movieTile = (MovieTile) movieTileObject;
            if(movieTile.getTitle() != null){
                titleTextView.setText(movieTile.getTitle());
            }
            if(movieTile.getTileContentText() != null){
                contentTextView.setText(movieTile.getTileContentText());
            }
            if(movieTile.getTileBadgeIcon() != null){
                iconImageView.setImageDrawable(movieTile.getTileBadgeIcon());
            }
            if(movieTile.getTileWidth() != null && movieTile.getTileHeight() != null && movieTile.getPoster() != null && movieTile.getRowLayout() != null ){

                if(movieTile.getRowLayout().compareToIgnoreCase("landscape")==0)
                {
                    Glide.with(viewHolder.view.getContext())
                            .load(movieTile.getPoster())
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .override(Integer.parseInt(movieTile.getTileWidth()),Integer.parseInt(movieTile.getTileHeight()))
                            .skipMemoryCache(true)
                            .into(posterImageView);

                }else if(movieTile.getRowLayout().compareToIgnoreCase("square")==0 || movieTile.getRowLayout().compareToIgnoreCase("portrait") == 0)
                {
                    Glide.with(viewHolder.view.getContext())
                            .load(movieTile.getPortrait())
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .override(Integer.parseInt(movieTile.getTileWidth()),Integer.parseInt(movieTile.getTileHeight()))
                            .skipMemoryCache(true)
                            .into(posterImageView);
                }

                ViewGroup.LayoutParams layoutParams = viewHolder.view.getLayoutParams();
                layoutParams.width =  Integer.parseInt(movieTile.getTileWidth());
                layoutParams.height = Integer.parseInt(movieTile.getTileHeight()) ;
                viewHolder.view.setLayoutParams(layoutParams);

            }else {
                setLayoutOfTile(movieTile,viewHolder.view.getContext(),viewHolder.view, posterImageView);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }



    private void setLayoutOfTile(MovieTile movieTile, Context context, View view, ImageView imageView)
    {
        if(movieTile != null && movieTile.getRowLayout() != null)
        {
            switch (movieTile.getRowLayout())
            {
                case"portrait" :
                {
                    if (movieTile.getTitle().equals("Refresh")) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_refresh_black_24dp));
                    } else {
                        int width = dpToPx(context , context.getResources().getInteger(R.integer.tilePotraitWidth));
                        int height = dpToPx(context , context.getResources().getInteger(R.integer.tilePotraitHeight));
                        Glide.with(context)
                                .load(movieTile.getPortrait())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .override(width, height)
                                .skipMemoryCache(true)
                                .into(imageView);
                    }
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = dpToPx(context , context.getResources().getInteger(R.integer.tilePotraitWidth))  ;
                    layoutParams.height =  dpToPx(context , context.getResources().getInteger(R.integer.tilePotraitHeight));
                    view.setLayoutParams(layoutParams);
                }
                break;

                case "square":
                {
                    if (movieTile.getTitle().equals("Refresh")) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_refresh_black_24dp));
                    } else {
                        int width = dpToPx(context , context.getResources().getInteger(R.integer.tileSquareWidth));
                        int height = dpToPx(context , context.getResources().getInteger(R.integer.tileSquareHeight));
                        Glide.with(context)
                                .load(movieTile.getPortrait())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .override(width, height)
                                .skipMemoryCache(true)
                                .into(imageView);
                    }
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = dpToPx(context , context.getResources().getInteger(R.integer.tileSquareWidth)) ;
                    layoutParams.height =  dpToPx(context , context.getResources().getInteger(R.integer.tileSquareHeight));
                    view.setLayoutParams(layoutParams);
                }
                break;

                case "landscape":
                {
                    if (movieTile.getTitle().equals("Refresh")) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_refresh_black_24dp));
                    } else {
                        int width = dpToPx(context , context.getResources().getInteger(R.integer.tileLandScapeWidth));
                        int height = dpToPx(context , context.getResources().getInteger(R.integer.tileLandScapeHeight));
                        Glide.with(context)
                                .load(movieTile.getPoster())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .override(width, height)
                                .skipMemoryCache(true)
                                .into(imageView);
                    }
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = dpToPx(context , context.getResources().getInteger(R.integer.tileLandScapeWidth))  ;
                    layoutParams.height =  dpToPx(context , context.getResources().getInteger(R.integer.tileLandScapeHeight));
                    view.setLayoutParams(layoutParams);
                }
                break;
            }
        }
        else
        {
            if (movieTile.getTitle().equals("Refresh")) {
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_refresh_black_24dp));
            } else {
                int width = dpToPx(context , context.getResources().getInteger(R.integer.defaulttileWidth));
                int height = dpToPx(context , context.getResources().getInteger(R.integer.deafulttileHeight));
                Glide.with(context)
                        .load(movieTile.getPoster())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .override(width, height)
                        .skipMemoryCache(true)
                        .into(imageView);
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = dpToPx(context , context.getResources().getInteger(R.integer.defaulttileWidth))  ;
            layoutParams.height =  dpToPx(context , context.getResources().getInteger(R.integer.deafulttileHeight));
            view.setLayoutParams(layoutParams);
        }
    }

    private int dpToPx(Context ctx , int dp) {
        float density = ctx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


}
