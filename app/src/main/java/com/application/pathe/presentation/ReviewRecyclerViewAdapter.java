package com.application.pathe.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Review;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder>{
    private List<Review> mReviewList;
    private ReviewRecyclerViewAdapter.MovieClickListener listener;

    public ReviewRecyclerViewAdapter(List<Review> mReviewList, ReviewRecyclerViewAdapter.MovieClickListener listener) {
        this.mReviewList = mReviewList;
        this.listener = listener;
    }

    @Override
    public ReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        View reviewListItem = inflator.inflate(R.layout.review_recycler_item, parent, false);
        ReviewRecyclerViewAdapter.ViewHolder viewHolder = new ReviewRecyclerViewAdapter.ViewHolder(reviewListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ReviewRecyclerViewAdapter.ViewHolder holder, int position) {
        Review mReview = mReviewList.get(position);

        holder.content.setText(mReview.getContent());
        holder.author.setText(mReview.getAuthor());
        holder.createdAt.setText(mReview.getCreatedAt());
        holder.rating.setText(mReview.getRating());

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView content;
        public TextView author;
        public TextView createdAt;
        public TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.tv_content);
            this.author = itemView.findViewById(R.id.tv_author);
            this.createdAt =  itemView.findViewById(R.id.tv_created_at);
            this.rating = itemView.findViewById(R.id.tv_review_rating);
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
