package ws.com.login_ws_team;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;
import ws.com.login_ws_team.adapter.InformationAdapter;
import ws.com.login_ws_team.entity.InformationDPBean;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.UserManage;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.InformationDepartmentModelImpl;
import ws.com.login_ws_team.util.DPUtil;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class InformationDepartmentActivity extends BaseActivity {

    private String TAG = "InformationDepartmentActivity";
    private SearchView searchView;
    private TextView search;
    private RecyclerView informationListRV;
    private String selfPhone;
    private Integer selfStatus;
    private String department;
    private String regname;
    private TextView dpAndName;
    private View allView;
    private LinearLayout bottomBox;
    private SwipeRefreshLayout sr;
    private InformationDepartmentModelImpl informationDepartmentModel;
    private HashMap<String, String> hashMap;
    private LinearLayout contentBox;
    private LinearLayout contentBoxByDP;

    private Type type;
    private Gson gson;
    //    private UpPullAdapter upPullAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_department);

        informationDepartmentModel = new InformationDepartmentModelImpl();
        sr = findViewById(R.id.sr);
        contentBox = findViewById(R.id.contentBox);
        contentBoxByDP = findViewById(R.id.contentBoxByDP);
        informationListRV = findViewById(R.id.informationListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        informationListRV.setLayoutManager(linearLayoutManager);
        informationListRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            //触摸拦截事件 返回false不执行后面函数
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //取消search框的焦点
                searchView.clearFocus();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //设置主题
        setTheme();
        //初始化登录数据
        initLoginData();

        searchView = findViewById(R.id.searchView);
        //设置输入框属性
        searchViewStyle();
        search = findViewById(R.id.tv_search);
        //设置搜索按钮的点击事件
        search.setOnClickListener(view -> {
            String phone = searchView.getQuery().toString();
            hashMap.put("phone", phone);
            initData(hashMap);
//            InformationDP.informationDP(this, allView, hashMap);
        });
    }

    private void upPullAndDownPush() {
        handlerUpPullOnload();
        handlerDownPullUpdate();
    }

    static int showEndNum = 0;
    int lastVisibleItem = 0;
    private long lastonScrollTime=0;//全局变量
    private void handlerUpPullOnload() {
        informationListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == InformationAdapter.getList().size()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InformationAdapter instance = InformationAdapter.getInstance(InformationAdapter.getList());
                            instance.changeMoreStatus(InformationAdapter.NO_LOAD_MORE);
                        }
                    }, 1000);
                    return;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                List<InformationDPBean.DataBean> list = InformationAdapter.getList();
                InformationAdapter instance = InformationAdapter.getInstance(list);
                if (dy == 0) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            instance.changeMoreStatus(2);
                            return;
                        }
                    });
                    return;
                }
                if (dy > 0) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            instance.changeMoreStatus(0);
                            return;
                        }
                    });
                }
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("managerPhone", selfPhone);
                hashMap.put("phone", "");
                informationDepartmentModel.queryInformation(hashMap, new IBaseRetCallback<InformationDPBean>() {
                    @Override
                    public void onSucceed(Response<InformationDPBean> response) {
                        InformationDPBean body = response.body();
                        List<InformationDPBean.DataBean> info;
                        int showMaxNum = getInformationListRVHeight();
                        InformationAdapter instance;
                        if(body==null){
                            onSucceed(response);
                        }
                        if ("success".equals(body.getFlag())) {
                            info = gson.fromJson(body.getData().toString(), type);
                            synchronized (info) {
                                //计算页面最大可以显示几条数据
                                if (showMaxNum == 0) {
                                    int height = informationListRV.getHeight();
                                    showMaxNum = DPUtil.px2dip(InformationDepartmentActivity.this, height) / 60;
                                }

                                //获取现在适配器里面数据的长度
                                List<InformationDPBean.DataBean> list = InformationAdapter.getList();
                                //获取适配器
                                instance = InformationAdapter.getInstance(list);
                                synchronized (instance) {
                                    int size = list.size();
                                    showEndNum = size + 1;
                                    if (showEndNum > info.size()) {
                                        if (size > showMaxNum) {
                                            return;
                                        }
                                        List<InformationDPBean.DataBean> dataBeans = info.subList(size, info.size());
                                        instance.changeMoreStatus(instance.PULLUP_LOAD_MORE);
                                        //添加数据
                                        instance.addHeaderItem(dataBeans);
                                        return;
                                    }

                                    //从全部数据中获取要加载的数据段
                                    List<InformationDPBean.DataBean> dataBeans = info.subList(size, showEndNum);
                                    instance.changeMoreStatus(instance.PULLUP_LOAD_MORE);
                                    //添加数据
                                    instance.addHeaderItem(dataBeans);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable t) {

                    }
                });

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void handlerDownPullUpdate() {
        sr.setEnabled(true);
        //添加事件监听器
//        initData(hashMap);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //上拉刷新重置数据
                        initData(hashMap);
                        ToastUtil.show(InformationDepartmentActivity.this, "成功刷新");
                        //停止刷新
                        sr.setRefreshing(false);
                        //设置searchView的输入框查询字段
                        searchView.setQuery("", true);
                    }
                }, 0);
            }
        });
    }

    private void initLoginData() {
        gson = new Gson();
        type = new TypeToken<ArrayList<InformationDPBean.DataBean>>() {
        }.getType();
        dpAndName = findViewById(R.id.dpAndName);
        //获取登录和注册页面传过来的参数
        getLoginData();
        //初始化所有人员信息
        hashMap = new HashMap<>();
        hashMap.put("managerPhone", selfPhone);
        hashMap.put("phone", "");
        initData(hashMap);
        //上拉加载，下拉刷新
        upPullAndDownPush();
    }

    private void setTheme() {
        //设置状态栏背景为透明---------------
        StatusBarUtil.getStatusAToTransparent(this);
        //获取状态栏高度
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        //获取RelativeLayout
        RelativeLayout topBox = findViewById(R.id.topBox);
        //设置属性,获取属性要获取到他的父级容器标签或者布局
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) topBox.getLayoutParams();
        lp.setMargins(0, statusBarHeight, 0, 0);
        topBox.setLayoutParams(lp);
