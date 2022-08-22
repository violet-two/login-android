package ws.com.login_ws_team.customView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ws.com.login_ws_team.R;
import ws.com.login_ws_team.adapter.WeekAdapter;

public class SignDate extends LinearLayout {
    private TextView tvYear;
    private SignInCalendarView gvWeek;
    private SignInCalendarView gvDate;

    public SignDate(Context context) {
        super(context);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(), R.layout.item_signin,this);
        gvWeek = view.findViewById(R.id.gvWeek);
        gvWeek.setAdapter(new WeekAdapter(getContext()));
    }
}
