package com.example.phoneapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.adapter.PhoneItemAdapter;
import com.example.phoneapp.utils.model.PhoneCart;
import com.example.phoneapp.utils.model.PhoneItem;
import com.example.phoneapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhoneItemAdapter.PhoneClickedListeners {


    private RecyclerView recyclerView;
    private List<PhoneItem> phoneItemList;
    private PhoneItemAdapter adapter;
    private CartViewModel viewModel;
    private List<PhoneCart> phoneCartList;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setUpList();

        viewModel.getAllCartItems().observe(this, new Observer<List<PhoneCart>>() {
            @Override
            public void onChanged(List<PhoneCart> phoneCart) {
                phoneCartList.addAll(phoneCart);
            }
        });

        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        } else {
            // Handle the case where adapter is null
            // For example, you can create a new adapter instance

            adapter.setPhoneItemList(phoneItemList);
            recyclerView.setAdapter(adapter);
        }
    }
    private void setUpList(){
        if (phoneItemList == null) {
            phoneItemList = new ArrayList<>();
        }
        phoneItemList.add(new PhoneItem("Samsung Galaxy S24 FE" , "Samsung Galaxy" , R.drawable.samsung_galaxy_s24_fe , 4299.00));
        phoneItemList.add(new PhoneItem("Samsung Galaxy S24 Ultra" , "Samsung Galaxy" , R.drawable.samsung_galaxy_s_24_ultra , 7799.00));
        phoneItemList.add(new PhoneItem("Samsung Galaxy Z Flip 6" , "Samsung Galaxy" , R.drawable.samsung_galaxy_z_flip_6 , 4999.00));
        phoneItemList.add(new PhoneItem("Samsung Galaxy Z Fold 6" , "Samsung Galaxy" , R.drawable.samsung_galaxy_z_fold_6 , 9099.00));
        phoneItemList.add(new PhoneItem("Apple Iphone 15" , "Apple" , R.drawable.apple_iphone_15 , 3799.00));
        phoneItemList.add(new PhoneItem("Apple Iphone 16" , "Apple" , R.drawable.apple_iphone_16 , 4299.00));
        phoneItemList.add(new PhoneItem("Huawei Nova 13" , "Huawei" , R.drawable.huawei_nova_13 , 2399.00));
        phoneItemList.add(new PhoneItem("Huawei Pura 70 Pro" , "Huawei" , R.drawable.huawei_pura_70_pro, 5999.00));

        if (adapter != null) {
            adapter.setPhoneItemList(phoneItemList);
        }
    }

    private void initializeVariables(){

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        phoneCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        phoneItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this , 2));

        adapter = new PhoneItemAdapter(this);
    }

    @Override
    public void onCardClicked(PhoneItem phone) {

        Intent intent = new Intent(MainActivity.this , DetailedActivity.class);
        intent.putExtra("phoneItem" , phone);
        startActivity(intent);
    }

    @Override
    public void onAddToCartBtnClicked(PhoneItem phoneItem) {
        PhoneCart phoneCart = new PhoneCart();
        phoneCart.setPhoneName(phoneItem.getPhoneName());
        phoneCart.setPhoneBrandName(phoneItem.getPhoneBrandName());
        phoneCart.setPhonePrice(phoneItem.getPhonePrice());
        phoneCart.setPhoneImage(phoneItem.getPhoneImage());

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

        makeSnackBar("Item Added To Cart");
        // Update the total cart price
        double totalCartPrice = 0;
        for (PhoneCart cartItem : phoneCartList) {
            totalCartPrice += cartItem.getTotalItemPrice();
        }
    }


    private void makeSnackBar(String msg){
        Snackbar.make(coordinatorLayout , msg , Snackbar.LENGTH_SHORT)
        .setAction("Go to Cart", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , CartActivity.class));
            }
        }).show();
    }
}
