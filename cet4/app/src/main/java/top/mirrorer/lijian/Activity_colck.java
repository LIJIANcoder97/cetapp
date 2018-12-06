package top.mirrorer.lijian;



import android.app.Activity;
import android.content.Intent;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
;import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_colck extends AppCompatActivity  {
    private Button newplan;
private clockLayout clocklayout;
private String path;
public static Activity_colck activity;
private  TimeclockBine bine;
    private List<planbein> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //锁屏
//        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
//        getWindow().addFlags(flags);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
//        getWindow().setAttributes(params);
        //
        setContentView(R.layout.activity_colck);
        activity=this;
      // Intent intent=new Intent(Activity_colck.this,ClockService.class);
        File file = Environment.getExternalStorageDirectory();
        path=file.getAbsolutePath()+"/SJJ";
        try {
            FileReader rtime =new FileReader(path+"/time.s");
            BufferedReader readtime=new BufferedReader(rtime);
            Gson gson = new Gson();
            String s=null;
            s=readtime.readLine();
            readtime.close();rtime.close();
            Log.d("111", "onCreate: "+s);
            bine=gson.fromJson(s,TimeclockBine.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Listview
        try {
            initlist();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mplanAdapter mplan = new mplanAdapter(Activity_colck.this,R.layout.planlayout,list);
        ListView listView = (ListView)findViewById(R.id.activity_clock_list);
        listView.setAdapter(mplan);




        clocklayout=(clockLayout) findViewById(R.id.clock_Time);
        clocklayout.setclock(bine.getName(),bine.getYear(),bine.getMonth(),bine.getDay(),bine.getHour(),bine.getMinute(),bine.getSecond());
        clocklayout.threadStart();


      // startService(intent);
        newplan=(Button)findViewById(R.id.newaplan);
        newplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putActivity p = new putActivity();
                p.setActivity(activity);
                Intent intent = new Intent(Activity_colck.this,NewplanActivity.class);
                intent.putExtra("filename",bine.getPlanfile());
                intent.putExtra("path",path);
               //Bundle bundle=new Bundle();
              // bundle.putSerializable("this",p);
              // intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initlist() throws IOException {
        Calendar cal=Calendar.getInstance();
        FileReader rnowclocklist = new FileReader(path+"/nowclocklist.json");
        BufferedReader readnowclocklist = new BufferedReader(rnowclocklist);
        String s;
        Gson gson = new Gson();
        TimeclockBine bine;
        while ((s=readnowclocklist.readLine())!=null){
            bine=gson.fromJson(s,TimeclockBine.class);
            File f = new File(path,bine.getPlanfile());
            if (f.exists()){
                FileReader r1=new FileReader(path+"/"+bine.getPlanfile());
                BufferedReader reader1 = new BufferedReader(r1);
                String str = null;
                while ((str=reader1.readLine())!=null){
                    planbein bein=gson.fromJson(str,planbein.class);
                    if (bein.getY()==cal.get(cal.YEAR)&&bein.getM()==cal.get(cal.MONTH)&&bein.getD()==cal.get(cal.DATE)){
                        list.add(bein);
                    }

                }
                reader1.close();r1.close();

        }
    }
        readnowclocklist.close();
        rnowclocklist.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clockLayout.stopThread=true;
    }
    public boolean onKeyDown(int keyCcode,KeyEvent event){
            if (keyCcode==KeyEvent.KEYCODE_BACK){
                moveTaskToBack(true);
            }
            return true;
    }

}
