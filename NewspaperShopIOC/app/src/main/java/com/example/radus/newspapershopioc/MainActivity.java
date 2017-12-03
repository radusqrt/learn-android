package com.example.radus.newspapershopioc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean isShoppingViewActive;
    private View mCheckoutView, mShoppingView;
    private int mShortAnimationDuration;
    private ArrayList<NewspaperEntry> listViewEntries = new ArrayList<>();
    private ArrayList<String> namesToCheckout = new ArrayList<>();
    private ArrayList<Integer> soldToCheckout = new ArrayList<>();
    private ListViewAdapter adapter;
    private EditText editSearch;
    private TextView checkoutList;
    private double currentPrice;

    private void initializeListView() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        List<NewspaperEntry> queryList = db.userDao().getAllBySoldNumber();
        listViewEntries.clear();
        listViewEntries.addAll(queryList);

        ListView listView = findViewById(R.id.news_listview);

        adapter = new ListViewAdapter(this, listViewEntries);
        listView.setAdapter(adapter);

        editSearch = findViewById(R.id.search_edit_text);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeScreen();
        setListeners();
        setDb();
        initializeListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void crossfade(final View fromView, View toView) {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        toView.setAlpha(0f);
        toView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        toView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        fromView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fromView.setVisibility(View.GONE);
                    }
                });
    }

    private void initializeDb() {
        Log.d("EXCEPTION", "INIT");
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        NewspaperEntry[] entries = new NewspaperEntry[9];
        entries[0] = new NewspaperEntry("Click!", "Cotidian Popular",
                "Cotidian", "Platit", "National", 96201, 0,
                R.drawable.click, 2.5);
        entries[1] = new NewspaperEntry("Practic in bucatarie", "Culinar",
                "Lunar", "Platit", "National", 86667, 0,
                R.drawable.practic_in_bucatarie, 9);
        entries[2] = new NewspaperEntry("Libertatea", "Cotidian Popular",
                "Cotidian", "Platit", "National", 59112, 0,
                R.drawable.libertatea, 1.5);
        entries[3] = new NewspaperEntry("TV Mania", "Ghid TV",
                "Bilunar", "Platit", "National", 58012, 0,
                R.drawable.tvmania, 8);
        entries[4] = new NewspaperEntry("Libertatea pentru femei", "Femei",
                "Saptamanal", "Platit", "National", 59034, 0,
                R.drawable.libertatea_pentru_femei, 4);
        entries[5] = new NewspaperEntry("Femeia", "Femei",
                "Saptamanal", "Platit", "National", 47258, 0,
                R.drawable.femeia, 3);
        entries[6] = new NewspaperEntry("Lumina", "Spiritualitate",
                "Cotidian", "Platit", "National", 22382, 0,
                R.drawable.lumina, 1);
        entries[7] = new NewspaperEntry("Gazeta Sporturilor", "Sport",
                "Cotidian", "Platit", "National", 25057, 0,
                R.drawable.gsp, 2);
        entries[8] = new NewspaperEntry("Sanatatea de azi", "Sport",
                "Lunar", "Platit", "National", 30000, 0,
                R.drawable.sanatatea_de_azi, 10);
        db.userDao().insertAll(entries);
    }

    private void printDb() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        List<NewspaperEntry> allEntries = db.userDao().getAll();

        for (NewspaperEntry entry : allEntries) {
            entry.printDescription();
        }
    }

    private void initializeScreen() {
        Toolbar myToolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(myToolbar);

        mShoppingView = findViewById(R.id.shopping_layout);
        mCheckoutView = findViewById(R.id.checkout_layout);

        // Initially hide the content view.
        mCheckoutView.setVisibility(View.GONE);
        isShoppingViewActive = true;

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime
        );

        checkoutList = findViewById(R.id.checkout_list);
    }

    private void setListeners() {
        // Set navigation buttons' methods
        findViewById(R.id.shopping_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShoppingViewActive) {
                    crossfade(mCheckoutView, mShoppingView);
                    isShoppingViewActive = true;
                }
            }
        });

        findViewById(R.id.checkout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShoppingViewActive) {
                    setCheckoutScreen();
                    crossfade(mShoppingView, mCheckoutView);
                    isShoppingViewActive = false;
                }
            }
        });

        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCheckout();
            }
        });

        findViewById(R.id.make_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Checkout")
                        .setMessage("Are you sure you want to continue purchasing these items?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                                TransactionEntry transactionEntry = new TransactionEntry(namesToCheckout);
                                db.transactionDao().insert(transactionEntry);

//                                for (NewspaperEntry ne : listViewEntries) {
//                                    for (int i = 0; i < namesToCheckout.size(); ++i) {
//
//                                    }
//                                }

                                resetCheckout();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void resetCheckout() {
        if (!isShoppingViewActive) {
            crossfade(mCheckoutView, mShoppingView);
            isShoppingViewActive = true;
        }

        initializeListView();
    }

    private void setCheckoutScreen() {
        checkoutList.setText("");
        List<NewspaperEntry> adapterList = adapter.getList();
        namesToCheckout.clear();
        currentPrice = 0;

        for (NewspaperEntry ne : adapterList) {
            if (ne.getNumberOfCopies() > 0) {
                currentPrice += ne.getPrice() * ne.getNumberOfCopies();
                String checkoutText = checkoutList.getText().toString();
                checkoutText += ne.getName() + ": " + ne.getNumberOfCopies() + " x " + ne.getPrice() + "RON";
                if (ne.getCdChecked()) {
                    currentPrice += ne.getNumberOfCopies();
                    checkoutText += " + CD (" + ne.getNumberOfCopies() + " RON)";
                }
                if(ne.getBookChecked()) {
                    currentPrice += ne.getNumberOfCopies() * 5;
                    checkoutText += " + BOOK (" + 5 * ne.getNumberOfCopies() + " RON)";
                }
                checkoutText += "\n";
                checkoutList.setText(checkoutText);
                namesToCheckout.add(ne.getName());
                soldToCheckout.add(ne.getNumberOfCopies());
            }
        }
    }

    private void setDb() {
        Thread initializeDbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                initializeDb();
            }
        });
        Thread printDb = new Thread(new Runnable() {
            @Override
            public void run() {
                printDb();
            }
        });

        try {
            initializeDbThread.start();
            initializeDbThread.join();
            printDb.start();
            printDb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
