package com.abijayana.user.yumadmin;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;GridView gv1,gv2;foodAdapter fo1;ArrayList<food> list1,list2;Animation an;
    static View rootView1;LayoutInflater lf1;foodAdapter veg;
    static View rootView2;LayoutInflater lf2;
    static View rootView3;Button b1;
    public SectionsPagerAdapter mSectionsPagerAdapter;

    public ViewPager mViewPager;Firebase fr,gr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Yummy Admin");
        b1=(Button)findViewById(R.id.faba) ;
        fr=new Firebase("https://lllsstfffuuddd.firebaseio.com/KERALAHUT/food/veg");
        gr=new Firebase("https://lllsstfffuuddd.firebaseio.com/KERALAHUT/food/non");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog abd=new Dialog(MainActivity.this);
                abd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                abd.setContentView(R.layout.select_item);
                abd.setCancelable(true);
                abd.show();
                Button c=(Button)abd.findViewById(R.id.button105);
                Button d=(Button)abd.findViewById(R.id.button205);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        abd.cancel();create(fr);

                    }
                });
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        abd.cancel();create(gr);

                    }
                });


            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);
        lf1=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        lf2=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        rootView1 =lf1.inflate(R.layout.content_main,null);
        rootView2=lf2.inflate(R.layout.contentmain2,null);
        gv1=(GridView) rootView1.findViewById(R.id.gridView1) ;
        gv2=(GridView)rootView2.findViewById(R.id.gridView2);
        list1=new ArrayList<food>();
        list2=new ArrayList<food>();
        fo1=new foodAdapter(MainActivity.this,R.layout.griditem,list1);
        veg=new foodAdapter(MainActivity.this,R.layout.griditem,list2);
        gv2.setAdapter(veg);
        gv1.setAdapter(fo1);
        Query av=gr.orderByKey();
        Query  cv=fr.orderByKey();
        av.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    food kj=new food();
                    kj.setFnme(String.valueOf(dataSnapshot1.child("name").getValue()));
                    kj.setImurl(String.valueOf(dataSnapshot1.child("url").getValue()));
                    kj.setMrp(String.valueOf(dataSnapshot1.child("price").getValue()));
                    kj.setCount(String.valueOf(dataSnapshot1.getKey()));
                    list2.add(kj);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                error();
            }
        });
        cv.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    food jk=new food();
                    jk.setFnme(String.valueOf(dataSnapshot1.child("name").getValue()));
                    jk.setImurl(String.valueOf(dataSnapshot1.child("url").getValue()));
                    jk.setMrp(String.valueOf(dataSnapshot1.child("price").getValue()));
                    jk.setCount(String.valueOf(dataSnapshot1.getKey()));
                    list1.add(jk);
                }
                fo1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        gr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                error();
            }
        });
        fr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                error();
            }
        });
        grid(gv1,list1,fr);
        grid(gv2,list2,gr);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class abi1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView1;
        }

    }
    public static class abi2 extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView2;
        }

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    return new abi1();


                case 1:

                    return new abi2();


                default:
                    //this page does not exists
                    return null;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {

            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "VEG";
                case 1:
                    return "NON VEG";


            }
            return null;
        }

    }
    public void error(){
        Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
    }
    public void create(final Firebase gh){
        final Dialog df=new Dialog(MainActivity.this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        df.setContentView(R.layout.new_food);
        df.setCancelable(true);
        df.show();
        final EditText ed1=(EditText)df.findViewById(R.id.editText11);
        final EditText ed2=(EditText)df.findViewById(R.id.editText211);
        final EditText ed3=(EditText)df.findViewById(R.id.editText311);
        Button btn=(Button)df.findViewById(R.id.button111);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((dfgt(ed1)*dfgt(ed2)*dfgt(ed3))==1){df.cancel();post(gh,ed1,ed2,ed3);}
                    else Toast.makeText(MainActivity.this,"Fill Properly",Toast.LENGTH_SHORT).show();

            }
        });



    }
    public int dfgt(EditText ed){
        if(ed.getText().toString().isEmpty())return 0;
        else return 1;
    }
    public void post(Firebase f,EditText e1,EditText e2,EditText e3){
        String ab=String.valueOf(System.currentTimeMillis()/1000);
        HashMap<String,Object> abijay=new HashMap<String,Object>();
        abijay.put("name",e1.getText().toString());
        abijay.put("url",e2.getText().toString());
        abijay.put("price",e3.getText().toString());

        f.child(ab).updateChildren(abijay, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError!=null){error();}
                else Toast.makeText(MainActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void function(final ArrayList<food> lists, final Firebase fup, final int position){
        final Dialog abd=new Dialog(MainActivity.this);
        abd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        abd.setContentView(R.layout.select_item);
        abd.setCancelable(true);
        abd.show();
        Button c=(Button)abd.findViewById(R.id.button105);
        c.setText("EDIT");
        Button d=(Button)abd.findViewById(R.id.button205);
        d.setText("DELETE");
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abd.cancel();create1(fup,lists,position);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abd.cancel();
              fup.child(lists.get(position).getCount()).removeValue(new Firebase.CompletionListener() {
                  @Override
                  public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                      if(firebaseError!=null){error();}
                      else Toast.makeText(MainActivity.this,"DELETED",Toast.LENGTH_SHORT).show();


                  }
              });

            }
        });




    }
    public void create1(final Firebase gh,final ArrayList<food> lists,int position){
       final String timestamp=lists.get(position).getCount();
        final Dialog df=new Dialog(MainActivity.this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        df.setContentView(R.layout.new_food);
        df.setCancelable(true);
        df.show();
        final EditText ed1=(EditText)df.findViewById(R.id.editText11);
        ed1.setText(lists.get(position).getFnme());
        final EditText ed2=(EditText)df.findViewById(R.id.editText211);
        ed2.setText(lists.get(position).getImurl());
        final EditText ed3=(EditText)df.findViewById(R.id.editText311);
        ed3.setText(lists.get(position).getMrp());
        Button btn=(Button)df.findViewById(R.id.button111);
        btn.setText("UPDATE");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((dfgt(ed1)*dfgt(ed2)*dfgt(ed3))==1){df.cancel();post1(gh,ed1,ed2,ed3,timestamp);}
                else Toast.makeText(MainActivity.this,"Fill Properly",Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void post1(Firebase f,EditText e1,EditText e2,EditText e3,String time){
        String ab=time;
        HashMap<String,Object> abijay=new HashMap<String,Object>();
        abijay.put("name",e1.getText().toString());
        abijay.put("url",e2.getText().toString());
        abijay.put("price",e3.getText().toString());

        f.child(ab).updateChildren(abijay, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError!=null){error();}
                else Toast.makeText(MainActivity.this,"EDITED",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void grid(GridView gv, final ArrayList<food> lists,final Firebase fg){

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                function(lists,fg,position);
            }
        });



    }

}
