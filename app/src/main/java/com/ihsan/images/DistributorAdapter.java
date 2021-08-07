package com.ihsan.images;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.ViewHolder> {

    private List<DistributorModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    DistributorAdapter(Context context, List<DistributorModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.distributor_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //TODO
        DistributorModel distr = mData.get(position);
        try {
            InputStream ims = context.getAssets().open(distr.getImgRes());
            Drawable d = Drawable.createFromStream(ims, null);
            holder.backgroundImg.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

        holder.facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = distr.getFbLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        holder.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = "http://maps.google.com/maps?q=loc:" + distr.getLatLong();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ee) {
                    Toast.makeText(context, "يجب أن تسطب تطبيق جوجل ماب", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(context, "حدث خطأ ما حاول مرة أخرى", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + distr.getPhone()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView callBtn, facebookBtn, mapBtn, backgroundImg;

        ViewHolder(View itemView) {
            super(itemView);
            callBtn = itemView.findViewById(R.id.callBtn);
            facebookBtn = itemView.findViewById(R.id.facebookBtn);
            mapBtn = itemView.findViewById(R.id.mapBtn);
            backgroundImg = itemView.findViewById(R.id.backgroundImg);
        }

    } //TODO

}