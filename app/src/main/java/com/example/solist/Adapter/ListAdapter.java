package com.example.solist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Database.ListVO;
import com.example.solist.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListVO> mList = new ArrayList<>();
    private Context mContext;

    private OnItemClickListener listener;

    public ListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        // 화면 깜빡임 없애기
        return mList.get(position).getId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListVO items = mList.get(position);
        holder.contents.setText(items.getContents());
        holder.status.setText(items.getStatus());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contents;
        private Button status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.list_contents_text);
            status = itemView.findViewById(R.id.list_status_button);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mList.get(position));
                }
            });
        }
    }

    public void setLists(List<ListVO> lists) {
        this.mList = lists;
        notifyDataSetChanged();
    }

    public ListVO getListAt(int position) {
        return mList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(ListVO listVO);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
