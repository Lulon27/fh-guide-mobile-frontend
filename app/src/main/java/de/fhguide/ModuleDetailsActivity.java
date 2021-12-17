package de.fhguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import de.fhguide.module.Modules;
import de.fhguide.ui.modules.ModuleDetailsFragment;

public class ModuleDetailsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_details_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ModuleDetailsFragment.newInstance())
                    .commitNow();
        }
    }
}