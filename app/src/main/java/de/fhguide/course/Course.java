package de.fhguide.course;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    private final int courseID;

    private final CourseOverview overview;

    public Course(int courseID, CourseOverview overview)
    {
        this.courseID = courseID;
        this.overview = overview;
    }

    public CourseOverview getOverview()
    {
        return this.overview;
    }

    public int getCourseID()
    {
        return this.courseID;
    }
}
