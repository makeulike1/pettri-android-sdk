package com.example.aospettri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.aospettri.AppConfig;
import com.example.aospettri.LoginUser;
import com.example.aospettri.R;
import com.example.aospettri.Utils;
import com.example.aospettri.databinding.FragmentFirstBinding;
import com.example.aospettri.thread.EventLoggingThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // 로그인 버튼 누르면 로그인 이벤트 서버로 전송, 로그인 화면 오픈
        binding.buttonLogin.setOnClickListener(view1 -> {

            EditText text = getView().findViewById(R.id.textbox_signup);
            String userId = text.getText().toString();

            LoginUser.userId = userId;
            String ip = Utils.getIPAddress(true); // IPv4
            JSONArray propList = new JSONArray();

            try {

                JSONObject prop1 = new JSONObject();
                prop1.put("key", "ip");
                prop1.put("value", ip);
                propList.put(prop1);

            }catch(JSONException e){
                e.printStackTrace();
            }

            EventLoggingThread thread = new EventLoggingThread(AppConfig.ck, "login", userId, propList);
            thread.start();

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

        // 로그인 버튼 누르면 로그인 이벤트 서버로 전송, 회원 가입 화면 오픈
        binding.buttonSignup.setOnClickListener(view2 ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_ThirdFragment));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}