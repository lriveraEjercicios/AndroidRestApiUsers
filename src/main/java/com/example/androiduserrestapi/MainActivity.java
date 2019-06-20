package com.example.androiduserrestapi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Activity activity = this;
    ArrayList<User> users;
    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<User>();

        listView = findViewById(R.id.listView);
        adapter = new MyAdapter(activity, R.layout.row, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int userId = users.get(position).getId();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        getUsersFromRetrofit();




    }

    private void getUsersFromRetrofit() {

        MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);

        Call<List<User>> call = service.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //TODO: Mostrar error al usuario.
            }
        });
    }
}
