package nl.admenator.example;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import nl.admenator.AdmenatorAdapter;

public class ExampleAdActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_row, R.id.text, items);
        AdmenatorAdapter headerAdapter = new AdmenatorAdapter(adapter, new ExamplePattern(4), new HeaderProvider());
        AdmenatorAdapter wrapperAdapter = new AdmenatorAdapter(headerAdapter, new ExamplePattern(5), new MyAdViewProvider());
        setListAdapter(wrapperAdapter);
    }
}
