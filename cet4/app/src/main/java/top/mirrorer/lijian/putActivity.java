package top.mirrorer.lijian;

import android.app.Activity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class putActivity implements Serializable {
 Activity_colck activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity_colck activity) {
        this.activity = activity;
    }
}

