package de.fhguide.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import de.fhguide.databinding.FragmentLoadingBinding;
import de.fhguide.databinding.FragmentLoadingFailBinding;

public class FragmentLoadingFail extends Fragment
{
    private FragmentLoadingFailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentLoadingFailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    public void setErrorText(CharSequence text)
    {
        this.binding.errorText.setText(text);
    }
}
