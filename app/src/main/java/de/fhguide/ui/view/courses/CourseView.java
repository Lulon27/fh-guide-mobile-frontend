package de.fhguide.ui.view.courses;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import de.fhguide.Util;
import de.fhguide.course.Course;
import de.fhguide.course.CourseOverview;
import de.fhguide.databinding.LayoutCourseViewBinding;

public class CourseView extends LinearLayout
{
    private LayoutCourseViewBinding binding;

    public CourseView(Context context)
    {
        this(context, null);
    }

    public CourseView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.binding = LayoutCourseViewBinding.inflate(LayoutInflater.from(this.getContext()), this, true);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public void applyCourseInfo(@NonNull Course course)
    {
        CourseOverview courseOverview = course.getOverview();

        if(courseOverview == null)
        {
            throw new IllegalArgumentException("course must have a courseOverview");
        }

        this.binding.mainSection.removeAllViews();
        this.binding.infoSection.removeAllViews();
        this.binding.customSection.removeAllViews();


        for(String s : courseOverview.getSectionMain())
        {
            final TextView textName = new TextView(this.binding.mainSection.getContext());
            textName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textName.setPadding(8, 0, 8, 0);
            textName.setTextSize(12);
            textName.setText(s);
            this.binding.mainSection.addView(textName);
        }

        Util.createViewsFromPairList(courseOverview.getSectionInfo().getProperties(), this.binding.infoSection, 4, 8);
        Util.createViewsFromPairList(courseOverview.getSectionCustom().getProperties(), this.binding.customSection, 4, 8);
    }
}
