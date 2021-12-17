package de.fhguide.module;

import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import de.fhguide.R;
import de.fhguide.course.Course;
import de.fhguide.course.CourseOverview;

public class Modules
{
    private static final ArrayList<Module> modules = new ArrayList<>();

    static
    {
        initModules();
    }

    private static void initModules()
    {
        Random rng = new Random(4);
        int nextID = 0;
        int nextCourseID = 0;

        for(int i = 0; i < 8; ++i)
        {
            final ModuleInfo moduleInfo = new ModuleInfo(
                    "Module " + i,
                    "Lecturer name",
                    "Description of the module",
                    R.drawable.placeholder_256);

            for(int j = 0; j < Skill.values().length; ++j)
            {
                if(rng.nextBoolean())
                {
                    moduleInfo.getAverageSkillRating().setRating(Skill.values()[j], rng.nextFloat());
                }
            }
            final Module module = new Module(nextID++, moduleInfo);

            module.getProperties().add(new Pair<>("module.props.lecturer", "Name"));
            module.getProperties().add(new Pair<>("module.props.etcs", "Name"));
            module.getProperties().add(new Pair<>("module.props.requirement", "Name"));

            String[] courseNames = {"Vorlesung", "Praktikum"};
            for(int j = 0; j < 2; ++j)
            {
                final CourseOverview overview = new CourseOverview(courseNames[j]);
                overview.getSectionMain().add("Text 1");
                overview.getSectionMain().add("Text 2");
                overview.getSectionMain().add("Text 3");
                overview.getSectionInfo().getProperties().add(new Pair<>("course.info.period", "01.10.2021 - 28.02.2022"));
                overview.getSectionInfo().getProperties().add(new Pair<>("course.info.mode", "Online"));
                overview.getSectionCustom().getProperties().add(new Pair<>("Zoom ID", "123456789"));
                overview.getSectionCustom().getProperties().add(new Pair<>("Kennwort", "987654321"));
                module.getCourses().add(new Course(nextCourseID++, overview));
            }

            modules.add(module);
        }
    }

    public static ArrayList<Module> getModules()
    {
        return modules;
    }

    public static Module getModuleByID(int moduleID)
    {
        for(int i = 0; i < Modules.modules.size(); ++i)
        {
            if(Modules.modules.get(i).getModuleID() == moduleID)
            {
                return Modules.modules.get(i);
            }
        }
        return null;
    }
}
