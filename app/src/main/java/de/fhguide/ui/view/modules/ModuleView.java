package de.fhguide.ui.view.modules;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import de.fhguide.R;
import de.fhguide.Util;
import de.fhguide.databinding.LayoutModuleViewBinding;
import de.fhguide.module.ModuleInfo;
import de.fhguide.module.Skill;
import de.fhguide.module.SkillRating;

public class ModuleView extends TableLayout
{
    private static final int SKILL_PADDING_TOP_DP = 4;
    private static final int SKILL_PADDING_SIDE_DP = 8;

    private LayoutModuleViewBinding binding;

    public ModuleView(Context context)
    {
        this(context, null);
    }

    public ModuleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //This is the main part of the module view (icon, name, description)
        this.binding = LayoutModuleViewBinding.inflate(LayoutInflater.from(this.getContext()), this, true);

        if(attrs != null)
        {
            this.applyAttributes(attrs);
        }
    }

    private void applyAttributes(AttributeSet attrs)
    {
        TypedArray a=getContext().obtainStyledAttributes(
                attrs,
                R.styleable.ModuleView);

        this.setModuleName(a.getString(R.styleable.ModuleView_moduleName));
        this.setModuleInfo(a.getString(R.styleable.ModuleView_moduleInfo));
        this.setModuleDescription(a.getString(R.styleable.ModuleView_moduleDescription));
        this.setModuleIconDrawable(a.getDrawable(R.styleable.ModuleView_moduleIcon));

        a.recycle();
    }

    public CharSequence getModuleName()
    {
        return this.binding.moduleName.getText();
    }

    public void setModuleName(CharSequence name)
    {
        this.binding.moduleName.setText(name);
    }

    public CharSequence getModuleInfo()
    {
        return this.binding.moduleInfo.getText();
    }

    public void setModuleInfo(CharSequence info)
    {
        this.binding.moduleInfo.setText(info);
    }

    public CharSequence getModuleDescription()
    {
        return this.binding.moduleDescription.getText();
    }

    public void setModuleDescription(CharSequence description)
    {
        this.binding.moduleDescription.setText(description);
    }

    public Drawable getModuleIcon()
    {
        return this.binding.moduleIcon.getDrawable();
    }

    public void setModuleIconDrawable(Drawable drawable)
    {
        this.binding.moduleIcon.setImageDrawable(drawable);
    }

    public void applyModuleInfo(@NonNull ModuleInfo module)
    {
        this.setModuleName(module.getName());
        this.setModuleInfo(module.getLecturerName());
        this.setModuleDescription(module.getDescription());
        this.setModuleIconDrawable(getResources().getDrawable(module.getIconID(), null));

        //Add skills

        final SkillRating rating = module.getAverageSkillRating();

        //All skills that are set for this module rating
        ArrayList<Skill> addedSkills = new ArrayList<Skill>(Skill.values().length);
        //All SkillViews that are added to this ModuleView
        ArrayList<SkillView> skillViewRefs = new ArrayList<>(addedSkills.size());

        View child;
        SkillView skillView;
        int i;
        //Get all skills that are set for this module rating
        for(Skill skill : Skill.values())
        {
            if(rating.isRatingSet(skill))
            {
                addedSkills.add(skill);
            }
        }
        //Get all SkillsViews that are already added in order to reuse them
        for(i = 0; i < this.getChildCount(); ++i)
        {
            child = this.getChildAt(i);
            if(child instanceof SkillView)
            {
                if(skillViewRefs.size() == addedSkills.size())
                {
                    //Remove all SkillViews that are more than needed
                    this.removeView(child);
                    --i;
                }
                else
                {
                    skillViewRefs.add((SkillView) child);
                }
            }
        }
        //Fill up SkillViews if needed
        for(i = skillViewRefs.size(); i < addedSkills.size(); ++i)
        {
            skillView = new SkillView(this.getContext());
            this.addView(skillView);
            skillViewRefs.add(skillView);
        }
        //Now we have references to all added SkillViews
        //skillViewRefs and addedSkills should have the same size now
        for(i = 0; i < addedSkills.size(); ++i)
        {
            skillView = skillViewRefs.get(i);

            int pad_top = Util.dpToPixels(SKILL_PADDING_TOP_DP, this);
            int pad_side = Util.dpToPixels(SKILL_PADDING_SIDE_DP, this);
            skillView.setPadding(pad_side, pad_top, pad_side, 0);

            final Skill skill = addedSkills.get(i);
            skillView.setSkillName(skill.getName());
            skillView.setSkillValue(rating.getRating(skill));
            skillView.setColor(skill.getColor());
            skillView.setIcon(getResources().getDrawable(R.drawable.placeholder_32));
        }
    }
}
