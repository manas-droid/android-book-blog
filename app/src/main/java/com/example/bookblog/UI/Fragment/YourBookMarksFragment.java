package com.example.bookblog.UI.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookblog.R;
import com.example.bookblog.UI.Adapter.YourBookMarkAdapter;
import com.example.bookblog.UI.ViewModel.YourBookMarkViewModel;


public class YourBookMarksFragment  extends Fragment {
    private ProgressBar progressBar;
    private YourBookMarkViewModel yourBookMarkViewModel;
    private RecyclerView recyclerView;
    private static final String TAG = "YourBookMarksFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yourBookMarkViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(YourBookMarkViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yourbookmarks, container , false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        progressBar = view.findViewById(R.id.progressBar);

        yourBookMarkViewModel.getMutableLiveData().observe(getActivity(), getYourBookMarks -> {
            if(getYourBookMarks!=null){
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: here");
                YourBookMarkAdapter adapter = new YourBookMarkAdapter(getYourBookMarks, getActivity());
                recyclerView.setAdapter(adapter);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}