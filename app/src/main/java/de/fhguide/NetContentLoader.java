package de.fhguide;

import com.android.volley.VolleyError;

public abstract class NetContentLoader
{
    private NetContentActivity ac;

    public abstract void loadContent();

    void setInfo(NetContentActivity ac)
    {
        this.ac = ac;
    }

    protected final void onSuccess()
    {
        this.ac.onSuccess();
    }

    protected final void onError(VolleyError err)
    {
        this.ac.onError(err);
    }
}
