package com.alisaidgunes.goalassistant;

import android.app.Dialog;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Fragment fragment;
    private DrawerLayout drawer;
    public BottomNavigationView bottomNavigationView;
    MainPageFragment mainPageFragment;
    NotificationSettingsFragment notificationSettingsFragment;
    ProfileFragment profileFragment;
    PurchaseFragment purchaseFragment;
    ThemeSettingsFragment themeSettingsFragment;
    private  boolean isVisible;
    private GoalAsisstantDB gdb = new GoalAsisstantDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonAndGoalDao personAndGoalDao = new PersonAndGoalDao();


        if(personAndGoalDao.count(gdb)==0){
            RegDialShow();
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:

                        fragment = new MainPageFragment();
                        break;
                    case R.id.action_reward:
                        fragment = new RewardsFragment();
                        break;
                    case R.id.action_punishments:
                        fragment = new PunishFragment();
                        break;
                    case R.id.action_promodoro:
                        fragment = new PromodoroFragment();
                        break;


                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .commit();
                    }
                }, 200);

                return true;

            }
        });
        fragment = new MainPageFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        //ilk açılış Fragment'ın yüklenmesi

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Toolbar üzerine toggle menü buttonu getirir.

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        //Navigation Drawer için başlık tasarımı yüklenir.
        //Elde edilen view ile başlık üzerindeki görsel nesnelere erişilebilir.

        navigationView.setNavigationItemSelectedListener(this);

        drawer.closeDrawer(GravityCompat.START);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
    }

    void RegDialShow() {
        final Dialog RegisterDialog = new Dialog(this);
        RegisterDialog.setCancelable(false);
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
                    tiName.setError(getString(R.string.error_message_name));
                    tiGoal.setError(getString(R.string.error_message_goal));

                } else if (editTextName.getText().toString().length() < 1 || editTextGoal.getText().toString().length() < 1) {
                    if (editTextName.getText().toString().length() < 1) {
                        tiName.requestFocus();
                        tiName.setError(getString(R.string.error_message_name));
                        tiGoal.clearFocus();
                        tiGoal.setError(null);
                    } else {
                        tiGoal.requestFocus();
                        tiGoal.setError(getString(R.string.error_message_goal));
                        tiName.clearFocus();
                        tiName.setError(null);
                    }
                } else {
                    tiName.clearFocus();
                    tiName.setError(null);
                    tiGoal.clearFocus();
                    tiGoal.setError(null);
                    new PersonAndGoalDao().insert(gdb,editTextName.getText().toString(),editTextGoal.getText().toString());
                    fragment = new MainPageFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();

                    RegisterDialog.dismiss();
                }

            }
        });
        RegisterDialog.show();
    }



    @Override
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            //Navigation Drawer Açık ise geri tuşu Navigation Drawer'ı kapatır.
        } else {
            if (fragment instanceof MainPageFragment) {
                super.onBackPressed();
            } else {
                fragment = new MainPageFragment();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager()

                                .beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .commit();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        bottomNavigationView.setSelectedItemId(R.id.action_home);
                    }
                }, 200);

            }

            //Navigation Drawer kapalı ise geri tuşu uygulamayı bitirir.
           /* Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
*/
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        //Hangi item'a tıklanıldıysa o item'ın id'si alınır.
        if (id == R.id.action_notification_settings_) {

            fragment = new NotificationSettingsFragment();
            bottomNavigationView.setVisibility(View.GONE);


        }

        if (id == R.id.action_profile) {

            fragment = new ProfileFragment();
            bottomNavigationView.setVisibility(View.GONE);

        }

        if (id == R.id.action_purchase) {

            fragment = new PurchaseFragment();
            bottomNavigationView.setVisibility(View.GONE);


        }
        if (id == R.id.action_theme_settings) {
            fragment = new ThemeSettingsFragment();

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)

                        .commit();

                bottomNavigationView.setVisibility(View.GONE);

            }
        }, 200);

        //seçilen fragmentı gösterir.

        drawer.closeDrawer(GravityCompat.START);
        //Navigation Drawer üzerinde bir item'a tıklanıldıktan sonra Navigation Drawer kapanır.

        return true;
    }
}