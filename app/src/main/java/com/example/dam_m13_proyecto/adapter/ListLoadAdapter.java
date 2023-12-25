package com.example.dam_m13_proyecto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dam_m13_proyecto.R;

import java.util.List;

public class ListLoadAdapter extends RecyclerView.Adapter<ListLoadAdapter.ViewHolder> {

    private List<ListLoadElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListLoadAdapter(List<ListLoadElement> mData, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ListLoadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_load_element,null);

        return new ListLoadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLoadAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<ListLoadElement> items){mData=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView originAddressTextView, originCityTextView,destinationAddressTextView, destinationCityTextView, status;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
             System.out.println("VIIIIIIISTAAA " + itemView);

             iconImage = itemView.findViewById(R.id.iconImageView);
            originAddressTextView = itemView.findViewById(R.id.originAddressTextView);
            originCityTextView = itemView.findViewById(R.id.originCityTextView);
            destinationAddressTextView = itemView.findViewById(R.id.destinationAddressTextView);
            destinationCityTextView = itemView.findViewById(R.id.destinationCityTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }
        void bindData(final ListLoadElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            originAddressTextView.setText(item.getOrigin_address());
            originCityTextView.setText(item.getOrigin_city());
            destinationAddressTextView.setText(item.getDestination_address());
            destinationCityTextView.setText(item.getDestination_city());
            status.setText(item.getStatus());


        }
    }
}
