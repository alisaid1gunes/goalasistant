package com.alisaidgunes.goalassistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankushgrover.hourglass.Hourglass;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class PromodoroFragment extends Fragment {
    MediaPlayer mediaPlayer ;
    final String[] musicList = {"None","Clock Ticking","Rain",
            "Cicada Sound","Fire Crackling",
            "Rain And Wild","River","Rural Village Morning","Forest Birds Chirping","Forest Birds Chirping 2"};
    int checkedItem = 1;
    FloatingActionButton play;
    FloatingActionButton stop;
    FloatingActionButton next;
    ImageView music;
    ImageView pomim1;
    ImageView pomim2;
    ImageView pomim3;
    ImageView pomim4;
    TextView pomtext;
    CircularProgressBar pomprogress;
    Hourglass hourglass25;
    Hourglass hourglass20;
    Hourglass hourglass5;
    boolean playstate = false;
    int recession = 0;
    int pomimcount = 1;
    int state = 0;
    int progress = 1500000;

    //state 0 = ilk durum 25dk geri sayım
    //state 1 = 5 dk ilk mola
    //state 2 = 25dk ikinci set
    //state 3 = 5dk ikinci mola
    //state 4 = 25dk üçüncü set
    //state 5 = 5dk üçüncü mola
    //state 6 = 25dk son set
    //state 7 = 20dk uzun mola son durum


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promodoro_fragment_design, container, false);
        play = (FloatingActionButton) rootView.findViewById(R.id.pom_play);
        stop = (FloatingActionButton) rootView.findViewById(R.id.pom_stop);
        next = (FloatingActionButton) rootView.findViewById(R.id.pom_next);
        music = (ImageView) rootView.findViewById(R.id.imageview_music);
        pomim1 = (ImageView) rootView.findViewById(R.id.pomim1);
        pomim2 = (ImageView) rootView.findViewById(R.id.pomim2);
        pomim3 = (ImageView) rootView.findViewById(R.id.pomim3);
        pomim4 = (ImageView) rootView.findViewById(R.id.pomim4);
        pomtext = (TextView) rootView.findViewById(R.id.pomtext);
        pomprogress = (CircularProgressBar) rootView.findViewById(R.id.pom_progress_bar);
        pomprogress.setAnimateProgress(true);
        String input = "denenem";
        Intent serviceIntent = new Intent(getActivity(), CountdownService.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService( getActivity(), serviceIntent);





        //    Intent serviceIntent = new Intent(this, ExampleService.class);
          //  stopService(serviceIntent);


        hourglass25 = new Hourglass(1500000, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {

                pomprogress.setProgress(progress = progress - 1000);

                pomtext.setText(String.format(Locale.getDefault(), "%02d:%02d", (int) (timeRemaining / 1000) / 60, (int) (timeRemaining / 1000) % 60));
            }

            @Override
            public void onTimerFinish() {
                // Timer finished




                    Toast.makeText(getActivity(),String.valueOf(state),Toast.LENGTH_LONG).show();





                state++;
                switch (pomimcount) {
                    case 1:
                        pomim1.setImageResource(R.drawable.ic_active_pom);
                        pomimcount++;

                        pomim1.setAlpha((float) 0.75);

                        break;
                    case 2:
                        pomim2.setImageResource(R.drawable.ic_active_pom);
                        pomim2.setAlpha((float) 0.75);

                        pomimcount++;
                        break;
                    case 3:
                        pomim3.setImageResource(R.drawable.ic_active_pom);

                        pomim3.setAlpha((float) 0.75);

                        pomimcount++;
                        break;
                    case 4:
                        pomim4.setImageResource(R.drawable.ic_active_pom);
                        pomim4.setAlpha((float) 0.75);





                        pomimcount = 1;
                        break;
                }


            }
        };
        hourglass20 = new Hourglass(1200000, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {
                pomprogress.setProgress(progress = progress - 1000);
                pomtext.setText(String.format(Locale.getDefault(), "%02d:%02d", (int) (timeRemaining / 1000) / 60, (int) (timeRemaining / 1000) % 60));
            }

            @Override
            public void onTimerFinish() {
                state = 0;


                pomim1.setImageResource(R.drawable.ic_passive_pom);
                pomim2.setImageResource(R.drawable.ic_passive_pom);
                pomim3.setImageResource(R.drawable.ic_passive_pom);
                pomim4.setImageResource(R.drawable.ic_passive_pom);
                pomim1.setAlpha((float) 0.5);
                pomim2.setAlpha((float) 0.5);
                pomim3.setAlpha((float) 0.5);
                pomim4.setAlpha((float) 0.5);



            }
        };
        hourglass5 = new Hourglass(300000, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {
                pomprogress.setProgress(progress = progress - 1000);
                pomtext.setText(String.format(Locale.getDefault(), "%02d:%02d", (int) (timeRemaining / 1000) / 60, (int) (timeRemaining / 1000) % 60));
            }

            @Override
            public void onTimerFinish() {
                state++;



            }
        };
        mediaPlayer = mediaPlayer.create(getActivity(), R.raw.clock_ticking);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        while (hourglass25.isRunning()||hourglass5.isRunning()||hourglass20.isRunning()){
            switch (checkedItem){
                case 1:
                    Context context;

                    break;



            }

        }
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"music",Toast.LENGTH_LONG).show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playstate == true) {
                    Toast.makeText(getActivity(), "Pause", Toast.LENGTH_SHORT).show();
                    play.setImageResource(R.drawable.ic_play);
                    mediaPlayer.stop();;
                    if (state % 2 == 0) {
                        hourglass25.pauseTimer();
                    } else if (state == 7) {
                        hourglass20.pauseTimer();
                    } else {
                        hourglass5.pauseTimer();
                    }
                    playstate = false;
                } else {
                    play.setImageResource(R.drawable.ic_pause);
                    Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
                    if (state % 2 == 0) {
                        hourglass25.resumeTimer();
                    } else if (state == 7) {
                        hourglass20.resumeTimer();
                    } else {
                        hourglass5.resumeTimer();
                    }
                    playstate = true;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Next", Toast.LENGTH_SHORT).show();
                if (state % 2 == 0) {
                    hourglass25.stopTimer();
                } else if (state == 7) {
                    hourglass20.stopTimer();
                } else if (state != 7 && state % 2 != 0) {
                    hourglass5.stopTimer();
                }


                playstate = true;
                play.setImageResource(R.drawable.ic_pause);
                if (state % 2 == 0) {
                    pomprogress.setProgress(progress = 1500000);
                    pomprogress.setMaximum(1500000);

                    pomtext.setText("25:00");
                    hourglass25.startTimer();
                } else if (state == 7) {
                    pomprogress.setProgress(progress = 1200000);
                    pomprogress.setMaximum(1200000);

                    pomtext.setText("20:00");
                    hourglass20.startTimer();
                } else if (state != 7 && state % 2 != 0) {
                    pomprogress.setProgress(progress = 300000);
                    pomprogress.setMaximum(300000);

                    pomtext.setText("05:00");
                    hourglass5.startTimer();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pause", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (state % 2 == 0) {
                            hourglass25.stopTimer();
                        } else if (state == 7) {
                            hourglass20.stopTimer();
                        } else if (state != 7 && state % 2 != 0) {
                            hourglass5.stopTimer();
                        }

                        pomprogress.setProgress(progress = 1500000);
                        pomprogress.setMaximum(1500000);
                        pomtext.setText("25:00");
                        playstate = false;
                        play.setImageResource(R.drawable.ic_play);
                        state = 0;
                        pomim1.setImageResource(R.drawable.ic_passive_pom);
                        pomim2.setImageResource(R.drawable.ic_passive_pom);
                        pomim3.setImageResource(R.drawable.ic_passive_pom);
                        pomim4.setImageResource(R.drawable.ic_passive_pom);
                        pomim1.setAlpha((float) 0.5);
                        pomim2.setAlpha((float) 0.5);
                        pomim3.setAlpha((float) 0.5);
                        pomim4.setAlpha((float) 0.5);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("Pomodoro sonlandırılsın mı ?");
                builder.create().show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // mediaPlayer = mediaPlayer.create(getActivity(), R.raw.clock_ticking);
        //mediaPlayer.start();
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"music",Toast.LENGTH_LONG).show();

                AlertDialog.Builder musicBuilder = new AlertDialog.Builder(getActivity());
                musicBuilder.setSingleChoiceItems(musicList, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),musicList[which],Toast.LENGTH_LONG).show();
                        checkedItem = which;
                    }
                });
                musicBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });
                musicBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                musicBuilder.setTitle("Choose a sound");
                musicBuilder.create().show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playstate == true) {
                    Toast.makeText(getActivity(), "Pause", Toast.LENGTH_SHORT).show();
                    play.setImageResource(R.drawable.ic_play);
                    if (state % 2 == 0) {
                        hourglass25.pauseTimer();
                    } else if (state == 7) {
                        hourglass20.pauseTimer();
                    } else {
                        hourglass5.pauseTimer();
                    }
                    playstate = false;
                } else {
                    play.setImageResource(R.drawable.ic_pause);
                    Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
                    if (state % 2 == 0) {
                        hourglass25.resumeTimer();
                    } else if (state == 7) {
                        hourglass20.resumeTimer();
                    } else {
                        hourglass5.resumeTimer();
                    }
                    playstate = true;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Next", Toast.LENGTH_SHORT).show();
                if (state % 2 == 0) {
                    hourglass25.stopTimer();
                } else if (state == 7) {
                    hourglass20.stopTimer();
                } else if (state != 7 && state % 2 != 0) {
                    hourglass5.stopTimer();
                }


                playstate = true;
                play.setImageResource(R.drawable.ic_pause);
                if (state % 2 == 0) {
                    pomprogress.setProgress(progress = 1500000);
                    pomprogress.setMaximum(1500000);

                    pomtext.setText("25:00");
                    hourglass25.startTimer();
                } else if (state == 7) {
                    pomprogress.setProgress(progress = 1200000);
                    pomprogress.setMaximum(1200000);

                    pomtext.setText("20:00");
                    hourglass20.startTimer();
                } else if (state != 7 && state % 2 != 0) {
                    pomprogress.setProgress(progress = 300000);
                    pomprogress.setMaximum(300000);

                    pomtext.setText("05:00");
                    hourglass5.startTimer();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pause", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (state % 2 == 0) {
                            hourglass25.stopTimer();
                        } else if (state == 7) {
                            hourglass20.stopTimer();
                        } else if (state != 7 && state % 2 != 0) {
                            hourglass5.stopTimer();
                        }

                        pomprogress.setProgress(progress = 1500000);
                        pomprogress.setMaximum(1500000);
                        pomtext.setText("25:00");
                        playstate = false;
                        play.setImageResource(R.drawable.ic_play);
                        state = 0;
                        pomim1.setImageResource(R.drawable.ic_passive_pom);
                        pomim2.setImageResource(R.drawable.ic_passive_pom);
                        pomim3.setImageResource(R.drawable.ic_passive_pom);
                        pomim4.setImageResource(R.drawable.ic_passive_pom);
                        pomim1.setAlpha((float) 0.5);
                        pomim2.setAlpha((float) 0.5);
                        pomim3.setAlpha((float) 0.5);
                        pomim4.setAlpha((float) 0.5);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("Pomodoro sonlandırılsın mı ?");
                builder.create().show();
            }
        });
    }

}