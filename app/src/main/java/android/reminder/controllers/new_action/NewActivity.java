package android.reminder.controllers.new_action;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.reminder.controllers.R;
import android.reminder.controllers.db_interaction.NotificationLab;
import android.reminder.controllers.home_action.MainActivity;
import android.reminder.model.NotificationDetail;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewActivity extends AppCompatActivity {
    private RadioButton checked = null;
    private EditText dateEdit = null;
    private EditText timeEdit = null;
    private EditText contentText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        checked = findViewById(R.id.Low);
        dateEdit = findViewById(R.id.dateEditText);
        timeEdit = findViewById(R.id.timeEditText);
        contentText = findViewById(R.id.content);

        createChannel("UrgentChannel", "urgentId", NotificationManager.IMPORTANCE_MAX);
        createChannel("HighChannel", "highId", NotificationManager.IMPORTANCE_DEFAULT);
        createChannel("MediumChannel", "mediumId", NotificationManager.IMPORTANCE_LOW);
        createChannel("LowChannel", "lowId", NotificationManager.IMPORTANCE_MIN);
    }

    private void createChannel(String name, String channelId, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onClickPickDateButton(View view) {
        DatePickerFragment dateFragment = new DatePickerFragment(dateEdit);
        dateFragment.show(getSupportFragmentManager(), "Date Fragment");
    }

    public void onClickPickTimeButton(View view) {
        TimePickerFragment timeFragment = new TimePickerFragment(timeEdit);
        timeFragment.show(getSupportFragmentManager(), "Time Fragment");
    }

    public void onClickRadioButton(View view) {
        checked = (RadioButton) view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickCreateButton(View view) {
        String notificationContent = contentText.getText().toString();

        /*Validate notification content : must not be empty*/
        if (notificationContent.isEmpty()) {
            Toast.makeText(this, R.string.emptyContent, Toast.LENGTH_LONG).show();
            return;
        }

        /*validate the picked date time: must be not empty and after the current time*/
        if (dateEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.emptyDate, Toast.LENGTH_LONG).show();
            return;
        }

        if (timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.emptyTime, Toast.LENGTH_LONG).show();
            return;
        }

        String pickedInString = dateEdit.getText() + " " + timeEdit.getText();

        NotificationLab lab = NotificationLab.get(getApplicationContext());
        lab.insertNotification(new NotificationDetail(pickedInString, notificationContent, 1));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime picked = LocalDateTime.parse(pickedInString, formatter);
        if (!picked.isAfter(LocalDateTime.now())) {
            Toast.makeText(this, R.string.invalidDateTime, Toast.LENGTH_LONG).show();
            return;
        }

        long triggerAtMillis = picked.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        int uniqueInt = (int) (triggerAtMillis/60000);

        Intent intent = new Intent(getApplication(), AlertReceiver.class);
        intent.putExtra("content", notificationContent);
        intent.putExtra("channelId", findIdChannel());
        intent.putExtra("notificationId", uniqueInt);

        PendingIntent operation = PendingIntent.getBroadcast(getApplication() , uniqueInt, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);

        // After clicking on create button, return the homepage
        onBackPressed();
    }

    private String findIdChannel() {
        switch (checked.getId()) {
            case R.id.Urgent:
                return "urgentId";
            case R.id.High:
                return "highId";
            case R.id.Medium:
                return "mediumId";
            case R.id.Low:
                return "lowId";
             /*Never occur*/
            default: return "";
        }
    }

}