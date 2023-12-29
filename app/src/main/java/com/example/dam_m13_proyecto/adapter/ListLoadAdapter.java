package com.example.dam_m13_proyecto.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dam_m13_proyecto.R;
import com.example.dam_m13_proyecto.carrier.ReviewOpenLoadsActivity;

import java.nio.charset.StandardCharsets;
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
        CardView cardView;

         ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv2);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("CardView cv2 onClick()","Tap detected");
                    showDialog();
                }
            });

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
    public void showDialog(){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_bottomsheet);

        LinearLayout loadIdLayout = dialog.findViewById(R.id.layoutLoadId);

        loadIdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LoadIdLayout listener","Tap detected");
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
