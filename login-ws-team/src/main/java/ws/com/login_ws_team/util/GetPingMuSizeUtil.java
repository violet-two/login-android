package ws.com.login_ws_team.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

public class GetPingMuSizeUtil {

    private static String TAG = "GetPingMuSizeUtil";

    public static double getPingMuSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.getDisplay().getRealSize(point);
        } else {
            activity.getWindowManager().getDefaultDisplay().getSize(point);
        }
        double w = point.x / metrics.xdpi; // unit is inch
        double h = point.y / metrics.ydpi; // unit is inch
        double size = Math.sqrt(w * w + h * h);
        Log.d(TAG, String.format("Screen size: %.1f", size));
        return size;
    }
}
