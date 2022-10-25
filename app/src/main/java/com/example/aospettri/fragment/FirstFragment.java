package com.example.aospettri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.aospettri.LoginUser;
import com.example.aospettri.R;
import com.example.aospettri.databinding.FragmentFirstBinding;
import com.example.aospettri.thread.EventLoggingThread;

import org.json.JSONArray;

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
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText text = (EditText)getView().findViewById(R.id.textbox_signup);

                String userId = text.getText().toString();

                LoginUser.userId = userId;

                JSONArray propList = new JSONArray();
                EventLoggingThread thread = new EventLoggingThread("login", userId, propList);
                thread.start();

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        // 로그인 버튼 누르면 로그인 이벤트 서버로 전송, 회원 가입 화면 오픈
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}