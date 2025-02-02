package com.example.phoneapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.phoneapp.repository.CartRepo;
import com.example.phoneapp.utils.model.PhoneCart;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public LiveData<List<PhoneCart>> getAllCartItems(){
        return cartRepo.getAllCartItemsLiveData();
    }

    public void insertCartItem(PhoneCart phoneCart){
        cartRepo.insertCartItem(phoneCart);
    }

    public void updateQuantity(int id, int quantity){
        cartRepo.updateQuantity(id , quantity);
    }

    public void updatePrice(int id, double price){
        cartRepo.updatePrice(id, price);
    }

    public void deleteCartItem(PhoneCart phoneCart){
        cartRepo.deleteCartItem(phoneCart);
    }

    public void deleteAllCartItems(){
        cartRepo.deleteAllCartItems();
    }
}
