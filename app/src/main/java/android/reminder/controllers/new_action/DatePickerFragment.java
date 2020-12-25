package android.reminder.controllers.new_action;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText dateEdit = null;

    public DatePickerFragment(EditText dateEdit) {
        this.dateEdit = dateEdit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(year);
        dateBuilder.append( "-" + ((month < 10) ? ("0" + month) : month ));
        dateBuilder.append( "-" + ((dayOfMonth < 10) ? ("0" + dayOfMonth) : dayOfMonth ));
        dateEdit.setText(dateBuilder);
    }

}
