package android.reminder.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class NotificationDetail {

    private String createdAt;
    private String wakeupAt;
    private String content;
    private int importanceLevel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationDetail(String wakeupAt, String content, int importanceLevel) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        createdAt = LocalDateTime.now().format(formater);
        this.content = content;
        this.wakeupAt = wakeupAt;
        this.importanceLevel = importanceLevel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationDetail(String createdAt, String wakeupAt, String content, int importanceLevel) {
        this(wakeupAt, content, importanceLevel);
        this.createdAt = createdAt;
    }

    public String createdAt() {
        return createdAt;
    }

    public String wakeupAt() { return wakeupAt; }

    public String getContent() {
        return content;
    }

    public int getImportanceLevel() { return importanceLevel; }

    /*
    public long getTimeInMillis() {
        return wakeupAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }*/

    /*
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter) + " " + content + " " + wakeupAt.format(formatter);
    }*/
}
