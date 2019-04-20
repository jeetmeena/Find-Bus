package com.example.jeetmeena.findbus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class CelendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid,grid2,grid3;
   // private GridCellAdapter adapter;
    private Calendar _calendar;
    private int month, year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celendar);

        int[] rainbow = new int[] {
                R.color.summer,
                R.color.fall,
                R.color.winter,
                R.color.spring
        };
        int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
       // int month = currentDate.get(Calendar.MONTH);
      //  int season = monthSeason[month];
       // int color = rainbow[season];
        Date datev = null;
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,2);
        calendarView=findViewById(R.id.calendarView);
        txtDate=findViewById(R.id.calendar_date_display);
        calendar.set(Calendar.DATE, 10);
       // calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
       // calendar.set(Calendar.HOUR_OF_DAY, 23);//not sure this is needed
        long endOfMonth = calendar.getTimeInMillis();

        calendar = Calendar.getInstance();
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MMM-yyyy");
        SimpleDateFormat d3 = new SimpleDateFormat("dd-MM-yyyy");
        txtDate.setText(df2.format(c));
        SimpleDateFormat dff = new SimpleDateFormat("dd");
        String curentDay=dff.format(c);
        calendar.set(Calendar.DATE, Integer.parseInt(curentDay));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Toast.makeText(CelendarActivity.this, "" +curentDay,
                Toast.LENGTH_SHORT).show();
        long startOfMonth = calendar.getTimeInMillis();
             calendarView.setMinDate(startOfMonth);
             calendarView.setMaxDate(endOfMonth);

             //calendarView.setDate();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date =""+dayOfMonth+"-"+month+"-"+year;
                Intent intent =new Intent(CelendarActivity.this,MainActivity.class);
               intent.putExtra("date",date);
                startActivity(intent);
                finish();

                Toast.makeText(CelendarActivity.this, "" + date,
                        Toast.LENGTH_SHORT).show();
            }
        });





        /*grid.setAdapter(new ImageAdapter(this));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CelendarActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
 */



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,

        };


        public  int getPixelsFromDPs(Activity activity, int dps){
            Resources r = activity.getResources();
            int  px = (int) (TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
            return px;
        }
    }
    public class ImageAdapters extends BaseAdapter {
        private Context mContext;

        public ImageAdapters(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,


        };


        public  int getPixelsFromDPs(Activity activity, int dps){
            Resources r = activity.getResources();
            int  px = (int) (TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
            return px;
        }
    }
    public class ImageAdapterss extends BaseAdapter {
        private Context mContext;

        public ImageAdapterss(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
                R.drawable.bus,R.drawable.bus,
        };


        public  int getPixelsFromDPs(Activity activity, int dps){
            Resources r = activity.getResources();
            int  px = (int) (TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
            return px;
        }
    }
}
