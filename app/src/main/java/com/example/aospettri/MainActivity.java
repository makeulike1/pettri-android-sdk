package com.example.aospettri;

import android.os.Bundle;

import com.example.aospettri.room.AppDatabase;
import com.example.aospettri.room.Appdata;
import com.example.aospettri.room.AppdataDao;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aospettri.databinding.ActivityMainBinding;

import androidx.room.Room;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {



    private AppBarConfiguration appBarConfiguration;


    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("haha");




        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);




        // 딥링크 인텐트를 통해서 가지고 온 클릭키 및 파라미터를 추출
        Intent intent = getIntent();
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_VIEW)){
            System.out.println(intent.getData().getQueryParameter("key1"));
            System.out.println(intent.getData().getQueryParameter("key2"));
            System.out.println(intent.getData().getQueryParameter("click_key"));
        }



        // 앱 데이터베이스 초기화 및 앱에 저장되어 있는 클릭키를 가지고 옴
        /*
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "test").build();

        AppdataDao appdataDao = db.appdataDao();
        String ck = appdataDao.findCK();


        System.out.println(ck);

         */
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }




}