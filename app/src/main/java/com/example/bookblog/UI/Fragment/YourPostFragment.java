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
import com.example.bookblog.UI.Adapter.YourPostAdapter;
import com.example.bookblog.UI.ViewModel.YourPostsFragmentViewModel;



public class YourPostFragment extends Fragment {

    YourPostsFragmentViewModel fragmentViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String TAG = "YourPostFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(YourPostsFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yourposts, container , false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        fragmentViewModel.getMutableLiveData().observe(getActivity(), getYourPosts -> {
            if(getYourPosts!=null){
                progressBar.setVisibility(View.GONE);
                YourPostAdapter youAdapter = new YourPostAdapter(getYourPosts, getActivity());
                recyclerView.setAdapter(youAdapter);
            }else{
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

}
