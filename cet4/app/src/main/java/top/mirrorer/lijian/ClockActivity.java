package top.mirrorer.lijian;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClockActivity extends AppCompatActivity {
    private String path;
    private Button newclock;
    private List<TimeclockBine> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        File file = Environment.getExternalStorageDirectory();
        path=file.getAbsolutePath()+"/SJJ";
        newclock=(Button)findViewById(R.id.clock_newclock_button);
        try {
            initlist();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mclockAdapter mclock = new mclockAdapter(ClockActivity.this,R.layout.clockmode,list);
        ListView listView = (ListView)findViewById(R.id.clock_listview);
        listView.setAdapter(mclock);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimeclockBine T = list.get(position);
                Intent intent = new Intent(ClockActivity.this,exchangeClockActivity.class);
                intent.putExtra("name",T.getName());
                startActivity(intent);
            }
        });
        newclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClockActivity.this,NewclockActivity.class));
            }
        });
    }
    private void initlist() throws IOException {
        FileReader rallclocklist = new FileReader(path+"/allclocklist.json");
        BufferedReader readallclocklist = new BufferedReader(rallclocklist);
        String s=null;
        Gson gson = new Gson();
        TimeclockBine  bine;
        while ((s=readallclocklist.readLine())!=null){
            if (s.equals("null")) break;
              bine=gson.fromJson(s,TimeclockBine.class);
              Log.d("123", bine.getName());
                        list.add(bine);
        }
        readallclocklist.close();
        rallclocklist.close();
    }
    public boolean onKeyDown(int keyCcode,KeyEvent event){
        if (keyCcode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
        }
        return true;
    }
}
