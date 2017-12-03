package com.example.radus.newspapershopioc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    List<TransactionEntry> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        transactions = db.transactionDao().getAll();
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < transactions.size(); ++i) {
            numbers.add(String.valueOf(i));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numbers);
        Spinner spinner = findViewById(R.id.spinner_history);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String textToPrint = "";
                TransactionEntry transactionEntry = transactions.get(i);
                List<String> names = transactionEntry.getTransactions();
                for (String name : names) {
                    textToPrint += name + "\n";
                }

                ((TextView) findViewById(R.id.transaction_text)).setText(textToPrint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
