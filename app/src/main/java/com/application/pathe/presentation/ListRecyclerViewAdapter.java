package com.application.pathe.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Lists;
import com.application.pathe.domain.Movie;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

    private ListRecyclerViewAdapter.ListClickListener listener;
    private List<Lists> mListsList;

    ListRecyclerViewAdapter(List<Lists> ListsList, ListClickListener listener) {
        this.mListsList = ListsList;
        this.listener = listener;
    }

    @Override
    public ListRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        View listsListItem = inflator.inflate(R.layout.list_recycler_item, parent, false);
        ListRecyclerViewAdapter.ViewHolder viewHolder = new ListRecyclerViewAdapter.ViewHolder(listsListItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        Lists mList = mListsList.get(position);

        holder.name.setText(mList.getListName());
    }

    @Override
    public int getItemCount() {
        if (mListsList != null) {
            return mListsList.size();
        } else {return 0;}

    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tv_list_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }
    }

    public interface ListClickListener {
        public void onItemClick(int position);
    }

}

