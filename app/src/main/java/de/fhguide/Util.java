package de.fhguide;

import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class Util
{
    public static int dpToPixels(int dp, View v)
    {
        final float scale = v.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    public static void createViewsFromPairList(List<Pair<String, Object>> pairs, ViewGroup parent, int padV, int padH)
    {
        for(int i = 0; i < pairs.size(); ++i)
        {
            final TableRow row = new TableRow(parent.getContext());
            row.setPadding(0, padV, 0, padV);

            final TextView textName = new TextView(parent.getContext());
            textName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textName.setPadding(padH, 0, padH, 0);
            textName.setTextSize(12);
            textName.setText(pairs.get(i).first);

            final TextView textValue = new TextView(parent.getContext());
            textValue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textValue.setPadding(padH, 0, padH, 0);
            textValue.setTextSize(12);
            textValue.setText(String.valueOf(pairs.get(i).second));

            row.addView(textName);
            row.addView(textValue);
            parent.addView(row);

            if(i < pairs.size() - 1)
            {
                final View separator = new View(parent.getContext());
                separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                separator.setBackgroundColor(Color.parseColor("#CFCFCF"));
                parent.addView(separator);
            }
        }
    }
}
