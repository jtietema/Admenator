package nl.admenator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

public class AdmenatorAdapter extends BaseAdapter implements WrapperListAdapter {

    private ListAdapter wrappedAdapter;
    private InsertionPattern pattern;
    private ViewProvider viewProvider;

    public AdmenatorAdapter(ListAdapter adapter, InsertionPattern pattern, ViewProvider viewProvider) {
        wrappedAdapter = adapter;
        this.pattern = pattern;
        this.viewProvider = viewProvider;
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return wrappedAdapter;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (pattern.insertForIndex(position)) {
            return super.getViewTypeCount();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return getTotalCount(wrappedAdapter.getCount());
    }

    @Override
    public Object getItem(int i) {
        if (pattern.insertForIndex(i))
            return null;
        return wrappedAdapter.getItem(i - getOffsetForIndex(i));
    }

    @Override
    public long getItemId(int i) {
        if (pattern.insertForIndex(i))
            return 0;
        return wrappedAdapter.getItemId(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (pattern.insertForIndex(i))
            return viewProvider.getView(i, view, viewGroup);
        return wrappedAdapter.getView(i - getOffsetForIndex(i), view, viewGroup);
    }

    private int getTotalCount(int countWrappedAdapter) {
        int offset = 0;
        int totalListSize = countWrappedAdapter;

        for (int i = 0; i < totalListSize; i++) {
            if (pattern.insertForIndex(i)) {
                offset++;
                totalListSize++;
            }
        }

        return countWrappedAdapter + offset;
    }

    private int getOffsetForIndex(int index) {
        int offset = 0;

        for (int i = 0; i < index; i++) {
            if (pattern.insertForIndex(i)) {
                offset++;
            }
        }

        return offset;
    }
}
