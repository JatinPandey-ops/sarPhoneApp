package com.example.phoneapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phoneapp.utils.model.PhoneCart;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(PhoneCart phoneCart);

    @Query("SELECT * FROM phone_table")
    LiveData<List<PhoneCart>> getAllCartItems();

    @Delete
    void deleteCartItem(PhoneCart phoneCart);

    @Query("UPDATE phone_table SET quantity=:quantity WHERE id=:id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE phone_table SET totalItemPrice=:totalItemPrice WHERE id=:id")
    void updatePrice(int id, double totalItemPrice);

    @Query("DELETE FROM phone_table")
    void deleteAllItems();
}
