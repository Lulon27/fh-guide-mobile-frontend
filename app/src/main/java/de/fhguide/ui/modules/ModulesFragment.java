package de.fhguide.ui.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.fhguide.ModuleDetailsActivity;
import de.fhguide.R;
import de.fhguide.databinding.FragmentModulesBinding;

public class ModulesFragment extends Fragment
{
    private ModulesViewModel dashboardViewModel;
    private FragmentModulesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        dashboardViewModel =
                new ViewModelProvider(this).get(ModulesViewModel.class);

        binding = FragmentModulesBinding.inflate(inflater, container, false);
        LinearLayout root = binding.getRoot();

        LinearLayout scroll = root.findViewById(R.id.scroll_linear);
        scroll.setPadding(32, 0, 32, 0);

        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ModulesFragment.this.getActivity(), ModuleDetailsActivity.class);
                ModulesFragment.this.getActivity().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onClick()
    {

    }
}