package ws.com.login_ws_team.util;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    private static Toast toast;
    public static void show(Context context,String desc){
        toast = Toast.makeText(context,desc,Toast.LENGTH_SHORT);
        showTime(1000);
    }

    private static void showTime(int cnt) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,2500);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        },cnt);
    }
}
