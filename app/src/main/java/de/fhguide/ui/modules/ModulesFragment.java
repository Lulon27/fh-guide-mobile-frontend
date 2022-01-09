package de.fhguide.ui.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import de.fhguide.ModuleDetailsActivity;
import de.fhguide.database.FHGuideDatabase;
import de.fhguide.databinding.FragmentModulesBinding;
import de.fhguide.module.Module;

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
        final ArrayList<Module> dbModules = FHGuideDatabase.getInstance(this.getContext()).getModules();
        ModulesArrayAdapter adapter = new ModulesArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, dbModules);
        this.modulesList.setAdapter(adapter);

        this.modulesList.setOnItemClickListener((parent, view, pos, id) ->
        {
            Intent intent = new Intent(this.getActivity(), ModuleDetailsActivity.class);

            Module module = ((ModulesArrayAdapter.ViewHolder)view.getTag()).getModule();

            Bundle bundle = new Bundle();
            bundle.putInt("moduleID", module.getModuleID());
            intent.putExtras(bundle);

            this.requireActivity().startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}