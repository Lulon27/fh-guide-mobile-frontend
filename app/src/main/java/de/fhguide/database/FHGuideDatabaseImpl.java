package de.fhguide.database;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import de.fhguide.RequestHandler;
import de.fhguide.module.Module;

public class FHGuideDatabaseImpl extends FHGuideDatabase
{
    private static final String URL_GET_MODULES = "http://deerfindsadear.servebeer.com/users";

    private final ArrayList<Module> modules = new ArrayList<>();

    FHGuideDatabaseImpl(Context context)
    {
        super(context);
    }

    @Override
    public ArrayList<Module> getModules()
    {
        return this.modules;
    }

    @Override
    public void loadAllModules(Runnable onSuccess, Response.ErrorListener errorListener)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL_GET_MODULES, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        System.out.println(response.toString());
                        onSuccess.run();
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        errorListener.onErrorResponse(error);
                        error.printStackTrace();
                    }
                });
        RequestHandler.getInstance(this.getContext()).getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public Module getModuleByID(int moduleID)
    {
        for(int i = 0; i < this.modules.size(); ++i)
        {
            if(this.modules.get(i).getModuleID() == moduleID)
            {
                return this.modules.get(i);
            }
        }
        return null;
    }
}
