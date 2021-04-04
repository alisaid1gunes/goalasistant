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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final private static int TYPE_REWARDS = 0;
    private Context ctx;
    GoalAsisstantDB gdb ;
    ArrayList<Reward> allData;
    int rewardCount;
    public RewardAdapter(Context mctx) {
        this.ctx = mctx;
        gdb = new GoalAsisstantDB(ctx);
        allData = new RewardDao().getAllInfo(gdb);
        rewardCount = allData.size();
    }
public class  RewardViewHolder extends RecyclerView.ViewHolder{
        private CardView CardReward;
        private ProgressBar RewardProgress;
        private TextView ProgressCounter;
        private TextView Reward;
        private ImageView MenuImgReward;
    public RewardViewHolder(@NonNull View itemView) {
        super(itemView);
        CardReward = (CardView)itemView.findViewById(R.id.card_reward);
        RewardProgress  = (ProgressBar)itemView.findViewById(R.id.reward_progress);
        ProgressCounter = (TextView)itemView.findViewById(R.id.reward_counter);
        MenuImgReward = (ImageView)itemView.findViewById(R.id.menu_img_reward);
        Reward = (TextView)itemView.findViewById(R.id.reward);
    }
}
    @Override
    public int getItemCount() {
        return rewardCount;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
switch (i){
    case TYPE_REWARDS:
        RewardViewHolder rewardViewHolder = (RewardViewHolder)viewHolder;
        if(rewardCount!=0){
            for(int j = 0; j<rewardCount; j++){
                rewardViewHolder.ProgressCounter.setText("2/"+allData.get(j).getRewardopday());
                //üstteki kısımda bi algıritma eklenecek
                rewardViewHolder.Reward.setText(allData.get(j).getName());
                rewardViewHolder.RewardProgress.setMax(allData.get(j).getRewardopday());
                rewardViewHolder.RewardProgress.setProgress(2);
                //üstteki kısımda bi algıritma eklenecek
                rewardViewHolder.MenuImgReward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(ctx, v);
                        popupMenu.getMenu().add("Edit");
                        popupMenu.getMenu().add("Delete");

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
}
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
       RecyclerView.ViewHolder rv = null;
       switch (i){
           case TYPE_REWARDS:
               view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_card_design,viewGroup,false);
               rv = new RewardViewHolder(view);
               break;
       }

        return rv;
    }

    @Override
    public int getItemViewType(int position) {
        int ReturnTypeReward = 0;

        switch (position){
            case 0:
                ReturnTypeReward = TYPE_REWARDS;
                break;
        }

        return ReturnTypeReward;
    }


}