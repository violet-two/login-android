package ws.com.login_ws_team.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ws.com.login_ws_team.R;
import ws.com.login_ws_team.util.InformationDPUtil;

public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static InformationAdapter instance;
    private InformationAdapter (){}
    private static List<InformationDPUtil.DataBean> mData;
    public static InformationAdapter getInstance(List<InformationDPUtil.DataBean> data) {
        mData = data;
        if (instance == null) {
            instance = new InformationAdapter();
        }
        return instance;
    }

//    public InformationAdapter(List<InformationDPUtil.DataBean> mData) {
//        this.mData = mData;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_information_department, null);
        return new InnerHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((InnerHolder) holder).setData(mData.get(position),position);
    }

    public void addHeaderItem(List<InformationDPUtil.DataBean> items){
        mData.addAll(mData.size(),items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
}
