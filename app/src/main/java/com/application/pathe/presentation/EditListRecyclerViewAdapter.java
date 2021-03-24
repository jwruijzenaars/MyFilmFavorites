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

public class EditListRecyclerViewAdapter extends RecyclerView.Adapter<EditListRecyclerViewAdapter.ViewHolder> {

    private List<Movie> mMoviesList;
    private EditListRecyclerViewAdapter.ButtonClickListener listener;

    public EditListRecyclerViewAdapter(List<Movie> mMoviesList, EditListRecyclerViewAdapter.ButtonClickListener listener) {
        this.mMoviesList = mMoviesList;
        this.listener = listener;
    }

    @Override
    public EditListRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        View movieListItem = inflator.inflate(R.layout.movie_recycler_item, parent, false);
        EditListRecyclerViewAdapter.ViewHolder viewHolder = new EditListRecyclerViewAdapter.ViewHolder(movieListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (EditListRecyclerViewAdapter.ViewHolder holder, int position) {
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
        public Button deleteMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_movie_title);
            this.deleteMovie = itemView.findViewById(R.id.btn_movie_delete);
            deleteMovie.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }
    }

    public interface ButtonClickListener {
        public void onItemClick(int position);
    }

}
