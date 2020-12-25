package android.reminder.controllers.view_action;

import android.content.Context;
import android.reminder.model.NotificationDetail;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class NotificationAdapter extends RecyclerView.Adapter {
    private List<NotificationDetail> notifications;
    private Context context;

    public NotificationAdapter(List<NotificationDetail> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    public Context getContext() { return context; }

    public NotificationDetail getNotification(int position) {
        return notifications.get(position);
    }

    public void setNotifications(List<NotificationDetail> notifications) {
        this.notifications = notifications;
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

}
