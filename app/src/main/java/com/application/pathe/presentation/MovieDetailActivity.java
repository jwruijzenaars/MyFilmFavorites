package com.application.pathe.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.application.pathe.R;
import com.application.pathe.domain.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class MovieDetailActivity extends AppCompatActivity {

    private TextView detailTitle;
    private TextView detailRatingTitle;
    private TextView detailRatingText;
    private ImageView detailCoverImage;
    private TextView detailDescription;
    private TextView detailCastTitle;
    private TextView detailCastContent;
    private TextView detailTrailerTitle;
    private VideoView detailTrailerContent;
    private ScrollView scrollview;
//
   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_detail);

       detailTitle = findViewById(R.id.tv_movie_detail_title);
       detailRatingTitle = findViewById(R.id.tv_movie_detail_rating_title);
       detailRatingText = findViewById(R.id.tv_movie_detail_rating_text);
       detailCoverImage = findViewById(R.id.iv_movie_detail_cover);
       detailDescription = findViewById(R.id.tv_movie_detail_description);
       detailCastTitle = findViewById(R.id.tv_movie_detail_cast_title);
       detailCastContent = findViewById(R.id.tv_movie_detail_cast_content);
       detailTrailerTitle = findViewById(R.id.tv_movie_detail_trailer_title);
       detailTrailerContent = findViewById(R.id.vv_movie_detail_trailer_video);
       scrollview = findViewById(R.id.sv_detail_scrollview);

       Intent intent = getIntent();
       Movie movie = (Movie) intent.getSerializableExtra("Movie");


       detailTitle.setText(movie.getTitle());

       detailRatingTitle.setText("Rating");

       detailRatingText.setText(movie.getRating());

           Picasso.get().load(movie.getImgUrl()).into(detailCoverImage);

       detailDescription.setText(movie.getDescription());
       detailCastTitle.setText("Cast");

       String castString = "";
       ArrayList<String> castMembers = movie.getCastMember();
       for (int i = 0; i < movie.getCastMember().size(); i++) {
           castString += castMembers.get(i) + "\n";
       }

       detailCastContent.setText(castString);

       detailTrailerTitle.setText("Trailer");
       detailTrailerContent.setVideoPath(movie.getTrailerUrl());
       (findViewById(R.id.sv_detail_scrollview)).post(new Runnable() {
           public void run() {
               ((ScrollView) findViewById(R.id.sv_detail_scrollview)).fullScroll(View.FOCUS_UP);
           }
       });
   }
}
