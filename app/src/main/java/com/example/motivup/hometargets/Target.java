package com.example.motivup.hometargets;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.motivup.R;
import com.example.motivup.database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Target {
    private String productName;
    private String productDescription;
    private int imageID;

    private String target_id;

    private String lastDate;

    public Target() {
    }

    public Target(int imageID, String productName, String productDescription, String target_id) {
        this.imageID = imageID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.target_id = target_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getLastDate() {
        return lastDate;
    }
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @SuppressLint("Range")
    public static ArrayList<Target> getData(DbHelper dbHelper, String type) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        ArrayList<Target> productList = new ArrayList<>();
        SQLiteDatabase dbx = dbHelper.getWritableDatabase();

        Cursor cursor = dbx.rawQuery("SELECT * FROM targets",null);

        if(type.equals("Daily")){
            cursor = dbx.rawQuery("SELECT * FROM targets WHERE target_type = 'Daily'",null);
        } else if (type.equals("Weekly")) {
            cursor = dbx.rawQuery("SELECT * FROM targets WHERE target_type = 'Weekly'",null);
        }



        while (cursor.moveToNext()) {
            Target target = new Target();
            if(cursor.getString(cursor.getColumnIndex("last_date")).equals(formattedDate)) {
                target.setImageID(R.drawable.ic_baseline_check_checked);
            } else {
                target.setImageID(R.drawable.ic_baseline_check_24);
            }
            target.setTarget_id(cursor.getString(cursor.getColumnIndex("target_id")));
            target.setProductName(cursor.getString(cursor.getColumnIndex("target_name")));
            target.setProductDescription(cursor.getString(cursor.getColumnIndex("target_type")));
            target.setLastDate(cursor.getString(cursor.getColumnIndex("last_date")));

            productList.add(target);
        }
        return productList;
    }
}
