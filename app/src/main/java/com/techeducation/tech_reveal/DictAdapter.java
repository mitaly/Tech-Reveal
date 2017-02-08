package com.techeducation.tech_reveal;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DictAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    ArrayList<String> list;
    ArrayList<String> tempList;
    int flag;

    public DictAdapter(Context context, int resource, ArrayList<String> objects,int flag) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.flag=flag;
        list=objects;

        tempList=new ArrayList<>();
        tempList.addAll(list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        TextView textView=null;

        view= LayoutInflater.from(context).inflate(resource,parent,false);

        if(flag==0){
             textView=(TextView)view.findViewById(R.id.textSubject);
            Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/JLSDataGothicR_NC.ttf");
            textView.setTypeface(typeface);
        }
        else if(flag==1){
            textView=(TextView)view.findViewById(R.id.textWord);

        }
        if(flag==2||flag==3){
            textView=(TextView)view.findViewById(R.id.textViewRecFav);
            ImageView btnDelete=(ImageView)view.findViewById(R.id.deleteSingle);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag==2){
                        ContentResolver resolver=context.getContentResolver();
                        resolver.delete(DictionaryUtil.uri_recent,DictionaryUtil.COL_WORDS+"="+"'"+list.get(position)+"'",null);
                        list.remove(position);
                        notifyDataSetChanged();
                    }else if(flag==3){
                        ContentResolver resolver=context.getContentResolver();
                        resolver.delete(DictionaryUtil.uri_favourites,DictionaryUtil.COL_WORDS+"="+"'"+list.get(position)+"'",null);
                        list.remove(position);
                        notifyDataSetChanged();
                    }

                }
            });
        }
        textView.setText(list.get(position));
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/Prime Regular.ttf");
        textView.setTypeface(typeface);
        return  view;
    }

    void filterChoices(String str){
        list.clear();

        if(str.isEmpty()){
            list.addAll(tempList);
        }else{
            for(String ob:tempList){
                if(ob.toLowerCase().startsWith(str.toLowerCase())){
                    list.add(ob);
                }
            }
        }

        notifyDataSetChanged();
    }
}


