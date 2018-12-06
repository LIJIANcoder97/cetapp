package top.mirrorer.lijian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity_hello extends AppCompatActivity {
    private EditText namestr;
    private CheckBox siji,liuji;
    private Button sinup;
    private String level="四级";
    private String name;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        namestr=(EditText)findViewById(R.id.hello_name);
        siji  = (CheckBox)findViewById(R.id.hello_siji);
        liuji = (CheckBox)findViewById(R.id.hello_liuji);
        sinup = (Button)findViewById(R.id.hello_button);
        AndPermission.with(Activity_hello.this)
                     .requestCode(1000)
                     .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                     .send();
        siji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (siji.isChecked()) {liuji.setChecked(false); level="四级";}
            }
        });
        liuji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(liuji.isChecked()) {siji.setChecked(false);level="六级";}
            }
        });
        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=namestr.getText().toString();
                if (openFile()) startActivity(new Intent(Activity_hello.this,HOMEActivity.class));
                else{
                    Toast.makeText(Activity_hello.this, "出现问题了，小主给我权限了吗", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean openFile() {
        File file = Environment.getExternalStorageDirectory();
        String path=file.getAbsolutePath();
        Log.d("666", path);
        File f4=new File(path,"SJJ");
        if (!f4.exists())f4.mkdir();
        path=path+"/SJJ";
        File namef,timef;
        FileOutputStream namewr;
        try {
               //new pathexcise("data/data/四级君/files");

            try {
                new Timecontrol().newTimeclock("六级",2018,11,16,15,0,0,true,"六级plan");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                new Timecontrol().newTimeclock("四级",2018,11,16,9,0,0,true,"四级plan");
            } catch (IOException e) {
                e.printStackTrace();
            }
                char []b=name.toCharArray();
                char []c=level.toCharArray();
                namewr = new FileOutputStream(path+"/name.s");
                for (int i=0;i<b.length;i++){
                    namewr.write(b[i]);
                }
                new Timecontrol().setTimenow(level);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
