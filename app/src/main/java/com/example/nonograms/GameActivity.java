package com.example.nonograms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    LinearLayout lLayout;
    RadioButton rb_black;
    RadioButton rb_white;
    String picture;
    TextView name;
    Game g;
    Button b_back;
    Button b_restart;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //tableLayout = findViewById(R.id.tLayout);
        lLayout = findViewById(R.id.lLayout);
        rb_black = findViewById(R.id.rb_black);
        rb_white = findViewById(R.id.rb_white);
        b_back = findViewById(R.id.back);
        b_restart = findViewById(R.id.restart);
        name = findViewById(R.id.namePicture);
        g = new Game(this, null);
        getIntentItem();
        lLayout.addView(g);

        g.setField(picture);
        rb_black.setOnClickListener(radioButtonClickListener);
        rb_white.setOnClickListener(radioButtonClickListener);
    }

    //Получаем данные из предыдущего активити
    private void getIntentItem()
    {
        Intent i = getIntent();
        //Проверяем, что данные передались
        if (i != null)
        {
            name.setText(i.getStringExtra("item_name"));
            picture = i.getStringExtra("item_picture");
        }
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.rb_black: g.setRB(1);
                    break;
                case R.id.rb_white: g.setRB(0);
                    break;
                default:
                    break;
            }
        }
    };

    public void onBack (View view) {
        Intent i = new Intent(this, ItemActivity.class);
        startActivity(i);
    }

    public void onRestart (View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
