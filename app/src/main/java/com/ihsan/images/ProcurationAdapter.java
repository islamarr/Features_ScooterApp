package com.ihsan.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProcurationAdapter extends RecyclerView.Adapter<ProcurationAdapter.ViewHolder> {

    private List<ItemModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    ProcurationAdapter(Context context, List<ItemModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.procuration_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModel proc = mData.get(position);
        try {
            InputStream ims = context.getAssets().open(proc.getColumn1());
            Drawable d = Drawable.createFromStream(ims, null);
            holder.myImage.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseLocationActivity.class);
                intent.putExtra("procuration_id", proc.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myImage;

        ViewHolder(View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.imageView);
        }

    }

}