package nl.admenator.example;

import android.*;
import android.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import nl.admenator.ViewProvider;

public class HeaderProvider implements ViewProvider {
    @Override
    public View getView(int index, View convertView, ViewGroup container) {
        if (convertView == null)
            convertView = new TextView(container.getContext());
        ((TextView) convertView).setText(" * Header * ");
        return convertView;
    }
}
