package com.example.phoneapp.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PhoneItem implements Parcelable {

    private String phoneName, phoneBrandName;
    private int phoneImage;
    private double phonePrice;

    public PhoneItem(String phoneName, String phoneBrandName, int phoneImage, double phonePrice) {
        this.phoneName = phoneName;
        this.phoneBrandName = phoneBrandName;
        this.phoneImage = phoneImage;
        this.phonePrice = phonePrice;
    }

    protected PhoneItem(Parcel in) {
        phoneName = in.readString();
        phoneBrandName = in.readString();
        phoneImage = in.readInt();
        phonePrice = in.readDouble();
    }

    public static final Creator<PhoneItem> CREATOR = new Creator<PhoneItem>() {
        @Override
        public PhoneItem createFromParcel(Parcel in) {
            return new PhoneItem(in);
        }

        @Override
        public PhoneItem[] newArray(int size) {
            return new PhoneItem[size];
        }
    };

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneBrandName() {
        return phoneBrandName;
    }

    public void setPhoneBrandName(String phoneBrandName) {
        this.phoneBrandName = phoneBrandName;
    }

    public int getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(int phoneImage) {
        this.phoneImage = phoneImage;
    }

    public double getPhonePrice() {
        return phonePrice;
    }

    public void setPhonePrice(double phonePrice) {
        this.phonePrice = phonePrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(phoneName);
        parcel.writeString(phoneBrandName);
        parcel.writeInt(phoneImage);
        parcel.writeDouble(phonePrice);
    }
}
