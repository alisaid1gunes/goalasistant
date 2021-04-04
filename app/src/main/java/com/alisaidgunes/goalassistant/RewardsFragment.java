package com.alisaidgunes.goalassistant;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class RewardsFragment extends Fragment {
    FloatingActionButton fab;
    GoalAsisstantDB gdb ;
    Context mc;
    RewardAdapterB rewardAdapterB;
    RecyclerView recyclerView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mc = context;
        gdb = new GoalAsisstantDB(mc);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      final View rootView =  inflater.inflate(R.layout.rewards_fragment_design, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.add_reward);

         rewardAdapterB = new RewardAdapterB(getActivity());
     recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_reward);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(rewardAdapterB);

        /*rootView.findViewById(R.id.add_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
addRewardDialShow();
            }
        });*/
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      addRewardDialShow();
    }
});
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(rewardAdapterB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRewardDialShow();
            }
        });
    }


    void addRewardDialShow() {

        final Dialog RewardAddDialog = new Dialog(getActivity());
        RewardAddDialog.setCancelable(true);
        RewardAddDialog.setContentView(R.layout.add_reward_dialog);

        final TextInputLayout tiName = (TextInputLayout) RewardAddDialog.findViewById(R.id.rewardname_ti);
        final TextInputLayout tiRewardDay = (TextInputLayout) RewardAddDialog.findViewById(R.id.reward_day_ti);

        final TextInputEditText editTextName = (TextInputEditText) RewardAddDialog.findViewById(R.id.reward_name_e);
        final TextInputEditText editTextRewardDay = (TextInputEditText) RewardAddDialog.findViewById(R.id.reward_day_e);

        Button save = (Button) RewardAddDialog.findViewById(R.id.reward_save);
        Button cancel = (Button) RewardAddDialog.findViewById(R.id.reward_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().length() < 1 && editTextRewardDay.getText().toString().length() < 1) {
                    tiName.requestFocus();
                    tiName.requestFocus();
                    tiName.setError(getString(R.string.error_message_name));
                    tiRewardDay.setError(getString(R.string.error_message_goal));

                } else if (editTextName.getText().toString().length() < 1 || editTextRewardDay.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError(getString(R.string.error_message_name));
                        tiRewardDay.clearFocus();
                       tiRewardDay.setError(null);
                    } else {
                        tiRewardDay.requestFocus();
                        tiRewardDay.setError(getString(R.string.error_message_goal));
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                   tiRewardDay.clearFocus();
                    tiRewardDay.setError(null);
                    new RewardDao().insert(gdb, editTextName.getText().toString(), Integer.parseInt(editTextRewardDay.getText().toString()));


                    Toast.makeText(getActivity(), "Kaydedildi", Toast.LENGTH_SHORT);


                    Fragment fragment = new RewardsFragment();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .detach(fragment)
                            .attach(fragment)
                            .commit();
                   RewardAddDialog.dismiss();

                }

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardAddDialog.dismiss();
            }
        });

       RewardAddDialog.show();
    }


}
