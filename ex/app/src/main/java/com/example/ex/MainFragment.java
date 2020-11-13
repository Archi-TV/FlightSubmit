package com.example.ex;

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

public class MainFragment extends Fragment {


    private MainViewModel viewModel;
    private RecyclerView recyclerView;


        private final Observer<State> updateObserver = new Observer<State>() {
            @Override
            public void onChanged(final State tupleArrayList) {
                final FragmentActivity activity = getActivity();
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(activity, viewModel);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        };


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

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), updateObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        viewModel = null;
    }
}

