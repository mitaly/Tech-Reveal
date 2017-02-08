package com.techeducation.tech_reveal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class WordListActivity extends AppCompatActivity implements RecognitionListener {
    Uri uriTable;
    ContentResolver resolver;
    ArrayList<String> listOfWords;
    public  static ArrayList<String> tempList;
    DictAdapter adapter;
    ListView listView;
    EditText eTxtSearch;
    Button btnClear;
    String subject;

    ProgressDialog progressDialog;
    SpeechRecognizer speechRecognizer;

    void initViews(){
        resolver=getContentResolver();
        listOfWords = new ArrayList<>();

        listView = (ListView)findViewById(R.id.listViewWords);
        eTxtSearch=(EditText)findViewById(R.id.eTxtSearch);
        btnClear=(Button)findViewById(R.id.btnClear);

        Intent rcvIntent=getIntent();
        int position=rcvIntent.getIntExtra("keyPosition",0);
        subject = SubjectsActivity.listSubject.get(position);

        setTitle(subject);

        switch(position){
            case 0:
                uriTable=DictionaryUtil.uri_ca;
                break;
            case 1:
                uriTable=DictionaryUtil.uri_cg;
                break;
            case 2:
                uriTable=DictionaryUtil.uri_cn;
                break;
            case 3:
                uriTable=DictionaryUtil.uri_cs;
                break;
            case 4:
                uriTable=DictionaryUtil.uri_dbms;
                break;
            case 5:
                uriTable=DictionaryUtil.uri_dcld;
                break;
            case 6:
                uriTable=DictionaryUtil.uri_dsa;
                break;
            case 7:
                uriTable=DictionaryUtil.uri_map;
                break;
            case 8:
                uriTable=DictionaryUtil.uri_os;
                break;
            case 9:
                uriTable=DictionaryUtil.uri_pl;
                break;
            case 10:
                uriTable=DictionaryUtil.uri_wt;
                break;
        }

        initWordList();
        tempList=new ArrayList<>(listOfWords);

        adapter = new DictAdapter(this, R.layout.list_item_words,listOfWords,1);
        listView.setAdapter(adapter);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Listening...");
        progressDialog.setCancelable(false);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtSearch.setText("");

            }
        });

        ///Performs search on click of Enter key
        eTxtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ///hide keypad when user presses Enter key
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(eTxtSearch.getWindowToken(),0);
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        eTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!eTxtSearch.getText().toString().equals("")) { //if edittext include text
                    btnClear.setVisibility(View.VISIBLE);
                } else { //not include text
                    btnClear.setVisibility(View.GONE);
                }
                adapter.filterChoices(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(WordListActivity.this,WordMeaningActivity.class);
                String word=listOfWords.get(position);
                intent.putExtra("keyWord",word);
                intent.putExtra("keyUri",String.valueOf(uriTable));
                startActivity(intent);
            }
        });
    }

    //When User presses Enter key,then start searching the string in arrayList
    void performSearch(){
        boolean found=false;
        String textEntered=eTxtSearch.getText().toString().trim();
        for(String str:tempList){
            if(str.equalsIgnoreCase(textEntered)){
                Intent i=new Intent(WordListActivity.this,WordMeaningActivity.class);
                i.putExtra("keyWord",str);
                i.putExtra("keyUri",String.valueOf(uriTable));
                found=true;
                startActivity(i);
                break;
            }
        }
        if(found==false){
            Toast.makeText(this, "Word not found", Toast.LENGTH_SHORT).show();
        }
    }

    void initWordList(){
        String[] projection = {DictionaryUtil.COL_ID, DictionaryUtil.COL_WORDS};
        Cursor cursor = resolver.query(uriTable, projection, null, null, null);
        while (cursor.moveToNext()) {
            listOfWords.add(cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_WORDS)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_random,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            {
                finish();
                break;
            }
            case R.id.randomId:
            {
                Random random=new Random();
                int randomNumber=random.nextInt(listOfWords.size());
                Intent i=new Intent(WordListActivity.this,WordMeaningActivity.class);
                i.putExtra("keyWord",listOfWords.get(randomNumber));
                i.putExtra("keyUri",String.valueOf(uriTable));
                startActivity(i);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSpeak);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    speechRecognizer.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
                }
                else{

                    AlertDialog.Builder builder=new AlertDialog.Builder(WordListActivity.this);
                    builder.setTitle("Internet Connection");
                    builder.setMessage("You don't have internet connection.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(WordListActivity.this,"Please enable Internet Connection",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }

            }
        });

        initViews();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
        progressDialog.show();
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        progressDialog.dismiss();
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        boolean found=false;

            outer:  for(String str:tempList){

            for(String s:voiceResults){

                Log.i("aWord",str);
                if(s.toLowerCase().equalsIgnoreCase(str.toLowerCase())){
                    found=true;
                    Intent i=new Intent(WordListActivity.this,WordMeaningActivity.class);
                    i.putExtra("keyWord",str);
                    i.putExtra("keyUri",String.valueOf(uriTable));
                    startActivity(i);
                    break outer;
                }
            }
        }
        if(found==false){
            Toast.makeText(WordListActivity.this,"Word not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
