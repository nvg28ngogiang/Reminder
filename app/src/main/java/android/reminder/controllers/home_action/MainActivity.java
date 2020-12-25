package android.reminder.controllers.home_action;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.reminder.controllers.R;
import android.reminder.controllers.db_interaction.NotificationLab;
import android.reminder.controllers.new_action.NewActivity;
import android.reminder.controllers.view_action.NotificationAdapter;
import android.reminder.controllers.view_action.OnHomeNotificationAdapter;
import android.reminder.controllers.view_action.RecyclerViewControl;
import android.reminder.controllers.view_action.ViewActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer = null;
    private Toolbar toolbar = null;
    private NavigationView navigation = null;
    private DatePicker datePicker = null;
    private NotificationLab lab;
    private RecyclerViewControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        init();
        setting();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.main_toolbar);
        navigation = findViewById(R.id.navigation);
        datePicker = findViewById(R.id.date_picker);
        lab = NotificationLab.get(getApplicationContext());

        final DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formater);
        NotificationAdapter adapter = new OnHomeNotificationAdapter(lab.getNotificationsOn(today), getApplicationContext());
        control = new RecyclerViewControl((RecyclerView) findViewById(R.id.on_home_recycler_view), adapter);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Because monthOfYear is zero-based, so you must add 1 to it
                String date = LocalDate.of(year, monthOfYear + 1, dayOfMonth).format(formater);
                control.setNotifications(lab.getNotificationsOn(date));
            }
        });
    }

    private void setting() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawers();
                if (item.getItemId() == R.id.option_new) {
                    onClickNew();
                } else if (item.getItemId() == R.id.option_view_current) {
                    onClickViewOption("current");
                } else if (item.getItemId() == R.id.option_view_history) {
                    onClickViewOption("history");
                }
                return false;
            }
        });
    }

    public void onClickNew() {
        Intent intentNew = new Intent(MainActivity.this, NewActivity.class);
        startActivity(intentNew);
    }

    public void onClickViewOption(String viewOption) {
        Intent intentView = new Intent(MainActivity.this, ViewActivity.class);
        intentView.putExtra("option", viewOption);
        startActivity(intentView);
    }

}