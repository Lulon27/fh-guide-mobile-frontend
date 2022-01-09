package de.fhguide.database;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

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
import de.fhguide.course.Course;
import de.fhguide.course.CourseOverview;
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
                (Request.Method.GET, URL_GET_MODULES, null, (response) ->
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
                }, (error) ->
                {
                    errorListener.onErrorResponse(error);
                    error.printStackTrace();
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
    public void loadModuleDetails(int moduleID, Runnable onSuccess, Response.ErrorListener errorListener)
    {
        Module module = this.getModuleByID(moduleID);
        if(module == null)
        {
            errorListener.onErrorResponse(new VolleyError("Module must be loaded first"));
            return;
        }

        final String url = "https://fh-guide.hopto.org/Module/" + moduleID + "/course";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, (response) ->
                {
                    try
                    {
                        loadModulesDetailsFromJSON(module, response);
                    }
                    catch(JSONException e)
                    {
                        errorListener.onErrorResponse(new VolleyError("Incorrect JSON schema"));
                        return;
                    }
                    if(!module.getCourses().isEmpty())
                    {
                        //onSuccess is called in loadZoomInfo()
                        loadZoomInfo(module.getCourses().get(0), onSuccess, errorListener);
                        return;
                    }
                    onSuccess.run();
                }, (error) ->
                {
                    errorListener.onErrorResponse(error);
                    error.printStackTrace();
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

    private void loadZoomInfo(Course course, Runnable onSuccess, Response.ErrorListener errorListener)
    {


        final String urlZoom = "https://fh-guide.hopto.org/Course/" + course.getCourseID() + "/zoom";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlZoom, null, (response) ->
                {
                    try
                    {
                        loadZoomInfoFromJSON(course, response);
                    }
                    catch(JSONException e)
                    {
                        errorListener.onErrorResponse(new VolleyError("Incorrect JSON schema"));
                        return;
                    }
                    onSuccess.run();
                }, (error) ->
                {
                    errorListener.onErrorResponse(error);
                    error.printStackTrace();
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

    private void loadZoomInfoFromJSON(Course course, JSONObject obj) throws JSONException
    {
        String zoomLink = obj.getString("link");
        String zoomPw = obj.getString("password");
        course.getOverview().getSectionCustom().getProperties().add(new Pair<>("Zoom-Link", zoomLink));
        course.getOverview().getSectionCustom().getProperties().add(new Pair<>("Zoom-PW", zoomPw));
    }

    private void loadModulesDetailsFromJSON(Module module, JSONArray array) throws JSONException
    {
        String courseName;
        String courseDescription;
        String courseRoom;
        int courseID;
        CourseOverview overview;
        Course course;
        for(int i = 0; i < array.length(); ++i)
        {
            final JSONObject courseObj = array.getJSONObject(i);

            courseName = courseObj.getString("name");
            courseDescription = courseObj.getString("description");
            courseRoom = courseObj.getString("room");
            courseID = courseObj.getInt("courseId");

            overview = new CourseOverview(courseName);
            overview.getSectionMain().add(courseDescription);
            overview.getSectionInfo().getProperties().add(new Pair<>("Raum", courseRoom));
            course = new Course(courseID, overview);

            module.getCourses().add(course);
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
