package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mostafa on 12/22/17.
 */

public class customArrayAdaptor extends ArrayAdapter<EarthquakeData> {
    public customArrayAdaptor(EarthquakeActivity earthquakeActivity, ArrayList<EarthquakeData> earthquakes) {
        super(earthquakeActivity,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView=convertView;
        if(ListItemView == null){
            ListItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        EarthquakeData earthquakeDataObject=getItem(position);

        TextView TxMag=(TextView)ListItemView.findViewById(R.id.mag);
        double magnitude=earthquakeDataObject.getMag();
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) TxMag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(magnitude);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        TxMag.setText(magnitudeFormat.format(magnitude));



        //display the place of the earthquake.
        TextView TxPlace=(TextView)ListItemView.findViewById(R.id.place1);
        TextView TxPlace2=(TextView)ListItemView.findViewById(R.id.place2);

        String placeHolder=earthquakeDataObject.getPlace();
        if(placeHolder.contains(",")) {
            String[] places = placeHolder.split(", ");
            TxPlace.setText(places[0]);
            TxPlace2.setText(places[1]);
        }else{
            TxPlace.setText("Near the");
            TxPlace2.setText(placeHolder);
        }




        //display Time in Feb 2,2016  2:40 pm
        TextView TxFullDate=(TextView)ListItemView.findViewById(R.id.fulldate);
        TextView TxDate=(TextView)ListItemView.findViewById(R.id.timedate);
        long dataInSec=earthquakeDataObject.getData();
        Date dateobject=new Date(dataInSec);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM DD,YYYY");
        SimpleDateFormat dateFormatInHours=new SimpleDateFormat("h:mm a");
        TxFullDate.setText(dateFormat.format(dateobject));
        TxDate.setText(dateFormatInHours.format(dateobject));




        return ListItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitude1Color;
        switch((int)magnitude){
            case 1:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);

        }
        return magnitude1Color;
    }


}
