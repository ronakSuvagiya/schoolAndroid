package com.apps.smartschoolmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.apps.smartschoolmanagement.utils.CodeValue;
import java.util.ArrayList;
import java.util.List;

public class SpinnerrAdapter extends ArrayAdapter {
//    ArrayList<CodeValue> animalList = new ArrayList();
    int resourceId;
    ArrayList<String> values = new ArrayList();

//    public SpinnerrAdapter(Context context, int textViewResourceId, ArrayList<CodeValue> objects) {
//        super(context, textViewResourceId, objects);
//        this.animalList = objects;
//        this.resourceId = textViewResourceId;
//    }

    public SpinnerrAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.values = objects;
        this.resourceId = textViewResourceId;
    }

    public int getCount() {
//        if (this.animalList.size() > 0) {
//            return super.getCount();
//        }
        return this.values.size() > 0 ? this.values.size() : 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        v = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(this.resourceId, null);
        TextView textView = (TextView) v.findViewById(16908308);
//        if (this.animalList.size() > 0) {
//            textView.setText(((CodeValue) getItem(position)).getCodeValue());
//        } else {
            textView.setText((CharSequence) this.values.get(position));
//        }
        return v;
    }
}
