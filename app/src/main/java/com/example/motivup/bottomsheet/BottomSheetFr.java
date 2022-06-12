package com.example.motivup.bottomsheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.motivup.R;
import com.example.motivup.database.DbHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BottomSheetFr extends BottomSheetDialogFragment {
    private DbHelper dbHelper;

    Spinner type;
    EditText name;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        String myValue = this.getArguments().getString("name");
        String id = this.getArguments().getString("id");

        name = v.findViewById(R.id.name);
        type = v.findViewById(R.id.type_spinner);

        dbHelper = new DbHelper(getContext());

        name.setText(myValue);

        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(getContext(),R.array.typeArr, R.layout.spinner_item);

        type.setAdapter(typeAdapter);

        Button delBtn = v.findViewById(R.id.delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    delTarget(dbHelper,id);
                } finally {
                    dbHelper.close();
                }
                dismiss();
            }
        });

        Button cat1 = v.findViewById(R.id.addBtn);
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeStr = type.getSelectedItem().toString().trim();
                String nameStr = name.getText().toString();
                if(nameStr.equals("")) {
                    Toast.makeText(getContext(), getString(R.string.null_err), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    updateTarget(dbHelper,id,nameStr, typeStr);
                } finally {
                    dbHelper.close();
                }
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void updateTarget(DbHelper db, String id, String nameStr, String typeStr) {
        SQLiteDatabase dbx = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("target_name", nameStr);
        values.put("target_type", typeStr);

        dbx.update("targets",values,"target_id=?",new String[] {id});
    }

    public void delTarget(DbHelper db, String id) {
        SQLiteDatabase dbx = db.getReadableDatabase();
        dbx.delete("targets","target_id=?",new String[] {id});
    }
}
