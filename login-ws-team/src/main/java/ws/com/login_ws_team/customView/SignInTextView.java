package ws.com.login_ws_team.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import ws.com.login_ws_team.R;

public class SignInTextView extends androidx.appcompat.widget.AppCompatTextView{

    public SignInTextView(Context context) {
        super(context);
    }

    public SignInTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignInTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if(drawables != null){
            Drawable leftDrawable = drawables[0]; //drawableLeft
            Drawable rightDrawable = drawables[2];//drawableRight
            Drawable topDrawable = drawables[1];//topDrawable
            Drawable bottomDrawable = drawables[3];//bottomDrawable
            if(leftDrawable !=null || rightDrawable != null||topDrawable!=null){
                //1,获取text的width
                float textWidth = getPaint().measureText(getText().toString());
                //2,获取padding
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth;
                float bodyWidth = 0;
                if(leftDrawable !=null){
                    //3,获取drawable的宽度
                    drawableWidth = leftDrawable.getIntrinsicWidth();
                    //4,获取绘制区域的总宽度
                    bodyWidth= textWidth + drawablePadding + drawableWidth;
                }
                canvas.translate((getWidth() - bodyWidth)/2,0);
            }
        }
        super.onDraw(canvas);
    }
}
