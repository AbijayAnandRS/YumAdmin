package com.abijayana.user.yumadmin;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07-09-2016.
 */
public class listadapter extends ArrayAdapter<food>{
    Context context;
    ArrayList<food> objects;
    int resource;

    public listadapter(Context context, int resource, ArrayList<food> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.resource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHlder vw=new ViewHlder();
        if(convertView==null)
        {
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw.tnme=(TextView)convertView.findViewById(R.id.textView402);
            vw.tno=(TextView)convertView.findViewById(R.id.textView444);
            vw.locn=(TextView)convertView.findViewById(R.id.textView544);
            vw.tim1=(TextView)convertView.findViewById(R.id.textView844);
            vw.tim2=(TextView)convertView.findViewById(R.id.textView944);
            vw.itms=(TextView)convertView.findViewById(R.id.textView744);
            convertView.setTag(vw);






        }
        else vw=(ViewHlder)convertView.getTag();
        vw.tnme.setText(objects.get(position).getFnme());
        vw.tno.setText(objects.get(position).getImurl());
        vw.locn.setText(objects.get(position).getlocation());
        vw.itms.setText(objects.get(position).getMrp());
        long ad=Long.parseLong(objects.get(position).getTimestamp())*1000L;

        String time = DateUtils.formatDateTime(getContext(),ad, DateUtils.FORMAT_SHOW_TIME);
        String date =  DateUtils.formatDateTime(getContext(),ad, DateUtils.FORMAT_SHOW_DATE);
        vw.tim1.setText(date);
        vw.tim2.setText(time);



        return convertView;
    }

    public class ViewHlder{
        TextView tnme;
        TextView tno;
        TextView locn;
        TextView tim1;
        TextView tim2;
        TextView itms;

    }
}
