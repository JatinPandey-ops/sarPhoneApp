package com.example.phoneapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.phoneapp.R;
import com.example.phoneapp.utils.adapter.CartAdapter;
import com.example.phoneapp.utils.model.PhoneCart;
import com.example.phoneapp.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartClickedListeners {

    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private TextView totalCartPriceTv;
    private AppCompatButton checkoutBtn;
    private double checkoutPrice;
    private CardView cardView;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeVariables();

        cartViewModel.getAllCartItems().observe(this, new Observer<List<PhoneCart>>() {
            @Override
            public void onChanged(List<PhoneCart> phoneCart) {
                Log.d("CartActivity", "Received cart items: " + phoneCart.size());
                double price = 0;
                for (int i = 0; i < phoneCart.size(); i++) {
                    price = price + phoneCart.get(i).getTotalItemPrice();
                }
                totalCartPriceTv.setText(String.valueOf(price));
                cartAdapter.setPhoneCartList(phoneCart); // Update the adapter with the observed data
                Log.d("CartActivity", "Updated adapter with cart items");
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.deleteAllCartItems();
                cardView.setVisibility(View.VISIBLE);
                checkoutBtn.setVisibility(View.INVISIBLE);
                totalCartPriceTv.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initializeVariables() {
        cartAdapter = new CartAdapter(this);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter); // Set the adapter to the RecyclerView
        Log.d("CartActivity", "Set adapter to RecyclerView");

        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        checkoutBtn = findViewById(R.id.cartActivityCheckoutBtn);
        cardView = findViewById(R.id.cartActivityCardView);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    @Override
    public void onDeleteClicked(PhoneCart phoneCart) {
        cartViewModel.deleteCartItem(phoneCart);
        cartAdapter.notifyDataSetChanged(); // Notify the adapter to update the UI
        Log.d("CartActivity", "Deleted cart item");
    }

    @Override
    public void onPlusClicked(PhoneCart phoneCart) {
        int quantity = phoneCart.getQuantity() + 1;
        cartViewModel.updateQuantity(phoneCart.getId(), quantity);
        cartViewModel.updatePrice(phoneCart.getId(), quantity * phoneCart.getPhonePrice());
        cartAdapter.notifyDataSetChanged(); // Notify the adapter to update the UI
        Log.d("CartActivity", "Updated cart item quantity");
    }

    @Override
    public void onMinusClicked(PhoneCart phoneCart) {
        int quantity = phoneCart.getQuantity() - 1;
        if (quantity != 0) {
            cartViewModel.updateQuantity(phoneCart.getId(), quantity);
            cartViewModel.updatePrice(phoneCart.getId(), quantity * phoneCart.getPhonePrice());
            cartAdapter.notifyDataSetChanged(); // Notify the adapter to update the UI
            Log.d("CartActivity", "Updated cart item quantity");
        } else {
            cartViewModel.deleteCartItem(phoneCart);
            cartAdapter.notifyDataSetChanged(); // Notify the adapter to update the UI
            Log.d("CartActivity", "Deleted cart item");
        }
    }
}
