package com.techeducation.tech_reveal;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

public class QuizMain extends AppCompatActivity {
    List<QuesBean> quesList;
    int score = 0;
    int qNo,firstQuesNo;
    QuesBean question;
    TextView txtQuestion, times;
    Button opt1, opt2, opt3;
    CounterClass timer;

    private void setQuesOptions() {
        if(qNo!=firstQuesNo){
            timer.cancel();
        }
        timer = new CounterClass(30000, 1000);
        timer.start();

        txtQuestion.setText(question.getQues());
        opt1.setText(question.getOpt1());
        opt2.setText(question.getOpt2());
        opt3.setText(question.getOpt3());

        if(qNo==29){
            qNo=0;
        }
        else{
            qNo++;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QuizSetQues ob = new QuizSetQues();
        quesList = ob.getAllQuesBeans();

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        opt1 = (Button) findViewById(R.id.opt1);
        opt2 = (Button) findViewById(R.id.opt2);
        opt3 = (Button) findViewById(R.id.opt3);
        times = (TextView) findViewById(R.id.timers);

        Random r = new Random();
        int Low = 0;
        int High = 30;
        qNo = r.nextInt((High-Low)+1) + Low;
        firstQuesNo=qNo;
        question = quesList.get(qNo);
        setQuesOptions();

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(opt1.getText().toString());
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(opt2.getText().toString());
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(opt3.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    public void getAnswer(String AnswerString) {
        if (question.getAnswer().equals(AnswerString)) {
            score++;
        } else {
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("keyScore",score);
            intent.putExtra("keyText","You made a wrong choice");
            startActivity(intent);
            finish();
        }

        if (qNo!=firstQuesNo) {
            question = quesList.get(qNo);
            setQuesOptions();
        } else {
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("keyScore", score);
            intent.putExtra("keyText", "Thanks for playing !");
            startActivity(intent);
            finish();
        }
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(QuizMain.this, ResultActivity.class);
            intent.putExtra("keyScore",score);
            intent.putExtra("keyText","Time Over !");
            startActivity(intent);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            times.setText(""+millisUntilFinished/1000);
        }
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
