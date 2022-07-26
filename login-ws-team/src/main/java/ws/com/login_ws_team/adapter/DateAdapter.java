package ws.com.login_ws_team.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ws.com.login_ws_team.R;
import ws.com.login_ws_team.entity.SignInBean;
import ws.com.login_ws_team.util.DateUtils;
import ws.com.login_ws_team.util.GetPingMuSizeUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class DateAdapter extends BaseAdapter {
    private static final String TAG = "DateAdapter";
    private static int[] mDays;
    private static Context mContext;
    private static int mYear;
    private static int mMonth;
    private static DateAdapter instance;
    private static int[] mSignInDays;
    private static SignInBean.JpdetailBean mJpdetail;
    private int length;

    private DateAdapter() {
    }

    public static DateAdapter getInstance(Context context, int[][] days, int year, int month, int[] signInDays, List<SignInBean.JpdetailBean> jpdetail) {
        if (instance == null) {
            synchronized (DateAdapter.class){
                if(instance == null){
                    instance = new DateAdapter();
                }
            }
        }

        int dayNum = 0;
        int length = days.length;
        mDays = new int[length * 7];
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                mDays[dayNum] = days[i][j];
                dayNum++;
            }
        }
        mSignInDays = signInDays;
        mContext = context;
        mYear = year;
        mMonth = month;
        if(jpdetail!=null)
        mJpdetail = jpdetail.get(0);
        else{mJpdetail = null;}
        return instance;
    }



    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    InnerHolder viewHolder;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_signin, null);
//            View.inflate(viewGroup.getContext(), R.layout.item_information_department, null);
            viewHolder = new InnerHolder(view);
            viewHolder.date_item = view.findViewById(R.id.tvWeek);
            viewHolder.iv_item = view.findViewById(R.id.iv_check);
            viewHolder.iv_gift = view.findViewById(R.id.iv_gift);
//            viewHolder.date_item.setId(i);
            viewHolder.iv_item.setId(i);
            viewHolder.setStyle();
            view.setTag(viewHolder);
        } else {
            viewHolder = (InnerHolder) view.getTag();
        }
        if (mSignInDays != null) {
            for (int mSignInDay : mSignInDays) {
                if (mSignInDay != 0) {
                    if (mSignInDay == mDays[i]&&!(i > 20 && mDays[i] < 15)&&!(i > 20 && mDays[i] < 15)) {
                        viewHolder.iv_item.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if(mJpdetail!=null){
            Log.d(TAG, "getView: " + mJpdetail.getMonth() + mMonth);
            if (mJpdetail.getMonth() == mMonth) {
                if (mDays[i] == mJpdetail.getDay() &&!(i > 20 && mDays[i] < 15)&&!(i > 20 && mDays[i] < 15)) {
//                    viewHolder.date_item.setBackgroundResource(R.color.noBlack);
                    viewHolder.iv_gift.setVisibility(View.VISIBLE);
                    view.setOnClickListener(view1 -> {
                        try {
                            View textView = initToastView();
                            //自定义弹窗视图
                            ToastUtil.showSignIn(mContext, "",textView);
                        } catch (Exception e) {
                        }
                    });
                }
            }
        }
        if (mDays[i] == DateUtils.getCurrentDayOfMonth()&&!(i > 20 && mDays[i] < 15)&&!(i > 20 && mDays[i] < 15)) {
            viewHolder.date_item.setBackgroundResource(R.drawable.shape_signin_dataitem);
        }
        if (i < 7 && mDays[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
        } else if (i > 20 && mDays[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
        }
        viewHolder.date_item.setText(mDays[i] + "");
        return view;
    }

    private View initToastView() {
        TextView textView = new TextView(mContext);
        textView.setBackgroundResource(R.drawable.shape_signin_toast);
        textView.setPadding(50,25,50,25);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        String str;
        if(7!=mJpdetail.getContinuityNum()){
            str = "再连签" + (7 - mJpdetail.getContinuityNum()) + "天即可获得哦";
        }else{
            str = "您已经连续签到了7天了，下一次签到重新开始";
        }
        textView.setText(str);
        textView.setTextSize(18);
        return textView;
    }

    //将礼物的背景改变
    public void changeSetGift(int day,int month,int year,int num,int conNum) {
        SignInBean.JpdetailBean mJpdetail = new SignInBean.JpdetailBean();
        mJpdetail.setDay(day);
        mJpdetail.setContinuityNum(conNum);
        mJpdetail.setMonth(month);
        mJpdetail.setYear(year);
        mJpdetail.setNum(num);
        this.mJpdetail = mJpdetail;
        notifyDataSetChanged();
    }

    //修改礼物数据
    public void changeMJdetail(SignInBean.JpdetailBean jpdetailBean) {
        SignInBean.JpdetailBean mJpdetail = new SignInBean.JpdetailBean();
        mJpdetail.setDay(jpdetailBean.getDay());
        mJpdetail.setContinuityNum(jpdetailBean.getContinuityNum());
        mJpdetail.setMonth(jpdetailBean.getMonth());
        mJpdetail.setYear(jpdetailBean.getYear());
        mJpdetail.setNum(jpdetailBean.getNum());
        this.mJpdetail = mJpdetail;
        notifyDataSetChanged();
    }


    //将今天的背景改变
    public void changeToday(int today) {
        if (mSignInDays == null) {
            length = 1;
        } else {
            length = mSignInDays.length + 1;
        }
        int[] newArray = new int[length];
        for (int i = 0; i < length-1; i++) {
            if (length == 1) {
                newArray[0] = 0;
                break;
            }
            newArray[i] = mSignInDays[i];
        }
        newArray[length - 1] = today;
        mSignInDays = newArray;
        Log.d(TAG, "changeToday: " + mSignInDays);
        notifyDataSetChanged();
    }

    /**
     * 优化Adapter
     */
    class InnerHolder extends RecyclerView.ViewHolder {
        TextView date_item;
        TextView iv_item;
        ImageView iv_gift;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            this.date_item = itemView.findViewById(R.id.tvWeek);
            this.iv_item = itemView.findViewById(R.id.iv_check);
            this.iv_gift = itemView.findViewById(R.id.iv_gift);
        }

        public void setStyle() {
            double pingMuSize = GetPingMuSizeUtil.getPingMuSize((Activity)mContext);
            if (pingMuSize < 5.5) {
                date_item.setTypeface(Typeface.create(date_item.getTypeface(), Typeface.NORMAL), Typeface.BOLD);
                date_item.setTextSize(16);
                if (mDays.length / 7 > 5) {
                    date_item.setTextSize(12);
                }
                return;
            }
            date_item.setTypeface(Typeface.create(date_item.getTypeface(), Typeface.NORMAL), Typeface.BOLD);
            date_item.setTextSize(16);
        }
    }
}
