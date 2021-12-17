package de.fhguide.ui.modules;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.nio.charset.Charset;

import de.fhguide.ModuleDetailsActivity;
import de.fhguide.R;
import de.fhguide.Util;
import de.fhguide.databinding.FragmentModuleDetailsBinding;
import de.fhguide.databinding.FragmentModulesBinding;
import de.fhguide.module.Module;
import de.fhguide.module.Modules;
import de.fhguide.ui.view.courses.CourseView;

public class ModuleDetailsFragment extends Fragment {

    private ModuleDetailsModel dashboardViewModel;
    private FragmentModuleDetailsBinding binding;

    public static ModuleDetailsFragment newInstance()
    {
        return new ModuleDetailsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        dashboardViewModel = new ViewModelProvider(this).get(ModuleDetailsModel.class);

        binding = FragmentModuleDetailsBinding.inflate(inflater, container, false);

        Bundle extras = this.requireActivity().getIntent().getExtras();
        int moduleID = extras.getInt("moduleID", -1);
        if(moduleID == -1 || moduleID >= Modules.getModules().size())
        {
            System.err.println("ModuleID is missing");
            return binding.getRoot();
        }

        Module module = Modules.getModuleByID(moduleID);

        binding.moduleName.setText(module.getOverview().getName());
        binding.moduleIcon.setImageDrawable(this.getResources().getDrawable(module.getOverview().getIconID(), null));
        binding.moduleDescriptionShort.setText(module.getOverview().getDescription());

        Util.createViewsFromPairList(module.getProperties(), binding.propertiesList, 4, 8);

        CourseView cv = new CourseView(this.getContext());
        cv.applyCourseInfo(module.getCourses().get(0));
        this.binding.testCard.addView(cv);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}