package com.jnu.mybookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookListActivity extends AppCompatActivity {
    public static final int RESULT_CODE = 1;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_list);

        EditText editBookName = findViewById(R.id.edittext_book_name);

        position=this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("name");

        if(null!=title){
            editBookName.setText(title);
        }

        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("name",editBookName.getText().toString());
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                setResult(RESULT_CODE, intent);
                InputBookListActivity.this.finish();
            }
        });


    }
}