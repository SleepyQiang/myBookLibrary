package com.jnu.booklibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InputBookListActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 1;
    private int position;
    private int condition;
    private CardView cardViewImageUrl;
    private ImageView imageTop;
    private EditText editTitle;
    private EditText editAuthor;
    private EditText editRank;
    private EditText editYear;
    private EditText editPress;
    private EditText editIsbn;
    private Bitmap myBmp;
    private byte[] coverByte;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_list);

        setCondition(0);
        imageTop = findViewById(R.id.imageTop);
        byte[] data = this.getIntent().getByteArrayExtra("cover");

        if (data == null) {
            imageTop.setImageDrawable(getDrawable(R.drawable.wa));
        } else {
            imageTop.setImageBitmap(this.BytesToBitmap(data));
        }
        /*int id = this.getIntent().getIntExtra("cover", R.drawable.machinelearning);*/

        EditText editImageUrl = findViewById(R.id.edittextImageUrl);
        cardViewImageUrl = findViewById(R.id.cardViewImageUrl);

        cardViewImageUrl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                url = editImageUrl.getText().toString();

                if (url.length() != 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setCondition(0);
                            myBmp = getURLImage(url);
                            coverByte = BitmapToBytes(myBmp);
                            Message msg = new Message();

                            msg.what = 0;
                            msg.obj = myBmp;

                            System.out.println("000");
                            handle.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });

        editTitle = findViewById(R.id.edittextTitle);
        position = this.getIntent().getIntExtra("position", 0);
        String title = this.getIntent().getStringExtra("title");
        editTitle.setText(title);

        editAuthor = findViewById(R.id.edittextAuthor);
        String author = this.getIntent().getStringExtra("author");
        editAuthor.setText(author);

        editRank = findViewById(R.id.edittextRank);
        String rank = this.getIntent().getStringExtra("rank");
        editRank.setText(rank);

        editYear = findViewById(R.id.edittextYear);
        String year = this.getIntent().getStringExtra("year");
        editYear.setText(year);

        editPress = findViewById(R.id.edittextPress);
        String press = this.getIntent().getStringExtra("press");
        editPress.setText(press);

        editIsbn = findViewById(R.id.edittextIsbn);
        String isbn = this.getIntent().getStringExtra("isbn");
        editIsbn.setText(isbn);

        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putInt("position", position);
                bundle.putByteArray("cover", coverByte);
                bundle.putString("title", editTitle.getText().toString());
                bundle.putString("author", editAuthor.getText().toString());
                bundle.putString("rank", editRank.getText().toString());
                bundle.putString("year", editYear.getText().toString());
                bundle.putString("press", editPress.getText().toString());
                bundle.putString("isbn", editIsbn.getText().toString());

                intent.putExtras(bundle);
                setResult(RESULT_CODE, intent);
                InputBookListActivity.this.finish();
            }
        });
    }

    public int getCondition() {

        return condition;
    }

    public void setCondition(int condition) {

        this.condition = condition;
    }

    public byte[] BitmapToBytes(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos.toByteArray();
    }

    public Bitmap BytesToBitmap(@NonNull byte[] b) {

        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    private Handler handle = new Handler(Looper.getMainLooper()) {

        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                System.out.println("111");

                Bitmap bmp = (Bitmap) msg.obj;
                System.out.println("000");

                imageTop.setImageBitmap(bmp);
                setCondition(1);
            }
        }
    };

    public Bitmap getURLImage(String url) {

        Bitmap bmp = null;

        try {
            URL myUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();

            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }
}