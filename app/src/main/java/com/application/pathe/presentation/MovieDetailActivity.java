package com.application.pathe.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class MovieDetailActivity extends AppCompatActivity {

    private TextView detailTitle;
    private TextView detailRatingText;
    private ImageView detailCoverImage;
    private TextView detailDescription;
    private TextView detailCastTitle;
    private TextView detailCastContent;
    private VideoView detailTrailerContent;
    private Button detailShowReview;
    private TextView detailReviewTitle;
    private RecyclerView detailReviews;
    private LinearLayout detailReviewWrapper;
    private ReviewRecyclerViewAdapter detailAdapter;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_detail);

       detailTitle = findViewById(R.id.tv_movie_detail_title);
       detailRatingText = findViewById(R.id.tv_movie_detail_rating_text);
       detailCoverImage = findViewById(R.id.iv_movie_detail_cover);
       detailDescription = findViewById(R.id.tv_movie_detail_description);
       detailCastTitle = findViewById(R.id.tv_movie_detail_cast_title);
       detailCastContent = findViewById(R.id.tv_movie_detail_cast_content);
       detailTrailerContent = findViewById(R.id.vv_movie_detail_trailer_video);
       detailShowReview = findViewById(R.id.bt_show_review);
       detailReviewTitle = findViewById(R.id.tv_review_title);
       detailReviewWrapper = findViewById(R.id.ll_review_wrapper);

       Intent intent = getIntent();
       Movie movie = (Movie) intent.getSerializableExtra("Movie");

       detailReviews = findViewById(R.id.rv_reviews);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
       detailReviews.setLayoutManager(layoutManager);
       detailAdapter = new ReviewRecyclerViewAdapter(movie.getReviews(), new MovieDetailActivity.MovieClickListener());
       detailReviews.setAdapter(detailAdapter);

       detailTitle.setText(movie.getTitle());
       detailRatingText.setText(movie.getRating());
       Picasso.get().load(movie.getImgUrl()).into(detailCoverImage);

       detailDescription.setText(movie.getDescription());
       detailCastTitle.setText(R.string.tv_cast);

       String castString = "";
       ArrayList<String> castMembers = movie.getCastMember();
       for (int i = 0; i < movie.getCastMember().size(); i++) {
           castString += castMembers.get(i) + "\n";
       }

       detailCastContent.setText(castString);

       detailTrailerContent.setVideoPath(movie.getTrailerUrl());
       (findViewById(R.id.sv_detail_scrollview)).post(new Runnable() {
           public void run() {
               ((ScrollView) findViewById(R.id.sv_detail_scrollview)).fullScroll(View.FOCUS_UP);
           }
       });

       detailReviewTitle.setVisibility(View.GONE);
       detailReviews.setVisibility(View.GONE);
       detailReviewWrapper.setVisibility(View.GONE);

       detailShowReview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               System.out.println("test");
               if(detailShowReview.getText().equals("Show reviews") || detailShowReview.getText().equals("Toon reviews")) {
                   detailReviewTitle.setVisibility(View.VISIBLE);
                   detailReviews.setVisibility(View.VISIBLE);
                   detailReviewWrapper.setVisibility(View.VISIBLE);
                   detailShowReview.setText(R.string.bt_hide_review);

               } else {
                   detailReviewTitle.setVisibility(View.GONE);
                   detailReviews.setVisibility(View.GONE);
                   detailReviewWrapper.setVisibility(View.GONE);
                   detailShowReview.setText(R.string.bt_show_review);
               }
           }
       });
   }

    class MovieClickListener implements ReviewRecyclerViewAdapter.MovieClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }
}
