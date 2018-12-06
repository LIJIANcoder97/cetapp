package top.mirrorer.lijian;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class NewplanActivity extends AppCompatActivity {
private String path;
private Activity_colck activity_clock;
private String filename;
private EditText plan,locality;
private Button button,returnbutton;
private CalendarView mcalendarView;
private  int y,m,d;
private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan);
        path=getIntent().getStringExtra("path");
        filename=getIntent().getStringExtra("filename");
      //  putActivity p=(putActivity) getIntent().getSerializableExtra("this");
      //  activity_clock= (Activity_colck) p.getActivity();
        plan=(EditText)findViewById(R.id.plan_name);
        locality=(EditText)findViewById(R.id.plan_locality);
        button=(Button)findViewById(R.id.newaplan_ok);
        returnbutton=(Button)findViewById(R.id.return_clock);
        mcalendarView=(CalendarView)findViewById(R.id.plan_cal);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calendar=Calendar.getInstance();
        y=calendar.get(calendar.YEAR);m=calendar.get(calendar.MONTH);d=calendar.get(calendar.DATE);
        mcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                long t1=calendar.getTimeInMillis();
                calendar.set(year,month,dayOfMonth);
                long t2 =calendar.getTimeInMillis();
                if ((int)(t2-t1)/(1000*60*60*24)<0){
                    Toast.makeText(NewplanActivity.this,"不能选择过去",Toast.LENGTH_LONG);
                }else {
                    y=year;m=month;d=dayOfMonth;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!plan.getText().toString().equals("plan")&&!locality.getText().toString().equals("place")){
                    File f= new File(path,filename);
                    if (!f.exists()) try {
                        f.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    planbein p = new planbein();
                    p.setLocality(locality.getText().toString());
                    p.setPlan(plan.getText().toString());
                    p.setY(y);p.setD(d);p.setM(m);
                    p.setTime(calendar.getTimeInMillis());
                    Gson gson=new Gson();
                    try {
                        FileWriter w = new FileWriter(path+"/"+filename,true);
                        BufferedWriter writer=new BufferedWriter(w);
                        writer.write(gson.toJson(p));writer.newLine();
                        writer.flush();writer.close();w.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //activity_clock=Activity_colck.activity;
                   // activity_clock.finish();
                    startActivity(new Intent(NewplanActivity.this,HOMEActivity.class));
                }
            }
        });
    }
}
