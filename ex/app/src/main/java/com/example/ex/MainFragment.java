package com.example.ex;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {


    private static final int UPPER_BOUND = 1000000;
    private static final int DEFAULT_SLEEP_TIME = 1000;
    private static final int HUNDRED = 100;
    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText editText;
    private IAttachable listener;
    private ProgressBar progressBar;
    private int progressBarStatus;
    private final Handler progressBarHandler = new Handler();
    private long loading;
    private LinearLayout layout;


    private final Observer<ArrayList<Tuple>> tupleListUpdateObserver = new Observer<ArrayList<Tuple>>() {
        @Override
        public void onChanged(final ArrayList<Tuple> tupleArrayList) {
            final FragmentActivity activity = getActivity();
            recyclerViewAdapter = new RecyclerViewAdapter(activity, tupleArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof IAttachable) {
            listener = (IAttachable) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment, container, false);
        layout = v.findViewById(R.id.linear_layout);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() == null) {
            return;
        }
        recyclerView = getView().findViewById(R.id.rv_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), tupleListUpdateObserver);
        listener.passStateToActivity(viewModel.getState());

        initProgressBar();
        initButton();
        initEditText();
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setMax(HUNDRED);
        layout.addView(progressBar);
    }

    private void initEditText(){
        if (getView() == null) {
            return;
        }
        editText = (EditText)getView().findViewById(R.id.et_feedback);
        editText.setText(viewModel.getState().getText());
    }

    private void initButton(){
        final View v = getView();
        if (v == null) {
            return;
        }
        final Button submit = (Button)v.findViewById(R.id.b_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                saveState();
                Log.i("submission", "State is saved");
                setupLoading();
                runAsync();
                Log.i("AsyncTask", "AsyncTask was started");
            }
        });
    }

    private void setupLoading(){
        progressBarStatus = 0;
        loading = 0;
        progressBar.setVisibility(View.VISIBLE);
    }

    private void runAsync(){
        new Thread(new Runnable(){
            public void run(){
                while (progressBarStatus < HUNDRED){
                    progressBarStatus = simulateDoingSmthInteresting();
                    try {
                        Thread.sleep(DEFAULT_SLEEP_TIME);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                            if (progressBarStatus == HUNDRED){
                                show(viewModel.getState().toString());
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                try {
                    Thread.sleep(2 * DEFAULT_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int simulateDoingSmthInteresting() {

        while (loading <= UPPER_BOUND) {
            ++loading;

            if (loading == UPPER_BOUND / 3) {
                return HUNDRED / 3;
            } else if (loading == UPPER_BOUND * 2 / 3) {
                return HUNDRED * 2 / 3;
            } else if (loading == UPPER_BOUND * 4 / 5) {
                return HUNDRED * 4 / 5;
            }
        }

        return HUNDRED;

    }

    private void saveState(){
        final State state = viewModel.getState();
        final ArrayList<Tuple> tupleArrayList = recyclerViewAdapter.getTupleArrayList();

        state.setPeople(tupleArrayList.get(0).getRating());
        state.setAircraft(tupleArrayList.get(1).getRating());
        state.setSeat(tupleArrayList.get(2).getRating());
        state.setCrew(tupleArrayList.get(3).getRating());
        if (tupleArrayList.get(4).isChecked()) {
            state.setFood(-1);
        } else {
            state.setFood(tupleArrayList.get(4).getRating());
        }

        state.setText(editText.getText().toString());
    }


    private void show(final String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

}

