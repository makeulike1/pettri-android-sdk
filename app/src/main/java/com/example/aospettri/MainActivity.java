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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private AppBarConfiguration appBarConfiguration;


    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        // 딥링크 인텐트를 통해서 가지고 온 클릭키 및 파라미터를 추출
        Intent intent = getIntent();



        // [스레드 1] : Room 데이터베이스 초기화 및 CRUD
        Thread thread1 =  new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        checkCK(intent);
                    }
        });

        thread1.start();



        // [스레드 2] : 페트리 서버로 페이지 방문 이벤트 넘겨줌
        Thread thread2 = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    List<ReqProp> propList = new ArrayList<ReqProp>();
                    //RestApi.callEventCreate("abc","hi", propList);
                }
        });


        thread2.start();




        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }





    public void checkCK(Intent intent){
        String action = intent.getAction();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "test").build();

        AppdataDao appdataDao = db.appdataDao();

        // 앱 데이터베이스 초기화 및 앱에 저장되어 있는 클릭키를 가지고 옴
        String isCK = appdataDao.findCK();


        appdataDao.delete();


        // 클릭키가 없으면 신규로 전달받은 클릭키를 Room DB에 저장.
        if (isCK == null) {
            if (action.equals(Intent.ACTION_VIEW)) {
                String CLICK_KEY = intent.getData().getQueryParameter("click_key");

                if (CLICK_KEY != null) {
                    Appdata ap = new Appdata(1, CLICK_KEY);
                    appdataDao.insert(ap);
                    System.out.println("*** Click key " + CLICK_KEY + " is successfully saved into Room DB.");


                    // 최초 실행(인스톨)에 대해서 인스톨 로그를 남김.
                    List<ReqProp> propList = new ArrayList<ReqProp>();
                    RestApi.callInstallCreate(CLICK_KEY, propList);
                }
            }

        } else System.out.println("*** Getting saved click key from Room DB : " + isCK);

        db.close();
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