package top.mirrorer.lijian;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class HOMEActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        //初始化TabHost容器
        tab.setup(getLocalActivityManager());
        //在TabHost创建标签，然后设置：标题／图标／标签页布局
    //    tab.addTab(tab.newTabSpec("tab1").setIndicator("个人中心" , null).setContent(new Intent(this, AccountActivity.class)));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("主页" , null).setContent(new Intent(this, Activity_colck.class)));
        tab.addTab(tab.newTabSpec("tab3").setIndicator("项目" , null).setContent(new Intent(this, ClockActivity.class)));
        tab.setCurrentTab(0);
    }

}