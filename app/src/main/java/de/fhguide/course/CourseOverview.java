package de.fhguide.course;

import java.util.ArrayList;
import java.util.List;

public class CourseOverview
{
    private String name;
    private List<String> sectionMain;
    private CourseInfoSection sectionInfo;
    private CourseInfoSection sectionCustom;

    public CourseOverview(String name)
    {
        this.name = name;
        this.sectionMain = new ArrayList<>();
        this.sectionInfo = new CourseInfoSection("course.section.info");
        this.sectionCustom = new CourseInfoSection("course.section.custom");
    }

    public List<String> getSectionMain()
    {
        return this.sectionMain;
    }

    public CourseInfoSection getSectionInfo()
    {
        return this.sectionInfo;
    }

    public CourseInfoSection getSectionCustom()
    {
        return this.sectionCustom;
    }
}
