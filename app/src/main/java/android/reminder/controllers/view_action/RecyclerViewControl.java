package android.reminder.controllers.view_action;

import android.content.Context;
import android.reminder.model.NotificationDetail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewControl {
    private RecyclerView managedView;
    private NotificationAdapter adapter;

    public RecyclerViewControl(RecyclerView managedView, NotificationAdapter adapter) {
        this.managedView = managedView;
        this.adapter = adapter;
        this.managedView.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
        this.managedView.setAdapter(adapter);
    }

    public void setNotifications(List<NotificationDetail> notifications) {
        adapter.setNotifications(notifications);
        adapter.notifyDataSetChanged();
    }

}
