package de.fhguide.ui.modules;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhguide.Util;
import de.fhguide.database.FHGuideDatabase;
import de.fhguide.databinding.FragmentModuleDetailsBinding;
import de.fhguide.module.Module;
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

        FHGuideDatabase db = FHGuideDatabase.getInstance(this.getContext());

        Module module = db.getModuleByID(moduleID);

        if(module == null)
        {
            System.err.println("Module not found by ID " + moduleID);
            return binding.getRoot();
        }

        binding.moduleName.setText(module.getOverview().getName());
        binding.moduleIcon.setImageDrawable(this.getResources().getDrawable(module.getOverview().getIconID(), null));
        binding.moduleDescriptionShort.setText(module.getOverview().getDescription());

        Util.createViewsFromPairList(module.getProperties(), binding.propertiesList, 4, 8);

        if(module.getCourses().size() > 0)
        {
            CourseView cv = new CourseView(this.getContext());
            cv.applyCourseInfo(module.getCourses().get(0));
            this.binding.testCard.addView(cv);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}