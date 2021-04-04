package com.alisaidgunes.goalassistant;

import android.app.Dialog;
import android.content.Context;
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

import java.util.List;

public class PunishmentAdapterB extends RecyclerView.Adapter<PunishmentAdapterB.PunDesign> {
    private Context mcontext;
    private GoalAsisstantDB gdb;
    private int punishCount;
    private List<Punishment> allData;

    public PunishmentAdapterB(Context mcontext) {
        this.mcontext = mcontext;
        this.gdb = new GoalAsisstantDB(mcontext);
        this.allData = new PunishmentDao().getAllInfo(gdb);
        this.punishCount = allData.size();
    }

    @NonNull
    @Override
    public PunDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.punishment_card_design,parent,false);
        return new PunishmentAdapterB.PunDesign(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PunDesign holder, final int position) {
        if(punishCount!=0){

            holder.ProgressCounter.setText("2/"+allData.get(position).getPunishmentopday());
            //üstteki kısımda bi algıritma eklenecek
            holder.Punishment.setText(allData.get(position).getName());
            holder.PunishmentProgress.setMax(allData.get(position).getPunishmentopday());
            holder.PunishmentProgress.setProgress(2);
            //üstteki kısımda bi algıritma eklenecek
            holder.MenuImgPunishment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mcontext, v);
                   popupMenu.getMenuInflater().inflate(R.menu.rewards_and_punishments_popup,popupMenu.getMenu());
                   popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {
                           switch (item.getItemId()){
                               case R.id.rew_and_punish_popup_edit_item:
                                   editPunishDialShow(position);
                                   break;
                               case R.id.rew_and_punish_popup_delete_item:
                                   new PunishmentDao().delete(gdb,allData.get(position).getId());
                                   Fragment fragment = new PunishFragment();

                                   ((AppCompatActivity)mcontext).getSupportFragmentManager()
                                           .beginTransaction()
                                           .replace(R.id.content_frame, fragment)
                                           .detach(fragment)
                                           .attach(fragment)
                                           .commit();
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
        return punishCount;
    }

    public class PunDesign extends RecyclerView.ViewHolder{
        private CardView CardPunishment;
        private ProgressBar PunishmentProgress;
        private TextView ProgressCounter;
        private TextView Punishment;
        private ImageView MenuImgPunishment;

        public PunDesign(@NonNull View itemView) {
            super(itemView);
            CardPunishment = (CardView)itemView.findViewById(R.id.card_punishment);
            PunishmentProgress  = (ProgressBar)itemView.findViewById(R.id.punishment_progress);
            ProgressCounter = (TextView)itemView.findViewById(R.id.punishment_counter);
            MenuImgPunishment = (ImageView)itemView.findViewById(R.id.menu_img_punishment);
            Punishment = (TextView)itemView.findViewById(R.id.punishment);
        }
    }
    void editPunishDialShow(final int position) {

        final Dialog PunishEditDialog = new Dialog(mcontext);
        PunishEditDialog.setCancelable(true);
        PunishEditDialog.setContentView(R.layout.add_reward_dialog);

        final TextInputLayout tiName = (TextInputLayout)  PunishEditDialog.findViewById(R.id.rewardname_ti);
        final TextInputLayout tiPunishDay = (TextInputLayout)  PunishEditDialog.findViewById(R.id.reward_day_ti);

        final TextInputEditText editTextName = (TextInputEditText)  PunishEditDialog.findViewById(R.id.reward_name_e);
        final TextInputEditText editTextPunishDay = (TextInputEditText)  PunishEditDialog.findViewById(R.id.reward_day_e);

        Button save = (Button) PunishEditDialog.findViewById(R.id.reward_save);
        Button cancel = (Button)  PunishEditDialog.findViewById(R.id.reward_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().length() < 1 && editTextPunishDay.getText().toString().length() < 1) {
                    tiName.requestFocus();
                    tiName.requestFocus();
                    tiName.setError(mcontext.getString(R.string.error_message_name));
                    tiPunishDay.setError(mcontext.getString(R.string.error_message_goal));

                } else if (editTextName.getText().toString().length() < 1 || editTextPunishDay.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError(mcontext.getString(R.string.error_message_name));
                        tiPunishDay.clearFocus();
                        tiPunishDay.setError(null);
                    } else {
                        tiPunishDay.requestFocus();
                        tiPunishDay.setError(mcontext.getString(R.string.error_message_goal));
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                    tiPunishDay.clearFocus();
                    tiPunishDay.setError(null);
                    new PunishmentDao().update(gdb,allData.get(position).getId(), editTextName.getText().toString(), Integer.parseInt(editTextPunishDay.getText().toString()));


                    Toast.makeText(mcontext, "Kaydedildi", Toast.LENGTH_SHORT);


                    Fragment fragment = new PunishFragment();

                    ((AppCompatActivity)mcontext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .detach(fragment)
                            .attach(fragment)
                            .commit();

                    PunishEditDialog.dismiss();

                }

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PunishEditDialog.dismiss();
            }
        });

        PunishEditDialog.show();
    }
}
