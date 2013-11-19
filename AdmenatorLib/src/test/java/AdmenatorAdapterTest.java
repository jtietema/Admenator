import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import nl.admenator.AdmenatorAdapter;
import nl.admenator.InsertionPattern;
import nl.admenator.ViewProvider;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AdmenatorAdapterTest {

    private Context context;
    private ArrayAdapter<String> adapter;
    private Adapter wrappedAdapter;

    @Before
    public void setUp() {
        context = Robolectric.application;
        adapter = new MyArrayAdapter(context);
        wrappedAdapter = new AdmenatorAdapter(adapter, new MyInsertionPattern(), new MyAdProvider());
    }

    @Test
    public void getCountWithTwoNormalViews() {
        adapter.addAll("test1", "test2");
        assertThat(wrappedAdapter.getCount(), is(2));
    }

    @Test
    public void getCountWithThreeNormalViews() {
        adapter.addAll("test1", "test2", "test3");
        assertThat(wrappedAdapter.getCount(), is(3));
    }

    @Test
    public void getCountWithOneAdViewAndFourNormalViews() {
        // will we get an ad or not? The name says *not*
        adapter.addAll("test1", "test2", "test3", "test4");
        assertThat(wrappedAdapter.getCount(), is(5));
    }

    @Test
    public void getCountWithOneAdViewsAndFiveNormalViews() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5");
        assertThat(wrappedAdapter.getCount(), is(6));
    }

    @Test
    public void getCountWithTwoAdViewsAndSixNormalViews() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6");
        assertThat(wrappedAdapter.getCount(), is(8));
    }

    @Test
    public void getCountWithTwoAdViewsAndSevenNormalViews() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6", "test7");
        assertThat(wrappedAdapter.getCount(), is(9));
    }

    @Test
    public void getViewTypeForViews() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6", "test7");
        assertThat(wrappedAdapter.getViewTypeCount(), is(2));

        assertThat(wrappedAdapter.getItemViewType(0), is(0));
        assertThat(wrappedAdapter.getItemViewType(1), is(0));
        assertThat(wrappedAdapter.getItemViewType(2), is(0));
        assertThat(wrappedAdapter.getItemViewType(3), is(1));
        assertThat(wrappedAdapter.getItemViewType(4), is(0));
        assertThat(wrappedAdapter.getItemViewType(5), is(0));
        assertThat(wrappedAdapter.getItemViewType(6), is(1));
        assertThat(wrappedAdapter.getItemViewType(7), is(0));
        assertThat(wrappedAdapter.getItemViewType(8), is(0));
    }

    @Test
    public void getViewInstances() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6", "test7");

        assertTrue(wrappedAdapter.getView(0, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(1, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(2, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(3, new View(context), null) instanceof AdView);
        assertTrue(wrappedAdapter.getView(4, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(5, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(6, new View(context), null) instanceof AdView);
        assertTrue(wrappedAdapter.getView(7, new View(context), null) instanceof TestView);
        assertTrue(wrappedAdapter.getView(8, new View(context), null) instanceof TestView);
    }

    @Test
    public void getItems() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6", "test7");

        assertThat((String) wrappedAdapter.getItem(0), is("test1"));
        assertThat((String) wrappedAdapter.getItem(1), is("test2"));
        assertThat((String) wrappedAdapter.getItem(2), is("test3"));
        assertNull(wrappedAdapter.getItem(3));
        assertThat((String) wrappedAdapter.getItem(4), is("test4"));
        assertThat((String) wrappedAdapter.getItem(5), is("test5"));
        assertNull(wrappedAdapter.getItem(6));
        assertThat((String) wrappedAdapter.getItem(7), is("test6"));
        assertThat((String) wrappedAdapter.getItem(8), is("test7"));
    }

    @Test
    public void getItemIds() {
        adapter.addAll("test1", "test2", "test3", "test4", "test5", "test6", "test7");

        assertThat(wrappedAdapter.getItemId(0), is(0L));
        assertThat(wrappedAdapter.getItemId(1), is(1L));
        assertThat(wrappedAdapter.getItemId(2), is(2L));
        assertThat(wrappedAdapter.getItemId(3), is(-1L));
        assertThat(wrappedAdapter.getItemId(4), is(3L));
        assertThat(wrappedAdapter.getItemId(5), is(4L));
        assertThat(wrappedAdapter.getItemId(6), is(-1L));
        assertThat(wrappedAdapter.getItemId(7), is(5L));
        assertThat(wrappedAdapter.getItemId(8), is(6L));

    }

    private class MyAdProvider implements ViewProvider {

        @Override
        public View getView(int index, View convertView, ViewGroup container) {
            return new AdView(convertView.getContext());
        }
    }

    private class MyInsertionPattern implements InsertionPattern {

        @Override
        public boolean insertForIndex(int index) {
            return index != 0 && index % 3 == 0;
        }
    }

    private class MyArrayAdapter extends ArrayAdapter<String> {

        public MyArrayAdapter(Context context) {
            super(context, 1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return new TestView(convertView.getContext());
        }
    }

    private class AdView extends View {

        public AdView(Context context) {
            super(context);
        }

        public AdView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public AdView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    }

    private class TestView extends View {

        public TestView(Context context) {
            super(context);
        }

        public TestView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public TestView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    }

}
