package ws.com.login_ws_team.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    private static final String TAG = "ToastUtil";
    private static Toast toast;

    public static void show(Context context, String desc) {
        if (toast == null)
            toast = Toast.makeText(context, desc, Toast.LENGTH_SHORT);
        else {
            cancelToast();
            toast = Toast.makeText(context, desc, Toast.LENGTH_SHORT);
        }
        Log.d(TAG, "showSignIn: "+toast.getGravity());
        showTime(1000);
    }

    public static Toast showSignIn(Context context, String desc, View view) {
        if (toast == null) {
            toast = Toast.makeText(context, desc, Toast.LENGTH_LONG);
            toast.setView(view);
        } else {
            cancelToast();
            toast = Toast.makeText(context, desc, Toast.LENGTH_LONG);
            toast.setView(view);
        }
        Log.d(TAG, "showSignIn: "+toast.getGravity());
        toast.show();
//                showTime(1000);
        return toast;
    }

    //控制显示时间
    private static void showTime(int cnt) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 2500);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
