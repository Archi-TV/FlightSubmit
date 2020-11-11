package com.example.ex;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;

public class ScrollingActivity extends AppCompatActivity implements IAttachable {

    private State state;
    private RatingBar rb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initToolBar();
        initRatingBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    private void initRatingBar() {

        rb = findViewById(R.id.ratingBar2);

        if (state == null) {
            rb.setRating(0);
        }  else {
            rb.setRating(state.getFlight());
        }

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                state.setFlight((int)rating);
                Log.i("RatingToolBar", Integer.toString((int)rating));
            }
        });
    }

    private void initToolBar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(null);
        }

        final CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setBackgroundResource(R.drawable.toolbar_back);
    }

    @Override
    public void passStateToActivity(final State state) {
        this.state = state;
        rb.setRating(state.getFlight());
    }
}