//        -----------------------------
        bottomBox = findViewById(R.id.bottomBox);
        //ture是没有底部导航栏
        boolean b = StatusBarUtil.checkHasNavigationBar(this);
        //获取底部导航栏高度
        int navigationHeight = StatusBarUtil.getNavigationHeight(this);
        if (!b) {
            lp = (RelativeLayout.LayoutParams) bottomBox.getLayoutParams();
            lp.setMargins(0, 0, 0, navigationHeight);
            bottomBox.setLayoutParams(lp);
        }
    }

    private synchronized void initData(HashMap<String, String> hashMap) {
        allView = findViewById(R.id.allView);
        informationDepartmentModel.queryInformation(hashMap, new IBaseRetCallback<InformationDPBean>() {
            @Override
            public void onSucceed(Response<InformationDPBean> response) {
                InformationDPBean body = response.body();
                if ("fail".equals(body.getFlag())) {
                    ToastUtil.show(InformationDepartmentActivity.this, body.getData().toString());
                    informationListRV.setAdapter(null);
                    return;
                }
                List<InformationDPBean.DataBean> data = gson.fromJson(body.getData().toString(), type);
                //获取informationListRV的视图一面最多可以放多少个item
                int itemNum = getInformationListRVHeight();
                if (data.size() > itemNum) {
                    data = data.subList(0, itemNum);
                }
//                for (int i = 0; i < data.size(); i++) {
//                    data.get(i).setDepartment("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                    data.get(i).setRegname("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                    data.get(i).setPhone("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                    data.get(i).setUserStatus("图一面最多可以放多少图一面最多可以放多少多少图一面最");
//                }
                InformationAdapter informationAdapter = InformationAdapter.getInstance(data);
                informationAdapter.changeMoreStatus(2);
                informationListRV.setAdapter(informationAdapter);
            }

            @Override
            public void onFailed(Throwable t) {
                Log.d(TAG, "onFailed: "+t);
            }
        });
    }

    private int getInformationListRVHeight() {
        int height = informationListRV.getHeight();
        int itemNum = (int) Math.round(((double) DPUtil.px2dip(this, height)) / 60);
        return itemNum;
    }


    private void getLoginData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            LoginBean result = (LoginBean) bundle.getSerializable("data");
            Object data = result.getData().toString();
            System.out.println(data);
            Type type = new TypeToken<ArrayList<LoginBean.DataBean>>() {
            }.getType();
            List<LoginBean.DataBean> info = gson.fromJson(result.getData().toString(), type);
            System.out.println(info);
            regname = info.get(0).getRegname();
            department = info.get(0).getDepartment();
            dpAndName.setText(department + "+" + regname);
            selfStatus = info.get(0).getStatus();
            selfPhone = info.get(0).getPhone();
        }

        if (selfStatus == 1) {
            contentBox.setVisibility(View.GONE);
            contentBoxByDP.setVisibility(View.VISIBLE);
        }
    }

    private long lastChangeTextTime=0;//全局变量
    public void searchViewStyle() {
        //设置输入框的图标不可见
        searchView.setSubmitButtonEnabled(false);
        //设置搜索图标在输入框外面
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //判断是否为管理员
                if (selfStatus == 1) {
                    searchView.clearFocus();
                    ToastUtil.show(InformationDepartmentActivity.this, "未获取到管理员账号");
                    return false;
                }
                hashMap.put("phone", query);
                initData(hashMap);
//                InformationDP.informationDP(InformationDepartmentActivity.this, allView, hashMap);
                return false;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                hashMap.put("phone", newText);
                if (selfStatus == 1) {
                    return false;
                }
                if ("".equals(newText)) {
                    initData(hashMap);
                }
                long time=SystemClock.uptimeMillis();//局部变量
                if (time-lastChangeTextTime<=1000) {
                    return false;
                }else {
                    lastChangeTextTime=time;
                }
                initData(hashMap);
                return false;
            }
        });
    }


    public void quit(View view) {

        //清除SharedPreferences里面的数据
        UserManage.getInstance().saveUserInfo(InformationDepartmentActivity.this, null);
        Intent intent = new Intent(this, MainActivity.class);
        //新建一个activity并清除以前的全部activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
//        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //关闭软键盘
        closeKeyBoard();
        //取消search框的焦点
        searchView.clearFocus();
        return super.onTouchEvent(event);
    }
    

    public void reBack(View view) {
        finish();
    }

    //修改密码
    public void modifyPassword(View view) {
        Intent intent = new Intent(this, ModifyPasswordActivity.class);
        //如果修改密码成功则修改密码activity不进入栈
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("phone", selfPhone);
        this.startActivity(intent);
    }
}