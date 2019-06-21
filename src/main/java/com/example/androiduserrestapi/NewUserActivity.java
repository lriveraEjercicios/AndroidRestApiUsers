package com.example.androiduserrestapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiduserrestapi.model.User;
import com.example.androiduserrestapi.retrofit.MyService;
import com.example.androiduserrestapi.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserActivity extends AppCompatActivity {
    EditText et_name, et_username, et_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        et_mail = findViewById(R.id.et_mail);
        et_name = findViewById(R.id.et_name);
        et_username = findViewById(R.id.et_username);
    }

    public void saveNewUser(View view) {
        String name = et_name.getText().toString();
        String username = et_username.getText().toString();
        String mail = et_mail.getText().toString();

        if (checkFields(name, username, mail)) {
            User user = new User(name, username, mail);

            saveUserWithRetrofit(user);
        }
    }

    private void saveUserWithRetrofit(User user) {
        MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);
        Call<User> call = service.newUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Usuario creado con ID=" + response.body().getId(), Toast.LENGTH_LONG).show();
                finish(); //Tornem al llistat.
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al crear el usuario", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkFields(String name, String username, String mail) {
        boolean result = true;
        if ("".equals(username)) {
            result = false;
            et_username.setError("Username no pot ser buit");
        }
        if ("".equals(name)) {
            result = false;
            et_name.setError("Name no pot ser buit");
        }
        if ("".equals(mail)) {
            result = false;
            et_mail.setError("E-mail no pot ser buit");
        }
        return result;
    }
}
