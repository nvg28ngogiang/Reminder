package android.reminder.controllers.db_interaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.reminder.model.NotificationDbSchema.NotificationTable;

public class NotificationDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "notificationDb.db";

    public NotificationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NotificationTable.NAME + "(" +
                NotificationTable.Cols.WAKEUP_AT + " primary key, " +
                NotificationTable.Cols.CREATED_AT + ", " +
                NotificationTable.Cols.CONTENT + ", " +
                NotificationTable.Cols.IMPORTANT_LEVEL +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
