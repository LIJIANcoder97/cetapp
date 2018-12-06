package top.mirrorer.lijian;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class clockLayout extends LinearLayout implements Runnable{
    static public boolean stopThread=false;
    private TextView Time,text;
    private int year, month, day, hour, minute, second;
    private String name;
    private int b=4;
    private Thread thread;
    private static Handler mHandler;
    private String strt;
    private timeReduce r;
    private Calendar cal;
    private Context context;
    public clockLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.colcklayout,this);
        Time=(TextView)findViewById(R.id.colck_text);
        text=(TextView)findViewById(R.id.colck_text2);
        thread=new Thread(this);
        cal=Calendar.getInstance();
        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1) {Time.setText(strt);text.setText("距离"+name);}

            }
        };
    }
    public void setclock(String name,int year,int month,int day,int hour,int minute,int second){
        this.name=name;this.year=year;this.day=day;this.month=month;this.hour=hour;
        this.minute=minute;this.second=second;
    }
    public void ThreadreStart(){
        stopThread=true;
        thread.start();}
    public void threadStart(){
        thread.start();
    }
    @Override
    public void run() {

        r=new timeReduce(year,month,day,hour,minute,second);
        cal.set(2018,5,1,r.getHour(),r.getMin(),r.getSecond());
        while (!stopThread){

                cal.add(cal.SECOND,-1);
                strt =r.getDay()+"天"+cal.get(cal.HOUR)+"时"+cal.get(cal.MINUTE)+"分"+cal.get(cal.SECOND)+"秒";
                mHandler.sendEmptyMessage(1);
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }




