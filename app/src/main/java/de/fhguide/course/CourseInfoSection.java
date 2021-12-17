package de.fhguide.course;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoSection
{
    private String title;

    private List<Pair<String, Object>> properties;

    public CourseInfoSection(String title)
    {
        this.title = title;
        this.properties = new ArrayList<>();
    }

    public List<Pair<String, Object>> getProperties()
    {
        return this.properties;
    }
}
