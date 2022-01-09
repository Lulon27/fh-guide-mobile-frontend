package de.fhguide.database;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import de.fhguide.module.Module;

public abstract class FHGuideDatabase
{
    private static final int IMPL_REAL = 1;
    private static final int IMPL_DUMMY = 2;

    //Change to switch implementation
    private static final int IMPL_SELECTED = IMPL_REAL;

    private static FHGuideDatabase instance = null;

    public static synchronized FHGuideDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            switch (IMPL_SELECTED)
            {
                case IMPL_REAL: instance = new FHGuideDatabaseImpl(context); break;
                case IMPL_DUMMY: instance = new FHGuideDatabaseDummy(context); break;
            }
        }
        return instance;
    }

    private final Context context;

    public FHGuideDatabase(Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return this.context;
    }

    public abstract void loadAllModules(Runnable onSuccess, Response.ErrorListener errorListener);

    public abstract void loadModuleDetails(int moduleID, Runnable onSuccess, Response.ErrorListener errorListener);

    public abstract ArrayList<Module> getModules();

    public abstract Module getModuleByID(int moduleID);
}
