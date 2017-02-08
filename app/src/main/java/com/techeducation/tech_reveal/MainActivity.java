package com.techeducation.tech_reveal;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnDict, btnQuiz, btnHang;
    TextView categories;

    void initViews() {
        btnDict = (Button) findViewById(R.id.dictionary);
        btnQuiz = (Button) findViewById(R.id.quiz);
        btnHang = (Button) findViewById(R.id.hangman);
        categories=(TextView)findViewById(R.id.categories);

        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/SEASRN__.ttf");
        categories.setTypeface(typeface);

        btnHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuizMain.class);
                startActivity(i);
            }
        });
        btnDict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SubjectsActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About Us");
                builder.setMessage("App Developers: \n\n Gurkirat Kaur  gurkiratbajaj16@gmail.com \n\n Jaspreet Kaur  kaurjaspreet1996@gmail.com \n\n Mitaly Sen        senmitaly13@gmail.com");
                builder.setPositiveButton("OK",null);
                builder.create().show();
                break;
            case R.id.help:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Help");
                builder1.setMessage("Choose any one of the three: \n\n1. Dictionary: Get the meaning of     technical words\n\n2. Quiz: Play a techno-fun Quiz\n\n3. Hangman: Play techno-word game");
                builder1.setPositiveButton("OK", null);
                builder1.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
