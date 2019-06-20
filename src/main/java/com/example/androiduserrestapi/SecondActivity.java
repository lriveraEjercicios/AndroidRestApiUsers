package com.example.androiduserrestapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {
    int userId;
    TextView tv_name, tv_id, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv_name = findViewById(R.id.tvName);
        tv_id = findViewById(R.id.tvId);
        tv_email = findViewById(R.id.tvEmail);

        userId = getIntent().getIntExtra("userId", 0);

        getUserFromRetrofit();
    }

    private void getUserFromRetrofit() {
        MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);

        Call<User> call = service.getUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                loadUser(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void loadUser(User u) {
        tv_name.setText(u.getName());
        tv_id.setText(String.valueOf(u.getId()));
        tv_email.setText(u.getEmail());
    }
}
