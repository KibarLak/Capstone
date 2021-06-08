package com.example.drpotato.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drpotato.R;
import com.example.drpotato.databinding.FragmentHomeBinding;
import com.example.drpotato.model.Potato;
import com.example.drpotato.utils.FileUtils;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private static final int  REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private Uri selectedImageUrl;
    private ImageView imageSelected;
    private Bitmap bitmap;
    private Potato potato;

    private ActivityResultLauncher<String> mGetContent;


    private ActivityResultLauncher<String> requestPermissionLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {

            } else {

            }
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        selectedImageUrl = uri;
                        if (selectedImageUrl != null) {
                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUrl);
                                bitmap = BitmapFactory.decodeStream(inputStream);
                                imageSelected.setImageBitmap(bitmap);
                                binding.predictImage.setVisibility(View.VISIBLE);
                            } catch (Exception exception) {
                                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                });


        imageSelected = binding.selectedImage;
        Button predictButton = binding.predictImage;
        Button selectButton = binding.buttonSelectImage;
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) {
                    mGetContent.launch("image/*");
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            REQUEST_CODE_STORAGE_PERMISSION);
                }

            }
        });

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedFilePath = FileUtils.getPath(getActivity(), selectedImageUrl);
                File uploadFile = new File(selectedFilePath);
                RequestBody filePart = RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(selectedImageUrl)),
                        uploadFile
                );

                MultipartBody.Part file = MultipartBody.Part.createFormData("file", uploadFile.getName(), filePart);
                homeViewModel.getPotato(file).observe(getViewLifecycleOwner(), new Observer<Potato>() {
                    @Override
                    public void onChanged(Potato potato) {
                        binding.predictImage.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", selectedFilePath);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_home);
                        navController.navigate(R.id.action_navigation_home_to_predict_result_fragment, bundle);
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}