package com.example.maroy.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MaRoy on 4/18/2015.
 */
public class AppData implements Parcelable{

    private String name;
    private String imagee;
    private String type;
    private double price;
    private double rating;
    private int users;
    private String last_update;
    private String description;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagee() {
        return imagee;
    }

    public void setImagee(String imagee) {
        this.imagee = imagee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imagee);
        parcel.writeString(type);
        parcel.writeDouble(price);
        parcel.writeDouble(rating);
        parcel.writeInt(users);
        parcel.writeString(last_update);
        parcel.writeString(description);
        parcel.writeString(url);


    }

    public static final Parcelable.Creator<AppData> CREATOR = new Parcelable.Creator<AppData>() {
        public AppData createFromParcel(Parcel in) {
            AppData data = new AppData();
            data.name = in.readString();
            data.imagee = in.readString();
            data.type = in.readString();
            data.price = in.readDouble();
            data.rating = in.readDouble();
            data.users = in.readInt();
            data.last_update = in.readString();
            data.description = in.readString();
            data.url = in.readString();
            return data;
        }

        @Override
        public AppData[] newArray(int size) {
            return new AppData[size];
        }
    };
}
