package ws.com.login_ws_team.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ws.com.login_ws_team.R;
import ws.com.login_ws_team.SignInActivity;
import ws.com.login_ws_team.util.DateUtils;
import ws.com.login_ws_team.util.GetPingMuSizeUtil;

public class DateAdapter extends BaseAdapter {
    private int[] days = new int[35];
    private Context context;
    private int year;
    private int month;

    public DateAdapter(Context context, int[][] days, int year, int month) {
        this.context = context;
        int dayNum = 0;
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
        this.year = year;
        this.month = month;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        InnerHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_signin, null);
            viewHolder = new InnerHolder(view);
            viewHolder.date_item = view.findViewById(R.id.tvWeek);
            viewHolder.setStyle();
            view.setTag(viewHolder);
        } else {
            viewHolder = (InnerHolder) view.getTag();
        }
        if(i== DateUtils.getCurrentDayOfMonth()){
            viewHolder.date_item.setTextColor(Color.rgb(204, 0, 0));//将当天设置背景设置成红色
        }
        if (i < 7 && days[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
        } else if (i > 20 && days[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
        }
        viewHolder.date_item.setText(days[i] + "");

        return view;
    }

    /**
     * 优化Adapter
     */
    class InnerHolder extends RecyclerView.ViewHolder {
        TextView date_item;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            this.date_item = itemView.findViewById(R.id.tvWeek);
        }

        public void setStyle(){
            double pingMuSize = GetPingMuSizeUtil.getPingMuSize(context);
            if(pingMuSize<4.5){
                date_item.setTypeface(Typeface.create(date_item.getTypeface(), Typeface.NORMAL), Typeface.BOLD);
                date_item.setTextSize(16);
                return ;
            }
            date_item.setTypeface(Typeface.create(date_item.getTypeface(), Typeface.NORMAL), Typeface.BOLD);
            date_item.setTextSize(24);
        }
    }
}
