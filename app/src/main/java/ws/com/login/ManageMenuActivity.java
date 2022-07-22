package ws.com.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ManageMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);
        initView();
        Intent intent = getIntent();
        String test = intent.getStringExtra("name");
        tv_test.setText(test);
    }

    private void initView() {
        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}