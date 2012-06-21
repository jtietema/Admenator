package nl.admenator;

import android.view.View;
import android.view.ViewGroup;

public interface ViewProvider {
    public View getView(int index, View convertView, ViewGroup container);
}
