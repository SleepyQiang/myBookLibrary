package com.jnu.booklibrary.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;

import androidx.annotation.NonNull;

public class DataSaver {

    public void Save(Context context, ArrayList<Book> data) {

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("myData.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public ArrayList<Book> Load(Context context) {

        ArrayList<Book> data = new ArrayList<>();

        try {
            FileInputStream fileInputStream = context.openFileInput("myData.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            data = (ArrayList<Book>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}

