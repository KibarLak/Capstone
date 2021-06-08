package com.example.drpotato.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drpotato.R;
import com.example.drpotato.databinding.FragmentHomeBinding;
import com.example.drpotato.databinding.FragmentPredictResultBinding;
import com.example.drpotato.model.Potato;
import com.example.drpotato.utils.FileUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PredictResultFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentPredictResultBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPredictResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        String selectedFilePath = getArguments().getString("path");
        File uploadFile = new File(selectedFilePath);
        Uri uri = Uri.fromFile(uploadFile);
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        homeViewModel.getCurrentPotato().observe(getViewLifecycleOwner(), new Observer<Potato>() {
            @Override
            public void onChanged(Potato potato) {
                binding.diseaseDescription.setText(potato.getDescription());
                binding.diseaseGejala.setText(potato.getGejala().length() > 0 ? potato.getGejala() : "-" );
                binding.diseaseName.setText(potato.getName());
                binding.diseasePrevention.setText(potato.getPrevention().length() > 0 ? potato.getPrevention() : "-");
                binding.diseaseImage.setImageBitmap(bitmap);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getViewModelStore().clear();
        binding = null;
    }
}