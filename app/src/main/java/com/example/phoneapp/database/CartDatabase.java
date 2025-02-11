package com.example.phoneapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.phoneapp.dao.CartDAO;
import com.example.phoneapp.utils.model.PhoneCart;

@Database(entities = {PhoneCart.class} , version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();

    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , CartDatabase.class, "PhoneDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}


