package com.example.aospettri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.aospettri.R;
import com.example.aospettri.databinding.FragmentThirdBinding;
import com.example.aospettri.Pettri;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBack.setOnClickListener(view1 -> NavHostFragment.findNavController(ThirdFragment.this)
                .navigate(R.id.action_ThirdFragment_to_FirstFragment));


        binding.buttonEnroll.setOnClickListener(view2 -> {
            EditText text1 = getView().findViewById(R.id.textbox_userid);
            EditText text2 = getView().findViewById(R.id.textbox_email);
            EditText text3 = getView().findViewById(R.id.textbox_name);

            String userId   = text1.getText().toString();
            String email    = text2.getText().toString();
            String name     = text3.getText().toString();

            Thread th = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            JSONArray propList = new JSONArray();

                            JSONObject prop1 = new JSONObject();
                            prop1.put("key", "email");
                            prop1.put("value", email);

                            JSONObject prop2 = new JSONObject();
                            prop2.put("key", "name");
                            prop2.put("value", name);

                            propList.put(prop1);
                            propList.put(prop2);


                            Pettri.createUser(userId, propList);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            th.start();

            NavHostFragment.findNavController(ThirdFragment.this)
                    .navigate(R.id.action_ThirdFragment_to_FourthFragment);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}