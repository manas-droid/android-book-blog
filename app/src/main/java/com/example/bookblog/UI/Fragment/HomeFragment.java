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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookblog.HomeActivity;
import com.example.bookblog.R;
import com.example.bookblog.UI.Adapter.HomeAdapter;
import com.example.bookblog.UI.ViewModel.HomeFragmentViewModel;

import java.util.List;

import apolloSchema.GetAllPostResultsQuery;


public class HomeFragment extends Fragment {
    private HomeFragmentViewModel homeFragmentViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String TAG = "HomeFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragmentViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(HomeFragmentViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container , false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeFragmentViewModel.getGetAllPostMutableLiveData().observe(getActivity(), new Observer<List<GetAllPostResultsQuery.GetAllPost>>() {
            @Override
            public void onChanged(List<GetAllPostResultsQuery.GetAllPost> getAllPosts) {
                if(getAllPosts!=null) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onChanged: here");
                    HomeAdapter adapter = new HomeAdapter(getAllPosts, getActivity());
                    recyclerView.setAdapter(adapter);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}
