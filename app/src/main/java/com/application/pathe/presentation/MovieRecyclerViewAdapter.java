package com.application.pathe.presentation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private List<Movie> mMoviesList;
    private MovieClickListener listener;

    public MovieRecyclerViewAdapter(List<Movie> mMoviesList, MovieClickListener listener) {
        this.mMoviesList = mMoviesList;
        this.listener = listener;
    }

    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        View movieListItem = inflator.inflate(R.layout.movie_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(movieListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        Movie mMovie = mMoviesList.get(position);

        holder.title.setText(mMovie.getTitle());
        holder.rating.setText(mMovie.getRating());
        Picasso.get().load(mMovie.getImgUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView image;
        public TextView title;
        public TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.iv_movie_image);
            this.title = itemView.findViewById(R.id.tv_movie_title);
            this.rating =  itemView.findViewById(R.id.tv_movie_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }
    }

    public interface MovieClickListener {
        public void onItemClick(int position);
    }
}


