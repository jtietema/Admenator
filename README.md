## Admenator

Admenator aims to make it easy to insert different views into an
adapter. More specifically it tries to make inserting ads in your
adapter painless and easy, but you can use it for headers and other
views too.

### Usage

You need to create two classes when using Admenator. First you need a
class that implments InsertionPattern. This is a class contains your
logic for when a custom view needs to be inserted.

```java
public class ExamplePattern implements InsertionPattern {

    @Override
    public boolean insertForIndex(int index) {
        // this class will insert a view on every fifth index starting at 0
        return index % 5 == 0;
    }
}
```

Next you need to create a class that provides the View that needs to be
inserted. This class implements ViewProvider. Which basically is the
getView method from the adapter.

```java
public class MyAdViewProvider implements ViewProvider {

    @Override
    public View getView(int index, View convertView, ViewGroup container) {
        if (convertView == null)
            convertView = new TextView(container.getContext());
        ((TextView) convertView).setText("Ad #" + index);
        return convertView;
    }
}
```

That's it! Now just pass your InsertionPattern and your ViewProvider
into the AdmenatorAdapter together with your original adapter.
