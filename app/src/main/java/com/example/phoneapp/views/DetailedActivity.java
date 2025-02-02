package com.example.phoneapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.model.PhoneCart;
import com.example.phoneapp.utils.model.PhoneItem;
import com.example.phoneapp.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ImageView phoneImageView;
    private TextView phoneNameTV, phoneBrandNameTV, phonePriceTV;
    private AppCompatButton addToCartBtn;
    private PhoneItem phone;
    private CartViewModel viewModel;
    private List<PhoneCart> phoneCartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        phone = getIntent().getParcelableExtra("phoneItem");
        initializeVariables();

        viewModel.getAllCartItems().observe(this, new Observer<List<PhoneCart>>() {
            @Override
            public void onChanged(List<PhoneCart> phoneCart) {
                phoneCartList.addAll(phoneCart);
            }
        });
        if (phone != null){
            setDataToWidget();
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToRoom();
            }
        });

    }

    private void insertToRoom(){
        PhoneCart phoneCart = new PhoneCart();
        phoneCart.setPhoneName(phone.getPhoneName());
        phoneCart.setPhoneBrandName(phone.getPhoneBrandName());
        phoneCart.setPhonePrice(phone.getPhonePrice());
        phoneCart.setPhoneImage(phone.getPhoneImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!phoneCartList.isEmpty()){
            for(int i=0;i<phoneCartList.size();i++){
                if(phoneCart.getPhoneName().equals(phoneCartList.get(i).getPhoneName())){
                    quantity[0] = phoneCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = phoneCartList.get(i).getId();
                }
            }
        }

        if (quantity[0]==1){
            phoneCart.setQuantity(quantity[0]);
            phoneCart.setTotalItemPrice(quantity[0]*phoneCart.getPhonePrice());
            viewModel.insertCartItem(phoneCart);
        } else{
            viewModel.updateQuantity(id[0] , quantity[0]);
            viewModel.updatePrice(id[0] , quantity[0]*phoneCart.getPhonePrice());
        }

        // Update the total cart price
        double totalCartPrice = 0;
        for (PhoneCart cartItem : phoneCartList) {
            totalCartPrice += cartItem.getTotalItemPrice();
        }
        // Update the UI to reflect the new total cart price

        startActivity(new Intent(DetailedActivity.this , CartActivity.class));
    }

    private  void setDataToWidget(){
        phoneNameTV.setText(phone.getPhoneName());
        phoneBrandNameTV.setText(phone.getPhoneBrandName());
        phonePriceTV.setText(String.valueOf(phone.getPhonePrice()));
        phoneImageView.setImageResource(phone.getPhoneImage());
    }

    private void initializeVariables(){

        phoneCartList = new ArrayList<>();
        phoneImageView = findViewById(R.id.detailActivityPhoneIV);
        phoneNameTV = findViewById(R.id.detailActivityPhoneNameTv);
        phoneBrandNameTV = findViewById(R.id.detailActivityPhoneBrandNameTv);
        phonePriceTV = findViewById(R.id.detailActivityPhonePriceTv);
        addToCartBtn = findViewById(R.id. detailActivityAddToCartBtn);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);

    }
}