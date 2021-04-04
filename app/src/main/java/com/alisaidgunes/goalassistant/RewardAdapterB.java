package com.alisaidgunes.goalassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RewardAdapterB extends RecyclerView.Adapter<RewardAdapterB.Design> {
    private Context mcontext;
    GoalAsisstantDB gdb;
    int rewardCount;
    private List<Reward> allData;

    public RewardAdapterB(Context mcontext) {
        this.mcontext = mcontext;
        gdb = new GoalAsisstantDB(mcontext);
        allData = new RewardDao().getAllInfo(gdb);
        rewardCount = allData.size();
    }

    @NonNull
    @Override
    public Design onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_card_design, parent, false);
        return new RewardAdapterB.Design(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Design holder, final int position) {
        if (rewardCount != 0) {

            holder.ProgressCounter.setText("2/" + allData.get(position).getRewardopday());
            //üstteki kısımda bi algıritma eklenecek
            holder.Reward.setText(allData.get(position).getName());
            holder.RewardProgress.setMax(allData.get(position).getRewardopday());
            holder.RewardProgress.setProgress(2);
            //üstteki kısımda bi algıritma eklenecek
            holder.MenuImgReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mcontext, v);
                    //popupMenu.getMenu().add("Edit");
                    ///popupMenu.getMenu().add("Delete");
                    popupMenu.getMenuInflater().inflate(R.menu.rewards_and_punishments_popup,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.rew_and_punish_popup_edit_item:
                                   // Toast.makeText(mcontext,"edit",Toast.LENGTH_SHORT).show();
                                    editRewardDialShow(position);
                                    break;
                                case R.id.rew_and_punish_popup_delete_item:
                                   // Toast.makeText(mcontext,"delete",Toast.LENGTH_SHORT).show();
                                    final AlertDialog.Builder alert  = new AlertDialog.Builder(mcontext);
                                    alert.setMessage("Silmek istediğnizden eminmisiniz ?");
                                    alert.setCancelable(true);
                                    alert.setPositiveButton( "Evet", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new RewardDao().delete(gdb,allData.get(position).getId());
                                            RewardsFragment rewardsFragment = new RewardsFragment();

                                            Fragment fragment = new RewardsFragment();

                                            ((AppCompatActivity)mcontext).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.content_frame, fragment)
                                                    .detach(fragment)
                                                    .attach(fragment)
                                                    .commit();

                                        }
                                    });
                                    alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                   alert.create().show();
                                    break;

                            }
                            return false;
                        }
                    });


                    popupMenu.show();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return rewardCount;
    }

    public class Design extends RecyclerView.ViewHolder {
        private CardView CardReward;
        private ProgressBar RewardProgress;
        private TextView ProgressCounter;
        private TextView Reward;
        private ImageView MenuImgReward;

        public Design(@NonNull View itemView) {
            super(itemView);
            CardReward = (CardView) itemView.findViewById(R.id.card_reward);
            RewardProgress = (ProgressBar) itemView.findViewById(R.id.reward_progress);
            ProgressCounter = (TextView) itemView.findViewById(R.id.reward_counter);
            MenuImgReward = (ImageView) itemView.findViewById(R.id.menu_img_reward);
            Reward = (TextView) itemView.findViewById(R.id.reward);
        }
    }
    void editRewardDialShow(final int position) {

        final Dialog RewardEditDialog = new Dialog(mcontext);
        RewardEditDialog.setCancelable(true);
        RewardEditDialog.setContentView(R.layout.add_reward_dialog);

        final TextInputLayout tiName = (TextInputLayout) RewardEditDialog.findViewById(R.id.rewardname_ti);
        final TextInputLayout tiRewardDay = (TextInputLayout) RewardEditDialog.findViewById(R.id.reward_day_ti);

        final TextInputEditText editTextName = (TextInputEditText) RewardEditDialog.findViewById(R.id.reward_name_e);
        final TextInputEditText editTextRewardDay = (TextInputEditText) RewardEditDialog.findViewById(R.id.reward_day_e);

        Button save = (Button)RewardEditDialog.findViewById(R.id.reward_save);
        Button cancel = (Button) RewardEditDialog.findViewById(R.id.reward_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().length() < 1 && editTextRewardDay.getText().toString().length() < 1) {
                    tiName.requestFocus();
                    tiName.requestFocus();
                    tiName.setError(mcontext.getString(R.string.error_message_name));
                    tiRewardDay.setError(mcontext.getString(R.string.error_message_goal));

                } else if (editTextName.getText().toString().length() < 1 || editTextRewardDay.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError(mcontext.getString(R.string.error_message_name));
                        tiRewardDay.clearFocus();
                        tiRewardDay.setError(null);
                    } else {
                        tiRewardDay.requestFocus();
                        tiRewardDay.setError(mcontext.getString(R.string.error_message_goal));
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                    tiRewardDay.clearFocus();
                    tiRewardDay.setError(null);
                    new RewardDao().update(gdb,allData.get(position).getId(), editTextName.getText().toString(), Integer.parseInt(editTextRewardDay.getText().toString()));


                    Toast.makeText(mcontext, "Kaydedildi", Toast.LENGTH_SHORT);


                    Fragment fragment = new RewardsFragment();

                    ((AppCompatActivity)mcontext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .detach(fragment)
                            .attach(fragment)
                            .commit();

                    RewardEditDialog.dismiss();

                }

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardEditDialog.dismiss();
            }
        });

        RewardEditDialog.show();
    }
}
