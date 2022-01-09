package de.fhguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.VolleyError;

import de.fhguide.database.FHGuideDatabase;
import de.fhguide.module.Module;
import de.fhguide.ui.modules.ModuleDetailsFragment;

public class ModuleDetailsActivity extends NetContentActivity
{
    @Override
    protected void onContentLoaded(Bundle savedInstanceState)
    {
        this.showContent(savedInstanceState);
    }

    @Override
    public NetContentLoader getContentLoaders()
    {
        return new NetContentLoader()
        {
            @Override
            public void loadContent()
            {
                Bundle extras = ModuleDetailsActivity.this.getIntent().getExtras();
                int moduleID = extras.getInt("moduleID", -1);

                FHGuideDatabase db = FHGuideDatabase.getInstance(ModuleDetailsActivity.this);

                Module module = db.getModuleByID(moduleID);

                if(module == null)
                {
                    this.onError(new VolleyError("Module not found by ID " + moduleID));
                    return;
                }
                FHGuideDatabase.getInstance(ModuleDetailsActivity.this).loadModuleDetails(moduleID, this::onSuccess, this::onError);
            }
        };
    }

    private void showContent(Bundle savedInstanceState)
    {
        setContentView(R.layout.module_details_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ModuleDetailsFragment.newInstance())
                    .commitNow();
        }
    }
}