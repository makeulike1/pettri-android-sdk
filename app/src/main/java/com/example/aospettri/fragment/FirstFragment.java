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
import com.example.aospettri.network.IPConfig;
import com.example.aospettri.databinding.FragmentFirstBinding;
import com.example.aospettri.Pettri;

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


        binding.buttonLogin.setOnClickListener(view1 -> {

            EditText text = getView().findViewById(R.id.textbox_signup);
            String userId = text.getText().toString();

            LoginUser.userId = userId;

            String ip = IPConfig.getIPAddress(true); // IPv4
            JSONArray propList = new JSONArray();

            try {

                JSONObject prop1 = new JSONObject();
                prop1.put("key", "ip");
                prop1.put("value", ip);
                propList.put(prop1);

            }catch(JSONException e){
                e.printStackTrace();
            }


            Thread th = new Thread(new Runnable() {

                @Override
                public void run() {
                    Pettri.sendEvent("login", propList);
                }

            });

            th.start();

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

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