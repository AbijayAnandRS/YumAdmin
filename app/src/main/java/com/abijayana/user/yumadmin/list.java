package com.abijayana.user.yumadmin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 06-09-2016.
 */
public class list extends AppCompatActivity {
    listadapter adp;ListView lv;ArrayList<food> lista;Firebase hu;Button jm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.complttelists);
        lv=(ListView)findViewById(R.id.lsview1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ORDERED");
        hu=new Firebase("https://lllsstfffuuddd.firebaseio.com/KERALAHUT/orders");
        jm=(Button)findViewById(R.id.gobttn);
        jm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class f=Class.forName("com.abijayana.user.yumadmin.MainActivity");
                    Intent i=new Intent(list.this,f);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        lista=new ArrayList<food>();
        adp=new listadapter(list.this,R.layout.lists,lista);
        lv.setAdapter(adp);

        Query er=hu.orderByKey();
        er.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    food fg=new food();
                    fg.setFnme(String.valueOf(dataSnapshot1.child("name").getValue()));
                    fg.setImurl(String.valueOf(dataSnapshot1.child("phone").getValue()));
                    fg.setlocation(String.valueOf(dataSnapshot1.child("rollno").getValue()));
                    fg.setMrp(String.valueOf(dataSnapshot1.child("item").getValue()));
                    fg.setTimestamp(dataSnapshot1.getKey());

                    lista.add(fg);



                }
                adp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Tst("ERROR");
            }
        });
        hu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               adp.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                  adp.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                 adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
               Tst("ERROR");
            }
        });

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
           final Dialog dfg=new Dialog(list.this);
            dfg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dfg.setContentView(R.layout.select_item);
            dfg.setCancelable(true);
            dfg.show();
            Button c=(Button)dfg.findViewById(R.id.button105);
            c.setText("CANCEL");
            Button d=(Button)dfg.findViewById(R.id.button205);
            d.setText("DELETE");
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dfg.cancel();
                }
            });
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hu.child(lista.get(position).getTimestamp()).removeValue(new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if(firebaseError!=null)Tst("ERROR");
                            else {
                                dfg.cancel();Tst("DELETED");
                            }
                        }
                    });
                }
            });
        }
    });


    }
    public void Tst(String cb){

        Toast.makeText(list.this,cb,Toast.LENGTH_SHORT).show();
    }

}