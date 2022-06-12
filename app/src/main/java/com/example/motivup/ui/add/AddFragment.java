package com.example.motivup.ui.add;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motivup.R;
import com.example.motivup.database.DbHelper;
import com.example.motivup.database.Target;
import com.example.motivup.databinding.FragmentAddBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {
    private DbHelper dbHelper;

    private FragmentAddBinding binding;
    Spinner type;
    Button addBtn;
    EditText targetName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        binding = FragmentAddBinding.inflate(inflater, container, false);

        dbHelper = new DbHelper(getContext());

        type = binding.spinner;

        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(getContext(), R.array.typeArr, R.layout.spinner_item);

        type.setAdapter(typeAdapter);

        targetName = binding.targetName;

        addBtn = binding.addBtn;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                try {
                    addTarget(dbHelper,String.valueOf(targetName.getText()),String.valueOf(type.getSelectedItem()),formattedDate);
                } finally {
                    dbHelper.close();
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    targetName.setText("");
                }
            }
        });

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void addTarget(DbHelper db, String name, String type, String last_Date) {
        SQLiteDatabase dbx = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("target_name", name);
        values.put("target_type", type);
        values.put("last_date", last_Date);

        dbx.insertOrThrow("targets",null,values);
    }
}