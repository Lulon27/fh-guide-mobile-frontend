package de.fhguide.database;

import android.content.Context;
import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import de.fhguide.R;
import de.fhguide.RequestHandler;
import de.fhguide.module.Module;
import de.fhguide.module.ModuleInfo;

public class FHGuideDatabaseImpl extends FHGuideDatabase
{
    private static final String URL_GET_MODULES = "https://fh-guide.hopto.org/Module";
    private static final String AUTHORIZATION = "Basic d2hhdF9pc19qZWZmOndvcmRwYXNzMQ==";

    private final ArrayList<Module> modules = new ArrayList<>();

    FHGuideDatabaseImpl(Context context)
    {
        super(context);

        TrustManager[] trustAllCerts = new TrustManager[]
        {
            new X509TrustManager()
            {
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };

        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch(NoSuchAlgorithmException | KeyManagementException e)
        {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultHostnameVerifier(((hostname, session) -> true));
    }

    @Override
    public ArrayList<Module> getModules()
    {
        return this.modules;
    }

    @Override
    public void loadAllModules(Runnable onSuccess, Response.ErrorListener errorListener)
    {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, URL_GET_MODULES, null, new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try
                        {
                            loadModulesFromJSON(response);
                        }
                        catch(JSONException e)
                        {
                            errorListener.onErrorResponse(new VolleyError("Incorrect JSON schema"));
                            return;
                        }
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
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", AUTHORIZATION);
                return params;
            }
        };
        RequestHandler.getInstance(this.getContext()).getRequestQueue().add(jsonObjectRequest);
    }

    private void loadModulesFromJSON(JSONArray array) throws JSONException
    {
        modules.clear();

        String moduleName;
        String moduleDescription;
        String moduleLecturer = "Dozent";
        String moduleDeps;
        int moduleIcon = R.drawable.placeholder_256;
        int moduleID;
        ModuleInfo info;
        Module module;
        for(int i = 0; i < array.length(); ++i)
        {
            final JSONObject moduleObj = array.getJSONObject(i);

            moduleName = moduleObj.getString("name");
            moduleDescription = moduleObj.getString("description");
            moduleDeps = moduleObj.getString("dependencies");
            moduleID = moduleObj.getInt("moduleId");

            info = new ModuleInfo(moduleName, moduleLecturer, moduleDescription, moduleIcon);
            module = new Module(moduleID, info);

            module.getProperties().add(new Pair<>("Voraussetzung", moduleDeps));
            this.modules.add(module);
        }
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
