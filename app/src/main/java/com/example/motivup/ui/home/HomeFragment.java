package com.example.motivup.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivup.MainActivity;
import com.example.motivup.R;
import com.example.motivup.alarm.AlarmReceiver;
import com.example.motivup.bottomsheet.BottomSheetFr;
import com.example.motivup.database.DbHelper;
import com.example.motivup.hometargets.RecyclerItemClickListener;
import com.example.motivup.hometargets.Target;
import com.example.motivup.hometargets.TargetAdapter;
import com.example.motivup.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class HomeFragment extends Fragment {
    private DbHelper dbHelper;

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    Spinner search_spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DbHelper(getContext());

        recyclerView = binding.targetsRv;

        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(getContext(),R.array.searchArr, R.layout.spinner_item);

        search_spinner = binding.searchSpinner;

        search_spinner.setAdapter(typeAdapter);

        search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DbHelper dbHelper = new DbHelper(getContext());
                TargetAdapter targetAdapter = new TargetAdapter(getContext(), Target.getData(dbHelper,search_spinner.getSelectedItem().toString()) );
                recyclerView.setAdapter(targetAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DbHelper dbHelper = new DbHelper(getContext());
                TargetAdapter targetAdapter = new TargetAdapter(getContext(), Target.getData(dbHelper,search_spinner.getSelectedItem().toString()) );
                recyclerView.setAdapter(targetAdapter);
            }
        });

        //setAlarm();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                TextView target_id_tw = view.findViewById(R.id.hide_id);
                String target_id = (String) target_id_tw.getText();
                ImageView check = view.findViewById(R.id.productImage);
                TextView lastDate = view.findViewById(R.id.lastDate);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                String lastDateStr = (String) lastDate.getText();

                if(lastDateStr.equals(formattedDate)){
                    try {
                        updateTarget(dbHelper,target_id,"");
                    } finally {
                        dbHelper.close();
                        check.setImageResource(R.drawable.ic_baseline_check_24);
                    }
                } else {
                    try {
                        updateTarget(dbHelper,target_id,formattedDate);
                    } finally {
                        dbHelper.close();
                        check.setImageResource(R.drawable.ic_baseline_check_checked);
                    }
                }
                DbHelper dbHelper = new DbHelper(getContext());
                TargetAdapter targetAdapter = new TargetAdapter(getContext(), Target.getData(dbHelper,search_spinner.getSelectedItem().toString()) );
                recyclerView.setAdapter(targetAdapter);
            }

            @Override public void onLongItemClick(View view, int position) {
                TextView name = view.findViewById(R.id.productName);
                String nameStr = (String) name.getText();
                TextView target_id_tw = view.findViewById(R.id.hide_id);
                String target_id = (String) target_id_tw.getText();

                Bundle bundle = new Bundle();
                bundle.putString("id", target_id );
                bundle.putString("name", nameStr );

                BottomSheetFr bottomSheetFr = new BottomSheetFr();
                bottomSheetFr.setArguments(bundle);
                try {
                    bottomSheetFr.show(getActivity().getSupportFragmentManager(),"");
                } finally {
                    DbHelper dbHelper = new DbHelper(getContext());
                    TargetAdapter targetAdapter = new TargetAdapter(getContext(), Target.getData(dbHelper, search_spinner.getSelectedItem().toString()));
                    recyclerView.setAdapter(targetAdapter);
                }

                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    v.vibrate(50);
                }
            }
        }));

        DbHelper dbHelper = new DbHelper(getContext());
        TargetAdapter targetAdapter = new TargetAdapter(getContext(), Target.getData(dbHelper, search_spinner.getSelectedItem().toString()) );
        recyclerView.setAdapter(targetAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateTarget(DbHelper db, String id, String last_Date) {
        SQLiteDatabase dbx = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("last_date", last_Date);

        dbx.update("targets",values,"target_id=?",new String[] {id});
    }

    private void setAlarm(){
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pendingIntent);
        /*alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);*/
    }

}