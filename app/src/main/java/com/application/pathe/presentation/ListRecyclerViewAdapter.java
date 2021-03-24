package com.application.pathe.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Movie;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

    private List<Movie> mMoviesList;
    private MovieRecyclerViewAdapter.MovieClickListener listener;

    public ListRecyclerViewAdapter(List<Movie> mMoviesList, MovieRecyclerViewAdapter.MovieClickListener listener) {
        this.mMoviesList = mMoviesList;
        this.listener = listener;
    }

    @Override
    public ListRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        View movieListItem = inflator.inflate(R.layout.movie_recycler_item, parent, false);
        ListRecyclerViewAdapter.ViewHolder viewHolder = new ListRecyclerViewAdapter.ViewHolder(movieListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ListRecyclerViewAdapter.ViewHolder holder, int position) {
        Movie mMovie = mMoviesList.get(position);

        holder.title.setText(mMovie.getTitle());
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView title;
        public Button share;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_movie_title);
            this.share =  itemView.findViewById(R.id.btn_share_list);
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

