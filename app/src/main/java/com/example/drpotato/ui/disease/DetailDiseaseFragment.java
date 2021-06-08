package com.example.drpotato.ui.disease;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.drpotato.R;
import com.example.drpotato.databinding.FragmentPredictResultBinding;

public class DetailDiseaseFragment extends Fragment {

    private DiseaseViewModel diseaseViewModel;
    private FragmentPredictResultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        diseaseViewModel =
                new ViewModelProvider(requireActivity()).get(DiseaseViewModel.class);
        binding = FragmentPredictResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        diseaseViewModel.getPotato().observe(getViewLifecycleOwner(), potato -> {
            binding.diseaseDescription.setText(potato.getDescription());
            binding.diseaseGejala.setText(potato.getGejala().length() > 0 ? potato.getGejala() : "-" );
            binding.diseaseName.setText(potato.getName());
            binding.diseasePrevention.setText(potato.getPrevention().length() > 0 ? potato.getPrevention() : "-");
            Glide.with(binding.diseaseImage.getContext())
                    .load(potato.getImageUrl())
                    .apply(
                            RequestOptions.overrideOf(350, 350)
                                    .placeholder(new ColorDrawable(Color.BLACK))
                                    .error(R.drawable.ic_broken_image_24)
                    )
                    .into(binding.diseaseImage);
        });

        return root;
    }
}