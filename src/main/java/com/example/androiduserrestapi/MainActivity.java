package com.example.androiduserrestapi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androiduserrestapi.model.User;
import com.example.androiduserrestapi.retrofit.MyService;
import com.example.androiduserrestapi.retrofit.RetrofitClientInstance;

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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                openDialog(position);
                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersFromRetrofit();
    }

    //Menú:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    public void openDialog(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle(getString(R.string.app_name)); //modificar el titol
        alertDialogBuilder.setMessage("¿Seguro que quieres borrar el usuario?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Que volem fer si clica que si->
                        // Esborrar del servidor primer
                        deleteUserFromRetrofit(position);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); // No fem res, tanquem el Alert.
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create(); //crear el alert dialog
        alertDialog.show(); //mostrar per pantalla
    }

    private void deleteUserFromRetrofit(final int position) {
        MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);
        Call<Void> call = service.deleteUser(users.get(position).getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //Si el llistat NO és compartit, el podem esborrar visualment:
                users.remove(position);
                adapter.notifyDataSetChanged();

                //Si el llistat és compartit amb altres usuaris de l'app,
                //tornar a carregar el llistat desde servidor:
                //getUsersFromRetrofit();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

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
