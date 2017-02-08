package com.techeducation.tech_reveal;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FavouritesActivity extends AppCompatActivity {
    ListView listView;
    ContentResolver resolver;
    ArrayList<String> wordsFav;
    DictAdapter adapter;
    String[] projectionFav;

    void initViews() {
        listView = (ListView) findViewById(R.id.listViewFav);
        resolver = getContentResolver();
        projectionFav = new String[]{DictionaryUtil.COL_ID, DictionaryUtil.COL_WORDS,DictionaryUtil.COL_URI};

        setTitle("Favourites");

        queryFavourites();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String wordToDisplay=wordsFav.get(position);
                String uri=null;
                Cursor cursor=resolver.query(DictionaryUtil.uri_favourites,projectionFav,null,null,null);
                while(cursor.moveToNext()){
                    if(cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_WORDS)).equals(wordToDisplay)){
                        uri=cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_URI));
                        break;
                    }
                }
                cursor.close();

                Intent i = new Intent(FavouritesActivity.this, WordMeaningActivity.class);
                i.putExtra("keyWord", wordToDisplay);
                i.putExtra("keyUri",uri);
                startActivity(i);
            }
        });
    }

    void queryFavourites(){
        Cursor cursorFav=resolver.query(DictionaryUtil.uri_favourites,projectionFav,null,null,null);

        wordsFav=new ArrayList<>();

        while(cursorFav.moveToNext()){
            wordsFav.add(cursorFav.getString(cursorFav.getColumnIndex(DictionaryUtil.COL_WORDS)));
        }
        cursorFav.close();
        adapter=new DictAdapter(this,R.layout.list_item_rec_fav,wordsFav,3);
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
            case R.id.deleteAll:
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete all favourites?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resolver.delete(DictionaryUtil.uri_favourites, null, null);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                break;
            }
            case android.R.id.home:
            {
                finish();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }
}
