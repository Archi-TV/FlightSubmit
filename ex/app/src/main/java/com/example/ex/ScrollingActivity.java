package com.example.ex;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.widget.RatingBar;

public class ScrollingActivity extends AppCompatActivity{

    private  RatingBar rb;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initToolBar();
        initRatingBar();

        viewModel.getUserMutableLiveData().observe(this, new Observer<State>() {
            @Override
            public void onChanged(State state) {
                if (state == null) {
                    return;
                }
                rb.setRating(state.getFlight());
                rb.setEnabled(state.isEnabled());
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    private void initRatingBar() {

        rb = findViewById(R.id.ratingBarFlight);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                viewModel.getUserMutableLiveData().getValue().setFlight((int)rating);
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
    protected void onDestroy() {
        super.onDestroy();
        viewModel = null;
        rb = null;
    }
}