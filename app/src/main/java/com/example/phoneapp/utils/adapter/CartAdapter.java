package com.example.phoneapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.model.PhoneCart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<PhoneCart> phoneCartList;
    private CartClickedListeners cartClickedListeners;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
        phoneCartList = new ArrayList<>();
    }

    public void setPhoneCartList(List<PhoneCart> phoneCartList) {
        this.phoneCartList = phoneCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        PhoneCart phoneCart = phoneCartList.get(position);
        holder.phoneImageView.setImageResource(phoneCart.getPhoneImage());
        holder.phoneBrandNameTv.setText(phoneCart.getPhoneBrandName());
        holder.phoneNameTv.setText(phoneCart.getPhoneName());
        holder.phonePriceTv.setText(String.valueOf(phoneCart.getPhonePrice()));
        holder.phoneQuantityTv.setText(String.valueOf(phoneCart.getQuantity()));

        holder.deletePhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(phoneCart);
            }
        });

        holder.addQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(phoneCart);
            }
        });

        holder.minusQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(phoneCart);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneCartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView phoneImageView, deletePhoneButton;
        private TextView phoneNameTv, phoneBrandNameTv, phonePriceTv, phoneQuantityTv;
        private ImageButton addQuantityButton, minusQuantityButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneImageView = itemView.findViewById(R.id.eachCartItemIV);
            phoneNameTv = itemView.findViewById(R.id.eachCartItemName);
            phoneBrandNameTv = itemView.findViewById(R.id.eachCartItemBrandNameTv);
            phonePriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            phoneQuantityTv = itemView.findViewById(R.id.eachCartItemQuantityTV);
            deletePhoneButton = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            addQuantityButton = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityButton = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }

    public interface CartClickedListeners {
        void onDeleteClicked(PhoneCart phoneCart);
        void onPlusClicked(PhoneCart phoneCart);
        void onMinusClicked(PhoneCart phoneCart);
    }
}
