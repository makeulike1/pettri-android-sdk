package com.example.aospettri;

import android.os.Bundle;

import com.example.aospettri.network.IPConfig;
import com.example.aospettri.room.AppDatabase;
import com.example.aospettri.room.Appdata;
import com.example.aospettri.room.AppdataDao;
import com.example.aospettri.thread.WriteInstall;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aospettri.databinding.ActivityMainBinding;

import androidx.room.Room;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        Intent intent = getIntent();

        String action = getIntent().getAction();

        if (action.equals(Intent.ACTION_VIEW)) {
            String CLICK_KEY =      intent.getData().getQueryParameter("click_key");
            String TRACKING_ID =    intent.getData().getQueryParameter("trkId");
            String APP_KEY =        intent.getData().getQueryParameter("appKey");

            // 페트리 SDK 초기화
            initPettri(CLICK_KEY, TRACKING_ID);

        }


    }





    public void initPettri(String CLICK_KEY, String TRACKING_ID){

        Thread th = new Thread(new Runnable(){

            @Override
            public void run(){
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "test").build();

                AppdataDao appdataDao = db.appdataDao();

                // 앱 데이터베이스 초기화 및 앱에 저장되어 있는 클릭키를 가지고 옴
                String isCK = appdataDao.findCK();

                String isTrkID = appdataDao.findTrkId();

                //appdataDao.delete();


                if (isCK == null) {

                        // String APP_KEY = intent.getData().getQueryParameter("appKey");

                        if ((CLICK_KEY != null) && (TRACKING_ID != null)){
                            Appdata ap = new Appdata(1, CLICK_KEY, TRACKING_ID);
                            appdataDao.insert(ap);
                            System.out.println("*** Click key " + CLICK_KEY + " is successfully saved into Room DB.");


                            // 최초 실행(인스톨)에 대해서 인스톨 로그를 남김.
                            JSONArray propList = new JSONArray();
                            String ip = IPConfig.getIPAddress(true); // IPv4

                            try {

                                JSONObject prop1 = new JSONObject();
                                prop1.put("key", "ip");
                                prop1.put("value", ip);
                                propList.put(prop1);

                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                            WriteInstall thread = new WriteInstall(CLICK_KEY, propList);
                            thread.start();

                            AppConfig.trkId = TRACKING_ID;
                            AppConfig.ck = CLICK_KEY;
                        }

                } else {
                    System.out.println("*** Getting saved click key from Room DB : " + isCK);
                    System.out.println("*** Getting saved tracking id from Room DB : " + isTrkID);

                    AppConfig.ck = isCK;
                    AppConfig.trkId = isTrkID;
                }

                db.close();
            }
        });

        th.start();

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