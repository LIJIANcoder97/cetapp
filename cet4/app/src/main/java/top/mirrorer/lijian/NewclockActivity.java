package top.mirrorer.lijian;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class NewclockActivity extends AppCompatActivity {
    private String name;
    private String path;
    private EditText clockname,Y,M,D,H,m;
    private TimeclockBine bine;
    private Button button;
    private CheckBox chsoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_clock);
        File file = Environment.getExternalStorageDirectory();
        path=file.getAbsolutePath()+"/SJJ";
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        clockname=(EditText)findViewById(R.id.exchange_name);
        Y=(EditText)findViewById(R.id.exchange_year);
        M=(EditText)findViewById(R.id.exchange_month);
        D=(EditText)findViewById(R.id.exchange_date);
        H=(EditText)findViewById(R.id.exchange_hour);
        m=(EditText)findViewById(R.id.exchange_miute);
        button=(Button)findViewById(R.id.exchange_button);
        chsoe=(CheckBox)findViewById(R.id.exchange_chose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clockname.getText()==null||Y.getText()==null||M.getText()==null||D.getText()==null||H.getText()==null||m.getText()==null){
                    Toast.makeText(NewclockActivity.this,"内容不能为空",Toast.LENGTH_LONG);
                }
                else {
                    try {
                        new Timecontrol().newTimeclock(clockname.getText().toString(),Integer.parseInt(Y.getText().toString()), Integer.parseInt(M.getText().toString()) - 1, Integer.parseInt(D.getText().toString()), Integer.parseInt(H.getText().toString()), Integer.parseInt(m.getText().toString()), 0,chsoe.isChecked(),clockname.getText().toString()+"plan");
                        new Timecontrol().setTimenow(clockname.getText().toString());
                        startActivity(new Intent(NewclockActivity.this,HOMEActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
