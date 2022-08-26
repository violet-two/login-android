package ws.com.login_ws_m.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ScreenUtil {

    public static void closeSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
