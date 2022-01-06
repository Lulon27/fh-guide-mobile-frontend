package de.fhguide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import de.fhguide.databinding.FragmentLoadingFailBinding;

public abstract class NetContentActivity extends AppCompatActivity
{
    private Bundle savedInstanceState;

    @Override
    protected final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final NetContentLoader loader = this.getContentLoaders();
        if(loader == null)
        {
            throw new IllegalStateException("getContentLoaders must not return null");
        }

        this.setContentView(R.layout.fragment_loading);

        this.savedInstanceState = savedInstanceState;
        loader.setInfo(this);
        loader.loadContent();
    }

    protected abstract void onContentLoaded(Bundle savedInstanceState);

    protected void onLoadingError(Bundle savedInstanceState, VolleyError err)
    {
        final FragmentLoadingFailBinding lfBinding = FragmentLoadingFailBinding.inflate(getLayoutInflater());
        lfBinding.errorText.setText("Failed to connect.\nTry again.\n\nError:\n" + err);
        this.setContentView(lfBinding.getRoot());
    }

    public abstract NetContentLoader getContentLoaders();

    final void onSuccess()
    {
        this.onContentLoaded(this.savedInstanceState);
    }

    final void onError(VolleyError err)
    {
        this.onLoadingError(this.savedInstanceState, err);
    }
}
