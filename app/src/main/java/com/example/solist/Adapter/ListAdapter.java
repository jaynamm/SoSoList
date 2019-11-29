package com.example.solist.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Database.ListVO;
import com.example.solist.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListVO> mList = new ArrayList<>();
    private Context mContext;
    private static final String[] STATUS_TEXT = {"다했당", "하는듕", "내일행"};

    private ListAdapterClickListener mListener;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListVO items = mList.get(position);
        holder.contents.setText(items.getContents());
        holder.status.setText(STATUS_TEXT[items.getStatus()]);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contents;
        private Button status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.list_contents_text);
            status = itemView.findViewById(R.id.list_status_button);
            status.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onStatusClick(mList.get(position));
                }
            });
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mList.get(position));
                }
            });
        }
    }

    // DB가 변경되면 자동으로 변경된다.
    public void setLists(List<ListVO> lists) {
        this.mList = lists;
        notifyDataSetChanged();
    }

    // 해당 리스트 포지션을 가져온다.
    public ListVO getListAt(int position) {
        return mList.get(position);
    }

    public interface ListAdapterClickListener {
        void onItemClick(ListVO listVO);
        void onStatusClick(ListVO listVO);
    }

    public void setOnItemClickListener(ListAdapterClickListener listener) {
        mListener = listener;
    }

    public void setOnStatusClickListener(ListAdapterClickListener listener) {
        mListener = listener;
    }
}
