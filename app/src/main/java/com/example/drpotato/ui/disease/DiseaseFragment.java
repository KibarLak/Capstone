package com.example.drpotato.ui.disease;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drpotato.R;
import com.example.drpotato.databinding.FragmentDiseaseBinding;
import com.example.drpotato.model.Potato;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class DiseaseFragment extends Fragment {

    private DiseaseViewModel diseaseViewModel;
    private FragmentDiseaseBinding binding;
    private RecyclerView recyclerView;
    private DiseaseAdapter diseaseAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diseaseViewModel =
                new ViewModelProvider(requireActivity()).get(DiseaseViewModel.class);

        binding = FragmentDiseaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        diseaseAdapter = new DiseaseAdapter();
        diseaseAdapter.setOnItemClickCallback(potato -> {
            diseaseViewModel.setPotato(potato);
            showDiseaseDetail(potato);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(diseaseAdapter);

        return root;
    }

    private void showDiseaseDetail(Potato potato) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_home);
        navController.navigate(R.id.action_navigation_disease_to_navigation_detail_disease);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diseaseViewModel.getDiseases().observe(getViewLifecycleOwner(), potatoes -> {
            binding.diseasesShimmer.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            diseaseAdapter.setPotatoes(potatoes);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}