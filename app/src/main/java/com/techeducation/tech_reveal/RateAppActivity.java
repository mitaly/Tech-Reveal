package com.techeducation.tech_reveal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateAppActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button btnSubmit;
    TextView txtView;
    EditText etxtFeedback;

    void initViews(){
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        txtView=(TextView)findViewById(R.id.txtViewAck);
        etxtFeedback = (EditText)findViewById(R.id.editTextFeedback);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating == 0){
                    txtView.setText("");
                    btnSubmit.setVisibility(View.GONE);
                } else {
                    if (rating > 0 && rating <= 1) {
                        txtView.setText("Hated it ;(");
                    } else if (rating <= 2) {
                        txtView.setText("Disliked it :(");
                    } else if (rating <= 3) {
                        txtView.setText("It's OK :|");
                    } else if (rating <= 4) {
                        txtView.setText("Liked it :)");
                    } else if (rating <= 5) {
                        txtView.setText("Loved it <3");
                    }

                    btnSubmit.setVisibility(View.VISIBLE);

                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RateAppActivity.this, "Thanks for the feedback!", Toast.LENGTH_SHORT).show();
                etxtFeedback.setText("");
                btnSubmit.setVisibility(View.GONE);
                ratingBar.setRating(0);
            }
        });
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_app);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }
}
