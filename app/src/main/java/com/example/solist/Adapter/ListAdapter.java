package com.example.solist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Model.ListVO;
import com.example.solist.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<ListVO> mDataList = new ArrayList<>();

    public ListAdapter() {
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListVO items = mDataList.get(position);
        holder.contents.setText(items.getContents());
        holder.status.setText(items.getStatus());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contents;
        Button status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.list_contents_text);
            status = itemView.findViewById(R.id.list_status_button);
        }
    }

    public void add(ListVO listVO) {
        mDataList.add(listVO);
        notifyDataSetChanged();
    }
}
