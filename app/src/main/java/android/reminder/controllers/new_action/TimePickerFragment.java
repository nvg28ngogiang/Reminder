package android.reminder.controllers.new_action;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private EditText timeEdit = null;

    public TimePickerFragment(EditText timeEdit) { this.timeEdit = timeEdit; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append( (hourOfDay < 10) ? ("0" + hourOfDay) : hourOfDay );
        timeBuilder.append( ":" + ((minute < 10) ? ("0" + minute) : minute) );
        timeEdit.setText(timeBuilder);
    }
}
