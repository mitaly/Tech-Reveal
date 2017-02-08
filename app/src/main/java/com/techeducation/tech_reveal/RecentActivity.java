package com.techeducation.tech_reveal;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class RecentActivity extends AppCompatActivity {

    ListView listView;
    ContentResolver resolver;
    ArrayList<String> wordsRecent;
    DictAdapter adapter;
    ContentValues values;
    String[] projectionRec={DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS,DictionaryUtil.COL_URI};

    void initViews(){
        listView=(ListView)findViewById(R.id.listViewRec);
        resolver=getContentResolver();
        values=new ContentValues();

        setTitle("Recent Words");
        queryRecentWords();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String wordToDisplay=wordsRecent.get(position);
                String uri=null;
                Cursor cursor=resolver.query(DictionaryUtil.uri_recent,projectionRec,null,null,null);
                while (cursor.moveToNext()){
                    if(cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_WORDS)).equals(wordToDisplay)){
                        uri=cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_URI));
                        break;
                    }
                }
                cursor.close();

                ///Delete the word which is searched by user
                resolver.delete(DictionaryUtil.uri_recent, DictionaryUtil.COL_WORDS + "='" + wordToDisplay + "'", null);

                Intent i = new Intent(RecentActivity.this, WordMeaningActivity.class);
                i.putExtra("keyWord",wordToDisplay);
                i.putExtra("keyUri",uri);
                startActivity(i);
            }
        });

    }

    void queryRecentWords(){
        Cursor cursorRec=resolver.query(DictionaryUtil.uri_recent,projectionRec,null,null,null);

        wordsRecent=new ArrayList<>();
        while(cursorRec.moveToNext()){
            wordsRecent.add(cursorRec.getString(cursorRec.getColumnIndex(DictionaryUtil.COL_WORDS)));
        }
        cursorRec.close();
        adapter=new DictAdapter(this,R.layout.list_item_rec_fav,wordsRecent,2);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rec_fav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteAll: {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete all navigation history?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resolver.delete(DictionaryUtil.uri_recent, null, null);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                break;
            }
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
        setContentView(R.layout.activity_recent);
        ///to display back button   <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }
}
