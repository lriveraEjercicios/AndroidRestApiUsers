package com.example.androiduserrestapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.androiduserrestapi.model.User;
import com.example.androiduserrestapi.retrofit.MyService;
import com.example.androiduserrestapi.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {
    int userId;
    TextView tv_name, tv_id, tv_email, tv_street, tv_city, tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv_name = findViewById(R.id.tvName);
        tv_id = findViewById(R.id.tvId);
        tv_email = findViewById(R.id.tvEmail);
        tv_street = findViewById(R.id.tvStreet);
        tv_city = findViewById(R.id.tvCity);
        tv_location = findViewById(R.id.tvLocation);

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
        tv_street.setText(u.getAddress().getStreet());
        tv_city.setText(u.getAddress().getCity());
        tv_location.setText(u.getAddress().getGeo().getLat()+", "+u.getAddress().getGeo().getLng());
    }
}
