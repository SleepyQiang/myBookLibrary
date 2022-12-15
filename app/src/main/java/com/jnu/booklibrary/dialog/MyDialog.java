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

    public MyDialog(@NonNull Context context, Book book) {

        super(context, R.style.myDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.book_info, null);

        TextView textTitle_d = view.findViewById(R.id.textTitle_d);
        TextView textAuthor_d = view.findViewById(R.id.textAuthor_d);
        TextView textPress_d = view.findViewById(R.id.textPress_d);
        TextView textYear_d = view.findViewById(R.id.textYear_d);
        TextView textIsbn_d = view.findViewById(R.id.textIsbn_d);

        textTitle_d.setText(context.getString(R.string.book_name_display) + book.getTitle());
        textAuthor_d.setText(context.getString(R.string.book_author_display) + book.getAuthor());
        textPress_d.setText(context.getString(R.string.book_press_display) + book.getPress());
        textYear_d.setText(context.getString(R.string.book_year_display) + book.getYear());
        textIsbn_d.setText(context.getString(R.string.book_isbn_display) + book.getIsbn());

        setContentView(view);
    }
}
