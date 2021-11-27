package de.fhguide.ui.view.modules;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import de.fhguide.R;
import de.fhguide.databinding.LayoutModuleViewBinding;
import de.fhguide.module.ModuleInfo;

public class ModuleView extends TableLayout
{
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
        this.setModuleIconDrawable(module.getIcon());
    }
}
