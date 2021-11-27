package de.fhguide;

import android.view.View;

public class Util
{
    public static int dpToPixels(int dp, View v)
    {
        final float scale = v.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}
