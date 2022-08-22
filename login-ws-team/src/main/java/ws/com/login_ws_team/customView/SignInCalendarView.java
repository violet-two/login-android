package ws.com.login_ws_team.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import androidx.annotation.Nullable;

public class SignInCalendarView extends GridView {
    public SignInCalendarView(Context context) {
        super(context);
    }

    public SignInCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignInCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 3, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
