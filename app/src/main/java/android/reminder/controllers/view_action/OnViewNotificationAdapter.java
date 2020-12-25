package android.reminder.controllers.view_action;

import android.content.Context;
import android.reminder.controllers.R;
import android.reminder.model.NotificationDetail;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnViewNotificationAdapter extends NotificationAdapter {

    public OnViewNotificationAdapter(List<NotificationDetail> notifications, Context context) {
        super(notifications, context);
    }

    private class OnViewNotificationHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;


        public OnViewNotificationHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_on_view);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            });
        }
    }

    @NonNull
    @Override
    public OnViewNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.on_view_notification, parent, false);
        return new OnViewNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationDetail notification = getNotification(position);
        ((OnViewNotificationHolder) holder).textView.setText(notification.createdAt() + ": " + notification.getContent());
    }
}
