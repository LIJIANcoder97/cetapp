package top.mirrorer.lijian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

import static java.security.AccessController.getContext;

public class ScreenActivity extends AppCompatActivity implements Runnable {
    private boolean stopThread = false;
    private TextView Time, text, SystemTime;
    private Thread four, six;
    private Handler mHandler;
    private String strt;
    private timeReduce r4, r6;
    private Calendar cal;
    private Context context;
    private LinearLayout layout_back;
    public static Activity screen;
    private String path;
    private TimeclockBine bine;
    private timeReduce r;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        getWindow().addFlags(flags);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        getWindow().setAttributes(params);
        screen=this;
        setContentView(R.layout.activity_screen);
        Time = (TextView) findViewById(R.id.screen_text);
        text = (TextView) findViewById(R.id.screen_text2);
        SystemTime = (TextView) findViewById(R.id.screen_time);
        layout_back = (LinearLayout) findViewById(R.id.screen_back);
        layout_back.setBackgroundResource(R.drawable.backlock4);
        thread=new Thread(this);
        cal=Calendar.getInstance();
        File file = Environment.getExternalStorageDirectory();
         path = file.getAbsolutePath() + "/SJJ";
        try {
            FileReader r =new FileReader(path+"/time.s");
            BufferedReader reader=new BufferedReader(r);
            Gson gson = new Gson();

            String s=null;
            s=reader.readLine();
            bine=gson.fromJson(s,TimeclockBine.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        r=new timeReduce(bine.getYear(),bine.getMonth(),bine.getDay(),bine.getHour(),bine.getMinute(),bine.getSecond());
        cal.set(2018,5,1,r.getHour(),r.getMin(),r.getSecond());
        thread.start();
        }




    @Override
    public void run() {
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread=true;
    }
    public boolean onKeyDown(int keyCcode,KeyEvent event){
        if (keyCcode==KeyEvent.KEYCODE_HOME){
            finish();
        }
        return true;
    }
}
