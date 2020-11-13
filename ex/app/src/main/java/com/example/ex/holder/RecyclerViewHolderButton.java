package com.example.ex.holder;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ex.MainViewModel;
import com.example.ex.R;
import com.example.ex.cells.AbsResultCell;

public class RecyclerViewHolderButton extends AbsResultHolder {

    private static final int UPPER_BOUND = 1000000;
    private static final int DEFAULT_SLEEP_TIME = 1000;
    private static final int HUNDRED = 100;

    private final Button button;
    private final EditText editText;
    private final ProgressBar progressBar;
    private int progressBarStatus;
    private final Handler progressBarHandler = new Handler();
    private long loading;
    private final MainViewModel viewModel;

    public RecyclerViewHolderButton(@NonNull View itemView, MainViewModel viewModel) {
        super(itemView);

        this.viewModel = viewModel;
        button = itemView.findViewById(R.id.b_submit);
        editText = itemView.findViewById(R.id.et_feedback);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    @Override
    public void bind(@NonNull AbsResultCell cell) {
        initProgressBar();
        initButton();
        initEditText();
    }

    private void initEditText(){

        editText.setText(viewModel.getUserMutableLiveData().getValue().getText());

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = editText.getText().toString();
                viewModel.getUserMutableLiveData().getValue().setText(text);
                return true;
            }
        });
    }

    private void initButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupLoading();
                runAsync();
            }
        });
    }

    private void initProgressBar(){
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setMax(HUNDRED);
    }

    private void setupLoading(){
        progressBarStatus = 0;
        loading = 0;
        progressBar.setVisibility(View.VISIBLE);
        button.setEnabled(false);
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
                                show(viewModel.getUserMutableLiveData().getValue().toString());
                                progressBar.setVisibility(View.GONE);
                                button.setEnabled(true);
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

    private void show(String str){
        Toast.makeText(itemView.getContext(), str, Toast.LENGTH_LONG).show();
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
}
