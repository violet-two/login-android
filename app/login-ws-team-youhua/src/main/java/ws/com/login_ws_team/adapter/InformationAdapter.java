package ws.com.login_ws_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ws.com.login_ws_team.R;
import ws.com.login_ws_team.util.InformationDPUtil;

public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;
    //上拉加载更多状态-默认为2
    private static int mLoadMoreStatus = 2;


    private static InformationAdapter instance;
    private InformationAdapter (){}
    private static List<InformationDPUtil.DataBean> mData;
    public static InformationAdapter getInstance(List<InformationDPUtil.DataBean> data) {
        mData = data;
        mLoadMoreStatus = 2;
        if (instance == null) {
            instance = new InformationAdapter();
        }
        return instance;
    }
    public static List<InformationDPUtil.DataBean> getList(){
        return  mData;
    }
    public static void setList(List<InformationDPUtil.DataBean> data){
        mData = data;
    }

//    public InformationAdapter(List<InformationDPUtil.DataBean> mData) {
//        this.mData = mData;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = View.inflate(parent.getContext(), R.layout.item_information_department, null);
            return new InnerHolder(view,this);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = View.inflate(parent.getContext(),R.layout.item_load, null);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            ((InnerHolder) holder).setData(mData.get(position),position);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mLoadLayout.setVisibility(View.VISIBLE);
                    break;
                case NO_LOAD_MORE:
                    //隐藏加载更多
                    footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        System.out.println("adapter"+mData.size());
        return mData.size()+1;
    }
    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class InnerHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        TextView dp;
        TextView role;
        private RecyclerView.Adapter adapter;

        //获取控件
        public InnerHolder(@NonNull View itemView, RecyclerView.Adapter adapter) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.phone = itemView.findViewById(R.id.phone);
            this.dp = itemView.findViewById(R.id.dp);
            this.role = itemView.findViewById(R.id.role);
            this.adapter = adapter;
        }

        //设置每个控件的值
        public void setData(InformationDPUtil.DataBean informationDPUtil, int position) {
            phone.setText(informationDPUtil.getPhone());
            name.setText(informationDPUtil.getRegname());
            dp.setText(informationDPUtil.getDepartment());
            role.setText(informationDPUtil.getUserStatus());
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mPbLoad;
        LinearLayout mLoadLayout;
        public FooterViewHolder(View itemView) {
            super(itemView);
            this.mPbLoad = itemView.findViewById(R.id.pbLoad);
            this.mLoadLayout = itemView.findViewById(R.id.loadLayout);
            this.mLoadLayout.setVisibility(View.GONE);
        }
    }

    public void addHeaderItem(List<InformationDPUtil.DataBean> items){
        if(items==null){
            notifyDataSetChanged();
            return ;
        }
        mData.addAll(mData.size(),items);
        notifyDataSetChanged();
    }


    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }
}
