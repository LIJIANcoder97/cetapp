package top.mirrorer.lijian;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer = new Timer();//实例化Timer类
        final Handler mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0: startActivity(new Intent(MainActivity.this,Activity_hello.class));finish();break;
                    case 1: startActivity(new Intent(MainActivity.this,HOMEActivity.class));finish();break;
                }
            }
            };
        timer.schedule(new TimerTask() {
            public void run() {
                File namere = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SJJ/name.s");
                if (namere.exists()) mHandler.sendEmptyMessage(1);
                else if (!namere.exists()) mHandler.sendEmptyMessage(0);
                this.cancel();
            }
        }, 2400);//

    }
}

