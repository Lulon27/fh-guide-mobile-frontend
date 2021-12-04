package de.fhguide.module;

import java.util.ArrayList;
import java.util.Random;

import de.fhguide.R;

public class Modules
{
    private static final ArrayList<ModuleInfo> modules = new ArrayList<>();

    public static ArrayList<ModuleInfo> getModules()
    {
        return modules;
    }

    static
    {
        initModules();
    }

    private static void initModules()
    {
        Random rng = new Random(4);
        for(int i = 0; i < 8; ++i)
        {
            final ModuleInfo module = new ModuleInfo(
                    "Module " + i,
                    "Lecturer name",
                    "Description of the module",
                    R.drawable.placeholder_256);

            for(int j = 0; j < Skill.values().length; ++j)
            {
                if(rng.nextBoolean())
                {
                    module.getAverageSkillRating().setRating(Skill.values()[j], rng.nextFloat());
                }
            }

            modules.add(module);
        }
    }
}
