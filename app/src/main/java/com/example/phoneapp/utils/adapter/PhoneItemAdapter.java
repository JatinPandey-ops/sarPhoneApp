package com.example.phoneapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.model.PhoneItem;

import java.util.List;

public class PhoneItemAdapter extends RecyclerView.Adapter<PhoneItemAdapter.PhoneItemViewHolder> {

    private List<PhoneItem> phoneItemList;
    private PhoneClickedListeners phoneClickedListeners;
    public PhoneItemAdapter(PhoneClickedListeners phoneClickedListeners){
        this.phoneClickedListeners = phoneClickedListeners;
    }
    public void setPhoneItemList(List<PhoneItem> phoneItemList){
        this.phoneItemList = phoneItemList;
    }
    @NonNull
    @Override
    public PhoneItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_phone , parent , false);
        return new PhoneItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneItemViewHolder holder, int position) {
        PhoneItem phoneItem = phoneItemList.get(position);
        holder.phoneNameTv.setText(phoneItem.getPhoneName());
        holder.phoneBrandNameTv.setText(phoneItem.getPhoneBrandName());
        holder.phonePriceTv.setText(String.valueOf(phoneItem.getPhonePrice()));
        holder.phoneImageView.setImageResource(phoneItem.getPhoneImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneClickedListeners.onCardClicked(phoneItem);
            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneClickedListeners.onAddToCartBtnClicked(phoneItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (phoneItemList == null){
            return 0;
        }else {
           return phoneItemList.size();
        }
    }

    public class PhoneItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView phoneImageView, addToCartBtn;
        private TextView phoneNameTv, phoneBrandNameTv, phonePriceTv;
        private CardView cardView;
        public PhoneItemViewHolder(@NonNull View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.eachPhoneCardView);
            addToCartBtn = itemView.findViewById(R.id.eachPhoneAddToCartBtn);
            phoneNameTv = itemView.findViewById(R.id.eachPhoneName);
            phoneImageView = itemView.findViewById(R.id.eachPhoneIv);
            phoneBrandNameTv = itemView.findViewById(R.id.eachPhoneBrandNameTv);
            phonePriceTv = itemView.findViewById(R.id.eachPhonePriceTv);
        }
    }

    public interface PhoneClickedListeners{
        void onCardClicked(PhoneItem phone);
        void onAddToCartBtnClicked(PhoneItem phoneItem);
    }
}
