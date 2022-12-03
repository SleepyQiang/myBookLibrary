package com.jnu.booklibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jnu.booklibrary.R;
import com.jnu.booklibrary.data.Book;

public class MyDialog extends Dialog {
    private TextView textTitle_d;
    private TextView textAuthor_d;
    private TextView textPress_d;
    private TextView textYear_d;
    private TextView textIsbn_d;

    public MyDialog(@NonNull Context context, Book book) {
        super(context, R.style.myDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.book_info, null);

        textTitle_d = view.findViewById(R.id.textTitle_d);
        textAuthor_d = view.findViewById(R.id.textAuthor_d);
        textPress_d = view.findViewById(R.id.textPress_d);
        textYear_d = view.findViewById(R.id.textYear_d);
        textIsbn_d = view.findViewById(R.id.textIsbn_d);
        textTitle_d.setText("Title: " + book.getTitle());
        textAuthor_d.setText("Author: " + book.getAuthor());
        textPress_d.setText("Press: " + book.getPress());
        textYear_d.setText("Year: " + book.getYear());
        textIsbn_d.setText("ISBN: " + book.getIsbn());

        setContentView(view);
    }


}
