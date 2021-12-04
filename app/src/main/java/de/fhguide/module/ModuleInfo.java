package de.fhguide.module;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class ModuleInfo
{
    private final String name;

    private final String lecturerName;

    private final String description;

    private final SkillRating averageRatings;

    private final int iconID;

    public ModuleInfo(String name, String lecturerName, String description, int iconID)
    {
        this.name = name;
        this.lecturerName = lecturerName;
        this.description = description;
        this.iconID = iconID;
        this.averageRatings = new SkillRating();
    }

    public String getName()
    {
        return this.name;
    }

    public String getLecturerName()
    {
        return this.lecturerName;
    }

    public String getDescription()
    {
        return this.description;
    }

    public SkillRating getAverageSkillRating()
    {
        return this.averageRatings;
    }

    public int getIconID()
    {
        return this.iconID;
    }
}
