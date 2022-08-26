package ws.com.login_ws_team.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SignInRadioView extends View {
    public SignInRadioView(Context context) {
        super(context);
    }

    public SignInRadioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignInRadioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
