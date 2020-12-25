package android.reminder.controllers.db_interaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.reminder.model.NotificationDbSchema.NotificationTable;
import android.reminder.model.NotificationDetail;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificationLab {
    private static NotificationLab lab;

    private Context context;
    private SQLiteDatabase database;

    public static NotificationLab get(Context context) {
        if (lab == null) {
            lab = new NotificationLab(context);
        }
        return lab;
    }

    private NotificationLab(Context context) {
        this.context = context.getApplicationContext();
        database = new NotificationDbHelper(this.context)
                .getWritableDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<NotificationDetail> getNotificationsOn(String date) {
        String whereClause = "date(" + NotificationTable.Cols.WAKEUP_AT + ") = date(?)";
        String orderByClause = "datetime(" + NotificationTable.Cols.WAKEUP_AT + ") ASC";
        Cursor cursor = queryNotifications(whereClause, new String[] { date }, orderByClause);

        return getNotificationsFromCursor(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<NotificationDetail> getHistoryNotifications() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String now = LocalDateTime.now().format(formatter);
        String whereClause = "datetime(" + NotificationTable.Cols.WAKEUP_AT + ") < datetime(?)";
        String orderByClause = "datetime(" + NotificationTable.Cols.WAKEUP_AT + ") DESC";
        Cursor cursor = queryNotifications(whereClause, new String[] { now }, orderByClause);

        return getNotificationsFromCursor(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<NotificationDetail> getCurrentNotifications() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String now = LocalDateTime.now().format(formatter);
        String whereClause = "datetime(" + NotificationTable.Cols.WAKEUP_AT + ") >= datetime(?)";
        String orderByClause = "datetime(" + NotificationTable.Cols.WAKEUP_AT + ") ASC";
        Cursor cursor = queryNotifications(whereClause, new String[] { now }, orderByClause);

        return getNotificationsFromCursor(cursor);
    }

    private Cursor queryNotifications(String whereClause, String[] whereArgs, String orderByClause) {
        Cursor cursor = database.query(
                NotificationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderByClause);
        return cursor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static List<NotificationDetail> getNotificationsFromCursor(Cursor cursor) {
        List<NotificationDetail> notifications = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String createdAt = cursor.getString(cursor.getColumnIndex(NotificationTable.Cols.CREATED_AT));
                String wakeupAt = cursor.getString(cursor.getColumnIndex(NotificationTable.Cols.WAKEUP_AT));
                String content = cursor.getString(cursor.getColumnIndex(NotificationTable.Cols.CONTENT));
                int importanceLevel = cursor.getInt(cursor.getColumnIndex(NotificationTable.Cols.WAKEUP_AT));

                notifications.add(new NotificationDetail(createdAt, wakeupAt, content, importanceLevel));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notifications;
    }

    public void insertNotification(NotificationDetail note) {
        ContentValues values = getContentValues(note);
        database.insert(NotificationTable.NAME, null, values);
    }

    private static ContentValues getContentValues(NotificationDetail note) {
        ContentValues values = new ContentValues();
        values.put(NotificationTable.Cols.WAKEUP_AT, note.wakeupAt());
        values.put(NotificationTable.Cols.CREATED_AT, note.createdAt());
        values.put(NotificationTable.Cols.CONTENT, note.getContent());
        values.put(NotificationTable.Cols.IMPORTANT_LEVEL, note.getImportanceLevel());

        return values;
    }
}
