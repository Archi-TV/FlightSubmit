package com.example.ex.holder;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.example.ex.R;
import com.example.ex.TripListActionListener;
import com.example.ex.cells.AbsResultCell;
import com.example.ex.cells.ButtonCell;

public class RecyclerViewHolderButton extends AbsResultHolder {

    private static final int HUNDRED = 100;

    private final Button button;
    private final EditText editText;
    private final ProgressBar progressBar;
    private final TripListActionListener tripListActionListener;

    public RecyclerViewHolderButton(@NonNull View itemView,
                                    TripListActionListener tripListActionListener) {
        super(itemView);

        this.tripListActionListener = tripListActionListener;
        button = itemView.findViewById(R.id.b_submit);
        editText = itemView.findViewById(R.id.et_feedback);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    @Override
    public void bind(@NonNull AbsResultCell cell) {
        initProgressBar((ButtonCell) cell);
        initButton((ButtonCell) cell);
        initEditText((ButtonCell) cell);
    }

    private void initEditText(ButtonCell cell){

        editText.setText(cell.getText());

        final boolean enabled = cell.isEnabled();

        editText.setFocusableInTouchMode(enabled);
        editText.setFocusable(enabled);
        editText.setClickable(enabled);
        editText.setEnabled(enabled);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tripListActionListener.onKeyClick(editText.getText().toString());
                return true;
            }
        });
    }

    private void initButton(ButtonCell cell){

        button.setEnabled(cell.isEnabled());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripListActionListener.onButtonClick();
            }
        });
    }

    private void initProgressBar(ButtonCell cell){
        if (cell.isEnabled()){
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        progressBar.setProgress(0);
        progressBar.setMax(HUNDRED);
    }

}
