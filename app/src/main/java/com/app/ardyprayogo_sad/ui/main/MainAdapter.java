package com.app.ardyprayogo_sad.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ardyprayogo_sad.R;
import com.app.ardyprayogo_sad.model.Data;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Data> mList = new ArrayList<>();
    private Click mListener;

    public MainAdapter(Context mContext, Click mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_main_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Data data = mList.get(position);
        String firstName = data.getName().getFirst();
        String lastName = data.getName().getLast();
        holder.tvText.setText(firstName + " " + lastName);
        Glide.with(mContext)
                .load(data.getPicture().getThumbnail())
                .into(holder.ivPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItem(data);
            }
        });
    }

    public void setList(ArrayList<Data> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvText;
        private ImageView ivPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tv_name);
            ivPic = itemView.findViewById(R.id.iv_contact);
        }
    }

    public interface Click {
        void onItem(Data data);
    }
}
