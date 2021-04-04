package com.alisaidgunes.goalassistant;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class PunishmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    final private static int TYPE_PUNISHMENT = 0;
   Punishment punishment ;
    ArrayList<Punishment> allData;
    GoalAsisstantDB gdb ;
    int punishmentCount;

    public PunishmentAdapter(Context mctx) {
        this.ctx = mctx;
        gdb = new GoalAsisstantDB(ctx);
        punishmentCount = new PunishmentDao().count(gdb);
        allData= new PunishmentDao().getAllInfo(gdb);
    }
public class  PunishmentViewHolder extends RecyclerView.ViewHolder{
    private CardView CardPunishment;
    private ProgressBar PunishmentProgress;
    private TextView ProgressCounter;
    private TextView Punishment;
    private ImageView MenuImgPunishment;
    public PunishmentViewHolder(@NonNull View itemView) {
        super(itemView);

            CardPunishment = (CardView)itemView.findViewById(R.id.card_punishment);
            PunishmentProgress  = (ProgressBar)itemView.findViewById(R.id.punishment_progress);
            ProgressCounter = (TextView)itemView.findViewById(R.id.punishment_counter);
            MenuImgPunishment = (ImageView)itemView.findViewById(R.id.menu_img_punishment);
            Punishment = (TextView)itemView.findViewById(R.id.punishment);


    }
}
    @Override
    public int getItemCount() {
        return punishmentCount;
    }

    @Override
    public int getItemViewType(int position) {
        int ReturnTypePunishment = 0;

        switch (position){
            case 0:
                ReturnTypePunishment  = TYPE_PUNISHMENT;
                break;
        }

        return ReturnTypePunishment ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        RecyclerView.ViewHolder rv = null;
        switch (i){
            case TYPE_PUNISHMENT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.punishment_card_design,viewGroup,false);
                rv = new PunishmentViewHolder(view);

                break;
        }

        return rv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)){
            case TYPE_PUNISHMENT:
                PunishmentViewHolder pv = (PunishmentViewHolder)viewHolder;
                if(punishmentCount!=0){
                    for(int j = 0; j<punishmentCount; j++){
                        pv.ProgressCounter.setText("2/"+allData.get(j).getPunishmentopday());
                        //üstteki kısımda bi algıritma eklenecek
                        pv.Punishment.setText(allData.get(j).getName());
                        pv.PunishmentProgress.setMax(allData.get(j).getPunishmentopday());
                        pv.PunishmentProgress.setProgress(2);
                        //üstteki kısımda bi algıritma eklenecek
                        pv.MenuImgPunishment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PopupMenu popupMenu = new PopupMenu(ctx, v);
                                popupMenu.getMenu().add("Edit");
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        //güncelleme eklenecek
                                        return true;
                                    }
                                });
                                popupMenu.show();


                            }
                        });
                    }

                }


                break;
        }
    }
}
