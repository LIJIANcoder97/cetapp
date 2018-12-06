package top.mirrorer.lijian;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

public class ClockService extends Service {
    private boolean activityStart=false;
    public ClockService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startld){
        ScreenListener screenListener=new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {

            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onScreenOff() {
                if (!activityStart) {
                    Intent intent1 = new Intent(ClockService.this, ScreenActivity.class);
                    startActivity(intent1);
                    activityStart=true;
                }
            }
            @Override
            public void onUserPresent() {
                ScreenActivity.screen.finish();
                activityStart=false;
            }
        });
        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
