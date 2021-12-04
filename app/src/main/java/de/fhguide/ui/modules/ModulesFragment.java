package de.fhguide.ui.modules;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import de.fhguide.ModuleDetailsActivity;
import de.fhguide.R;
import de.fhguide.databinding.FragmentModulesBinding;
import de.fhguide.module.ModuleInfo;
import de.fhguide.module.Modules;
import de.fhguide.module.Skill;
import de.fhguide.ui.view.modules.ModuleView;

public class ModulesFragment extends Fragment
{
    private ModulesViewModel dashboardViewModel;
    private FragmentModulesBinding binding;

    private ListView modulesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        dashboardViewModel = new ViewModelProvider(this).get(ModulesViewModel.class);

        binding = FragmentModulesBinding.inflate(inflater, container, false);

        this.modulesList = binding.modulesList;
        ModulesArrayAdapter adapter = new ModulesArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, Modules.getModules());
        this.modulesList.setAdapter(adapter);

        this.modulesList.setOnItemClickListener((parent, view, pos, id) ->
        {
            ModuleView moduleView = (ModuleView)view;
            Intent intent = new Intent(this.getActivity(), ModuleDetailsActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("moduleID", pos);
            intent.putExtras(bundle);

            this.requireActivity().startActivity(intent);
        });

        return binding.getRoot();
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