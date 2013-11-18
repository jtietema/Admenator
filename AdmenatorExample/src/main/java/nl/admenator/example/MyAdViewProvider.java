package nl.admenator.example;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import nl.admenator.ViewProvider;

public class MyAdViewProvider implements ViewProvider {

    @Override
    public View getView(int index, View convertView, ViewGroup container) {
        if (convertView == null)
            convertView = new TextView(container.getContext());
        ((TextView) convertView).setText("Ad #" + index);
        return convertView;
    }
}
