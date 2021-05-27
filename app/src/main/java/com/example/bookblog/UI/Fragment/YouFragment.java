package com.example.bookblog.UI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookblog.R;
import com.example.bookblog.UI.Adapter.HomeAdapter;
import com.example.bookblog.UI.Adapter.YouAdapter;
import com.example.bookblog.UI.ViewModel.HomeFragmentViewModel;
import com.example.bookblog.UI.ViewModel.YouFragmentViewModel;

import java.util.List;

import apolloSchema.AllYourPostQuery;

public class YouFragment extends Fragment {

    YouFragmentViewModel fragmentViewModel;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(YouFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_you, container , false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentViewModel.getMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<AllYourPostQuery.GetYourPost>>() {
            @Override
            public void onChanged(List<AllYourPostQuery.GetYourPost> getYourPosts) {
                if(getYourPosts!=null){
                    YouAdapter youAdapter = new YouAdapter(getYourPosts, getActivity());
                    recyclerView.setAdapter(youAdapter);
                }
            }
        });

        return view;
    }

}
