package com.example.ex.holder;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.ex.R;
import com.example.ex.RecyclerViewAdapter;
import com.example.ex.cells.AbsResultCell;
import com.example.ex.cells.ButtonCell;

public class RecyclerViewHolderButton extends AbsResultHolder {

    private static final int HUNDRED = 100;

    private final Button button;
    private final EditText editText;
    private final ProgressBar progressBar;
    private final RecyclerViewAdapter.TripListActionListener tripListActionListener;

    public RecyclerViewHolderButton(@NonNull View itemView,
                                    RecyclerViewAdapter.TripListActionListener tripListActionListener) {
        super(itemView);

        this.tripListActionListener = tripListActionListener;
        button = itemView.findViewById(R.id.b_submit);
        editText = itemView.findViewById(R.id.et_feedback);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    @Override
    public void bind(@NonNull AbsResultCell cell) {
        initProgressBar();
        initButton();
        initEditText((ButtonCell) cell);
    }

    private void initEditText(ButtonCell cell){

        editText.setText(cell.getText());

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tripListActionListener.onKeyClick(editText.getText().toString());
                return true;
            }
        });
    }

    private void initButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripListActionListener.onButtonClick(progressBar, button, editText);
            }
        });
    }

    private void initProgressBar(){
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setMax(HUNDRED);
    }

}
