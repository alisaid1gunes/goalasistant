package com.alisaidgunes.goalassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final private static int TYPE_DAYS = 0;
    final private static int TYPE_AD = 1;
    final private static int TYPE_QUOTE = 2;
    final private static int TYPE_CHART = 3;
    final private static int TYPE_CLUE = 6;
    private Context ctx;
    GoalAsisstantDB gdb ;
    PersonAndGoal personAndGoal;
    DateTrackerDao dateTrackerDao;
    DateTracker dateTracker;
    int dateTrackerCount ;
    //ArrayList<PointAnDate> receivedInfo ;
    public MyAdapter(Context mc) {
        this.ctx = mc;
        gdb = new GoalAsisstantDB(mc);
        personAndGoal = new PersonAndGoalDao().getInfo(gdb);
        int dateTrackerCount = new DateTrackerDao().count(gdb);
        //this.pointAnDateCount =  new PointAnDateDao().count(gdb);
        //this.receivedInfo = new PointAnDateDao().getAllInfo(gdb);
    }



    public void RegDialShow() {
        final Dialog RegisterDialog = new Dialog(ctx);
        RegisterDialog.setCancelable(true);
        RegisterDialog.setContentView(R.layout.opening_dialog_design);

        final TextInputLayout tiName = (TextInputLayout) RegisterDialog.findViewById(R.id.opening_name_ti);
        final TextInputLayout tiGoal = (TextInputLayout) RegisterDialog.findViewById(R.id.opening_goal_ti);

        final TextInputEditText editTextName = (TextInputEditText) RegisterDialog.findViewById(R.id.opening_name_edittext);
        final TextInputEditText editTextGoal = (TextInputEditText) RegisterDialog.findViewById(R.id.opening_goal_edittext);

        Button save = (Button) RegisterDialog.findViewById(R.id.opening_save_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().length() < 1 && editTextGoal.getText().toString().length() < 1) {
                    tiName.requestFocus();
                    tiGoal.requestFocus();
                    tiName.setError("Ad Alanı Boş Bırakılamaz");
                    tiGoal.setError("Hedef Alanı Boş Bırakılamaz");

                } else if (editTextName.getText().toString().length() < 1 || editTextGoal.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError("Ad Alanı Boş Bırakılamaz");
                        tiGoal.clearFocus();
                        tiGoal.setError(null);
                    } else {
                        tiGoal.requestFocus();
                        tiGoal.setError("Hedef Alanı Boş Bırakılamaz");
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                    tiGoal.clearFocus();
                    tiGoal.setError(null);
                    new PersonAndGoalDao().update(gdb, editTextName.getText().toString(), editTextGoal.getText().toString());
                    Intent i = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(i);


                    RegisterDialog.dismiss();
                }

            }
        });
        RegisterDialog.show();
    }

    public class ClueViewHolder extends RecyclerView.ViewHolder {
        private CardView cardclue;
        private TextView clue;
        private TextView titleclue;

        public ClueViewHolder(@NonNull View itemView) {
            super(itemView);
            cardclue = (CardView) itemView.findViewById(R.id.cluecard);
            clue = (TextView) itemView.findViewById(R.id.clue);
            titleclue = (TextView) itemView.findViewById(R.id.titleclue);
        }
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder {
        private CardView cardquote;
        private TextView quote;
        private TextView whosays;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);

            cardquote = (CardView) itemView.findViewById(R.id.quotecard);
            quote = (TextView) itemView.findViewById(R.id.quote);
            whosays = (TextView) itemView.findViewById(R.id.whosays);

        }
    }

    public class DaysViewHolder extends RecyclerView.ViewHolder {
        private CardView cardDays;
        //private CalendarView calendarView;
        private ImageView edit;
        private TextView imagineTextview, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15, day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);

            cardDays = (CardView) itemView.findViewById(R.id.card_goalboard);
            edit = (ImageView) itemView.findViewById(R.id.editimagegoal);
            imagineTextview = (TextView) itemView.findViewById(R.id.imaginetextview);
            day1 = (TextView) itemView.findViewById(R.id.day1);
            day2 = (TextView) itemView.findViewById(R.id.day2);
            day3 = (TextView) itemView.findViewById(R.id.day3);
            day4 = (TextView) itemView.findViewById(R.id.day4);
            day5 = (TextView) itemView.findViewById(R.id.day5);
            day6 = (TextView) itemView.findViewById(R.id.day6);
            day7 = (TextView) itemView.findViewById(R.id.day7);
            day8 = (TextView) itemView.findViewById(R.id.day8);
            day9 = (TextView) itemView.findViewById(R.id.day9);
            day10 = (TextView) itemView.findViewById(R.id.day10);
            day11 = (TextView) itemView.findViewById(R.id.day11);
            day12 = (TextView) itemView.findViewById(R.id.day12);
            day13 = (TextView) itemView.findViewById(R.id.day13);
            day14 = (TextView) itemView.findViewById(R.id.day14);
            day15 = (TextView) itemView.findViewById(R.id.day15);
            day16 = (TextView) itemView.findViewById(R.id.day16);
            day17 = (TextView) itemView.findViewById(R.id.day17);
            day18 = (TextView) itemView.findViewById(R.id.day18);
            day19 = (TextView) itemView.findViewById(R.id.day19);
            day20 = (TextView) itemView.findViewById(R.id.day20);
            day21 = (TextView) itemView.findViewById(R.id.day21);
            day22 = (TextView) itemView.findViewById(R.id.day22);
            day23 = (TextView) itemView.findViewById(R.id.day23);
            day24 = (TextView) itemView.findViewById(R.id.day24);
            day25 = (TextView) itemView.findViewById(R.id.day25);
            day26 = (TextView) itemView.findViewById(R.id.day26);
            day27 = (TextView) itemView.findViewById(R.id.day27);
            day28 = (TextView) itemView.findViewById(R.id.day28);
            day29 = (TextView) itemView.findViewById(R.id.day29);
            day30 = (TextView) itemView.findViewById(R.id.day30);
        }
        /*

         */
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        private CardView cardAd;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            cardAd = (CardView) itemView.findViewById(R.id.ads);
        }
    }

    public class ChartViewHolder extends RecyclerView.ViewHolder {
        private CardView cardChart;
        private GraphView graph;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            cardChart = (CardView) itemView.findViewById(R.id.chart_card);
            graph = (GraphView) itemView.findViewById(R.id.graph);
        }
    }

    @Override
    public int getItemViewType(int position) {

        int ReturnType = 0;


        switch (position) {
            case 0:
                ReturnType = TYPE_DAYS;
                break;
            case 1:
                ReturnType = TYPE_AD;
                break;
            case 2:
                ReturnType = TYPE_QUOTE;
                break;
            case 3:
                ReturnType = TYPE_CHART;
                break;

            case 4:
                ReturnType = TYPE_CLUE;
        }


        return ReturnType;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder rv = null;

        switch (viewType) {
            case TYPE_DAYS:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goalboard_card_design, parent, false);
                rv = new DaysViewHolder(view);
                break;
            case TYPE_AD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ad_design, parent, false);
                rv = new AdViewHolder(view);
                break;
            case TYPE_QUOTE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_quote_design, parent, false);
                rv = new QuotesViewHolder(view);
                break;
            case TYPE_CHART:
                //goalAsisstantDB = new GoalAsisstantDB(parent.getContext());
                //personAndGoal = new PersonAndGoalDao().getInfo(goalAsisstantDB);
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_card_design, parent, false);
                rv = new ChartViewHolder(view);
                break;


            case TYPE_CLUE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_clue_design, parent, false);
                rv = new ClueViewHolder(view);
                break;
        }
        return rv;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DateTrackerDao dateTrackerDao = new DateTrackerDao();
        switch (getItemViewType(position)) {
            case TYPE_DAYS:

                final DaysViewHolder dv = (DaysViewHolder) holder;
                dv.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(ctx, v);
                        popupMenu.getMenu().add("Edit");
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                RegDialShow();
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
                if (personAndGoal.getGoal() != null) {
                    dv.imagineTextview.setText("Benim Hedefim : " + personAndGoal.getGoal());
                }
                TextView[] textviews;
                textviews = new TextView[]{dv.day1, dv.day2, dv.day3, dv.day4, dv.day5, dv.day6, dv.day7, dv.day8, dv.day9, dv.day10, dv.day11, dv.day12, dv.day13, dv.day14, dv.day15, dv.day16, dv.day17, dv.day18, dv.day19, dv.day20, dv.day21, dv.day22, dv.day23, dv.day24, dv.day25, dv.day26, dv.day27, dv.day28, dv.day29, dv.day30};



                ArrayList<DateTracker> dateTrackerArrayList;
                if (dateTrackerCount != 0 && dateTrackerCount <= 30) {
                    dateTrackerArrayList = new DateTrackerDao().getAllInfo(gdb);
                    for (int i = 0; i < 30; i++) {
                        if (dateTrackerArrayList.get(i).getGoalisokay() == 1) {
                            textviews[i].setBackgroundResource(R.mipmap.ic_circledayok);
                            textviews[i].setText("");
                        } else {
                            textviews[i].setBackgroundResource(R.mipmap.ic_circleday);
                            textviews[i].setText( String.valueOf(i+1));
                        }
                    }
                } else if (dateTrackerCount == 0) {
                    for (int j = 0; j < 30; j++) {
                        textviews[j].setBackgroundResource(R.mipmap.ic_circleday);
                        textviews[j].setText( String.valueOf(j+1));
                    }
                }


                break;
            case TYPE_AD:
                AdViewHolder av = (AdViewHolder) holder;
                break;
            case TYPE_QUOTE:
                QuotesViewHolder qv = (QuotesViewHolder) holder;
                break;
            case TYPE_CHART:
                ChartViewHolder cv = (ChartViewHolder) holder;
                int i = 0;
               if(dateTrackerCount != 0){
                   LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(getData());
                   //   LineGraphSeries<DataPoint> series = new LineGraphSeries<>
                   //         (new DataPoint[]{ new DataPoint(1, 1)

                   //       });
                   series.setColor(Color.argb(255, 233, 30, 99));
                   cv.graph.addSeries(series);


               }
                cv.graph.getViewport().setScalable(true);
                cv.graph.getViewport().setXAxisBoundsManual(true);
                cv.graph.getGridLabelRenderer().setLabelsSpace(1);
                cv.graph.getGridLabelRenderer().setPadding(70);

                cv.graph.getViewport().setMaxX(30);





                break;

            case TYPE_CLUE:
                ClueViewHolder clueViewHolder = (ClueViewHolder) holder;
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
      DataPoint[] getData(){
            DataPoint[] dp  = new DataPoint[30];
            ArrayList<DateTracker> dateTrackerArrayList = new DateTrackerDao().getAllInfo(gdb);
            if(dateTrackerCount!=0){
                for(int k = 0; k<dateTrackerCount; k++){

                    dp[k] = new DataPoint(k+1, dateTrackerArrayList.get(k).getPoint());
                }
            }

            return dp;
    }
}
