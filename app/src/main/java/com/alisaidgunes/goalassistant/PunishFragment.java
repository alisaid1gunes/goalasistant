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

public class PunishFragment extends Fragment {
    FloatingActionButton fab;
    GoalAsisstantDB gdb;
    Context mc;
    PunishmentAdapterB punishmentAdapterB;
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
        View rootView = inflater.inflate(R.layout.punish_fragment_design, container, false);
        punishmentAdapterB = new PunishmentAdapterB(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_punishment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(punishmentAdapterB);
        fab = (FloatingActionButton) rootView.findViewById(R.id.add_punishment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPunishDialShow();

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPunishDialShow();
                    }
                });
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
        recyclerView.setAdapter(punishmentAdapterB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPunishDialShow();
            }
        });
    }

    void addPunishDialShow() {

        final Dialog PunishAddDialog = new Dialog(getActivity());
        PunishAddDialog.setCancelable(true);
        PunishAddDialog.setContentView(R.layout.punadd);

        final TextInputLayout tiName = (TextInputLayout) PunishAddDialog.findViewById(R.id.punishname_ti);
        final TextInputLayout tiPunishDay = (TextInputLayout) PunishAddDialog.findViewById(R.id.punish_day_ti);

        final TextInputEditText editTextName = (TextInputEditText) PunishAddDialog.findViewById(R.id.punish_name_e);
        final TextInputEditText editTextPunishDay = (TextInputEditText) PunishAddDialog.findViewById(R.id.punish_day_e);

        Button save = (Button) PunishAddDialog.findViewById(R.id.punish_save);
        Button cancel = (Button) PunishAddDialog.findViewById(R.id.punish_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().length() < 1 && editTextPunishDay.getText().toString().length() < 1) {
                    tiName.requestFocus();
                    tiName.requestFocus();
                    tiName.setError(getString(R.string.error_message_name));
                    tiPunishDay.setError(getString(R.string.error_message_goal));

                } else if (editTextName.getText().toString().length() < 1 || editTextPunishDay.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError(getString(R.string.error_message_name));
                        tiPunishDay.clearFocus();
                        tiPunishDay.setError(null);
                    } else {
                        tiPunishDay.requestFocus();
                        tiPunishDay.setError(getString(R.string.error_message_goal));
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                    tiPunishDay.clearFocus();
                    tiPunishDay.setError(null);
                    new PunishmentDao().insert(gdb, editTextName.getText().toString(), Integer.parseInt(editTextPunishDay.getText().toString()));


                    Toast.makeText(getActivity(), "Kaydedildi", Toast.LENGTH_SHORT);


                    Fragment fragment = new PunishFragment();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .detach(fragment)
                            .attach(fragment)
                            .commit();
                    PunishAddDialog.dismiss();

                }

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PunishAddDialog.dismiss();
            }
        });

        PunishAddDialog.show();
    }
}
