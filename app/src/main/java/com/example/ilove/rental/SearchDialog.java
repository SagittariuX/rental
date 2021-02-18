package com.example.ilove.rental;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchDialog extends AppCompatDialogFragment {
    Spinner spinnerHouseNumber, spinnerMonth, spinnerYear;
    Button buttonReset, buttonSearch;
    SearchDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_search_dialog,null);

        builder.setView(view).setTitle("Search Database");

        spinnerHouseNumber = (Spinner)view.findViewById(R.id.spinnerSearchDialogHouseNumber);
        spinnerMonth = (Spinner)view.findViewById(R.id.spinnerSearchDialogMonth);
        spinnerYear =(Spinner)view.findViewById(R.id.spinnerSearchDialogYear);
        buttonReset = (Button)view.findViewById(R.id.buttonSearchDialogReset);
        buttonSearch = (Button)view.findViewById(R.id.buttonSearchDialogSearch);

        setupInitialSpinners();
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setupInitialSpinners();
                listener.searchFor("reset","reset","reset");//reset to default
                dismiss();            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String houseNumber = spinnerHouseNumber.getSelectedItem().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String year = spinnerYear.getSelectedItem().toString();
                listener.searchFor(houseNumber,month,year); //search for actual information

                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SearchDialogListener)context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString()+
           "must implement SearchDialogListener");
        }

    }

    public interface SearchDialogListener{
        void searchFor(String houseNumber, String month, String year);
    }


    public void setupInitialSpinners(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        spinnerHouseNumber.setSelection(0);
        spinnerMonth.setSelection(calendar.get(Calendar.MONTH));
        setSpinText(spinnerYear,""+calendar.get(Calendar.YEAR));
    }

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().equals(text))
            {
                spin.setSelection(i);
            }
        }

    }


}
