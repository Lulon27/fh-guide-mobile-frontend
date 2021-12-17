package de.fhguide.module;

import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.fhguide.course.Course;

public class Module
{
    private final ModuleInfo overview;
    private final int moduleID;

    private List<Pair<String, Object>> moduleProperties;

    private List<Course> courses;

    public Module(int moduleID, ModuleInfo overview)
    {
        this.moduleID = moduleID;
        this.overview = overview;

        this.moduleProperties = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public int getModuleID()
    {
        return this.moduleID;
    }

    public ModuleInfo getOverview()
    {
        return this.overview;
    }

    public List<Pair<String, Object>> getProperties()
    {
        return this.moduleProperties;
    }

    public List<Course> getCourses()
    {
        return this.courses;
    }
}
