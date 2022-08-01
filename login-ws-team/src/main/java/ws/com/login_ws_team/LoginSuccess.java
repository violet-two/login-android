package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Bundle bundle = getIntent().getExtras();
        String state = bundle.getString("state");
        TextView show = findViewById(R.id.show);
        show.setText(state);
    }
}