package de.fhguide.ui.view.modules;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableRow;

import de.fhguide.R;
import de.fhguide.Util;
import de.fhguide.databinding.LayoutSkillViewBinding;

public class SkillView extends TableRow
{
    private LayoutSkillViewBinding binding;

    public SkillView(Context context)
    {
        super(context);
        this.initSkillView();
    }

    public SkillView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.initSkillView();
        this.applyAttributes(attrs);
    }

    private void initSkillView()
    {
        this.binding = LayoutSkillViewBinding.inflate(LayoutInflater.from(this.getContext()), this, false);
        ViewGroup v = (ViewGroup)this.binding.ratingIcon.getParent();
        v.removeView(this.binding.ratingIcon);
        v.removeView(this.binding.ratingName);
        v.removeView(this.binding.ratingValue);

        this.addView(this.binding.ratingIcon);
        this.addView(this.binding.ratingName);
        this.addView(this.binding.ratingValue);
    }

    private void applyAttributes(AttributeSet attrs)
    {
        TypedArray a=getContext().obtainStyledAttributes(
                attrs,
                R.styleable.SkillView);

        this.setIcon(a.getDrawable(R.styleable.SkillView_skillIcon));
        this.setSkillName(a.getString(R.styleable.SkillView_skillName));
        this.setSkillValue(a.getFraction(R.styleable.SkillView_skillValue, 1, 1, 0));
        this.setColor(a.getColor(R.styleable.SkillView_skillColor, 0));

        a.recycle();
    }

    public CharSequence getSkillName()
    {
        return this.binding.ratingName.getText();
    }

    public void setSkillName(CharSequence name)
    {
        this.binding.ratingName.setText(name);
    }

    public int getSkillValue()
    {
        return this.binding.ratingValue.getProgress();
    }

    public void setSkillValue(float value)
    {
        this.binding.ratingValue.setProgress((int)(value * 100.0F));
    }

    public Drawable getIcon()
    {
        return this.binding.ratingIcon.getDrawable();
    }

    public void setIcon(Drawable icon)
    {
        this.binding.ratingIcon.setImageDrawable(icon);
    }

    public void setColor(int color)
    {
        this.binding.ratingValue.setProgressTintList(ColorStateList.valueOf(color));
    }

    public ColorStateList getColor()
    {
        return this.binding.ratingValue.getProgressTintList();
    }
}
