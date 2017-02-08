package com.techeducation.tech_reveal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textResult = (TextView) findViewById(R.id.textResult);

       Intent i=getIntent();
        int score=i.getIntExtra("keyScore", 0);
        String msg=i.getStringExtra("keyText");
        if(score!=30)
             textResult.setText(msg + "\nYour score is " + " " + score + "/30");
        else
            textResult.setText("Congratulations!\n"+"Your score is " + " " + score + "/30");

    }

    public void playagain(View o) {
        Intent intent = new Intent(this, QuizMain.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home: {
                finish();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
