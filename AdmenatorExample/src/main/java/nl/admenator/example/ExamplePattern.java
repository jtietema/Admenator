package nl.admenator.example;

import nl.admenator.InsertionPattern;

public class ExamplePattern implements InsertionPattern {

    private int every;

    public ExamplePattern(int insertEvery) {
        every = insertEvery;
    }

    @Override
    public boolean insertForIndex(int index) {
        return index % every == 0;
    }
}
