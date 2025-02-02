package com.example.phoneapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.phoneapp.dao.CartDAO;
import com.example.phoneapp.database.CartDatabase;
import com.example.phoneapp.utils.model.PhoneCart;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDAO cartDAO;
    private LiveData<List<PhoneCart>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<PhoneCart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }
    public CartRepo(Application application){
        cartDAO = CartDatabase.getInstance(application).cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();
    }

    public void insertCartItem(PhoneCart phoneCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.insertCartItem(phoneCart);
            }
        });
    }

    public  void deleteCartItem(PhoneCart phoneCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteCartItem(phoneCart);
            }
        });
    }

    public void updateQuantity(int id , int quantity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updateQuantity(id , quantity);
            }
        });
    }

    public void updatePrice(int id , double price){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updatePrice(id , price);
            }
        });
    }

    public void deleteAllCartItems(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteAllItems();
            }
        });
    }
}
