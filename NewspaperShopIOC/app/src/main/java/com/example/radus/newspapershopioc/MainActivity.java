package com.example.radus.newspapershopioc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean isShoppingViewActive;
    private View mCheckoutView, mShoppingView;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    crossfade(mShoppingView, mCheckoutView);
                    isShoppingViewActive = false;
                }
            }
        });
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
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
//                crossfade(mShoppingView, mCheckoutView);
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
}
