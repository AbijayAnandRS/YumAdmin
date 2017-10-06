package com.abijayana.user.yumadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 05-09-2016.
 */
public class foodAdapter extends ArrayAdapter<food> {int count;
    Context context;
    int resource;
    ArrayList<food> objects;
    public foodAdapter(Context context, int resource, ArrayList<food> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoldera vw=new ViewHoldera();
        if(convertView==null){
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw.iv=(ImageView)convertView.findViewById(R.id.imageView2);
            vw.tv1=(TextView)convertView.findViewById(R.id.textView2);
            vw.tv2=(TextView)convertView.findViewById(R.id.textView3);

            convertView.setTag(vw);



        }
        else  vw=(ViewHoldera)convertView.getTag();




        Picasso.with(getContext()).load(objects.get(position).getImurl()).into(vw.iv);
        vw.tv1.setText(objects.get(position).getFnme());
        vw.tv2.setText(objects.get(position).getMrp());
        Animation an= AnimationUtils.loadAnimation(getContext(),R.anim.upgo);
        convertView.startAnimation(an);

        return convertView;
    }

    public class ViewHoldera{
        ImageView iv;
        TextView tv1;
        TextView tv2;


    }

}

