package de.fhguide.ui.modules;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.fhguide.Util;
import de.fhguide.module.ModuleInfo;
import de.fhguide.ui.view.modules.ModuleView;

public class ModulesArrayAdapter extends ArrayAdapter<ModuleInfo>
{
    private static class ViewHolder
    {
        private ModuleView view;
    }

    public ModulesArrayAdapter(Context context, int textViewResourceId, ArrayList<ModuleInfo> items)
    {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = new ModuleView(this.getContext());
            int pad = Util.dpToPixels(16, convertView);
            convertView.setPadding(pad, pad, pad, pad);

            holder = new ViewHolder();
            holder.view = (ModuleView)convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModuleInfo item = getItem(position);
        if (item!= null) {
            holder.view.applyModuleInfo(item);
        }

        return convertView;
    }
}