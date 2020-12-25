package android.reminder.model;

public class NotificationDbSchema {
    public static final class NotificationTable {
        public static final String NAME = "notifications";

        public static final class Cols {
            public static final String CREATED_AT = "created_at";
            public static final String WAKEUP_AT = "wakeup_at";
            public static final String CONTENT = "content";
            public static final String IMPORTANT_LEVEL = "important_level";
        }
    }
}
