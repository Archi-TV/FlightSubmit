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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment {


    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private IAttachable listener;


        private final Observer<State> updateObserver = new Observer<State>() {
            @Override
            public void onChanged(final State tupleArrayList) {
                final FragmentActivity activity = getActivity();
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(activity, viewModel);
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
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() == null) {
            return;
        }
        recyclerView = getView().findViewById(R.id.rv_main);

        try {
            viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        } catch (Exception e) {
            show(e.getMessage());
        }
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), updateObserver);
        listener.passStateToActivity(viewModel.getUserMutableLiveData().getValue());
    }


    private void show(final String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        viewModel = null;
    }
}

