package android.reminder.controllers.view_action;

import android.content.Context;
import android.reminder.controllers.R;
import android.reminder.model.NotificationDetail;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnHomeNotificationAdapter extends NotificationAdapter {

    public OnHomeNotificationAdapter(List<NotificationDetail> notifications, Context context) {
        super(notifications, context);
    }

    private class OnHomeNotificationHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public OnHomeNotificationHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_on_home);
        }
    }

    @NonNull
    @Override
    public OnHomeNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.on_home_notification, parent, false);
        return new OnHomeNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationDetail notification = getNotification(position);
        String createdAt = notification.createdAt();
        String createdAtTime = createdAt.substring(createdAt.indexOf(' ') + 1);
        ((OnHomeNotificationHolder) holder).textView.setText(createdAtTime + ": " + notification.getContent());
    }
}
