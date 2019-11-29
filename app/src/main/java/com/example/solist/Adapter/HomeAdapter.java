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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<ListVO> mList = new ArrayList<>();
    private Context mContext;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_home, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListVO items = mList.get(position);
        holder.contents.setText(items.getContents());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.list_contents_text);
        }
    }

    // DB가 변경되면 자동으로 변경된다.
    public void setLists(List<ListVO> lists) {
        this.mList = lists;
        notifyDataSetChanged();
    }
}
