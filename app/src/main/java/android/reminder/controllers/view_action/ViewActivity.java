package android.reminder.controllers.view_action;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.reminder.controllers.R;
import android.reminder.controllers.db_interaction.NotificationLab;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewActivity extends AppCompatActivity {

    private Toolbar toolbar = null;
    private NotificationLab lab = null;
    private RecyclerViewControl control = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        String viewOption = intent.getStringExtra("option");

        toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        lab = NotificationLab.get(getApplicationContext());
        NotificationAdapter adapter;

        if (viewOption.equals("current")) {
            adapter = new OnViewNotificationAdapter(lab.getCurrentNotifications(), getApplicationContext());
            getSupportActionBar().setTitle("View Current Notifications");
        } else {
            adapter = new OnViewNotificationAdapter(lab.getHistoryNotifications(), getApplicationContext());
            getSupportActionBar().setTitle("View History Notifications");
        }

        RecyclerViewControl control = new RecyclerViewControl((RecyclerView) findViewById(R.id.on_view_recycler_view), adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_all_item) {
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplication());
                    manager.cancelAll();
                    Toast.makeText(getApplication(), "Delete all", Toast.LENGTH_LONG).show();
                } else if (item.getItemId() == R.id.delete_item)  {

                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}