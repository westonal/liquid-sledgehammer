package com.coltsoftware.liquidsledgehammer;

import java.util.Iterator;

public final class IterableCounter {

    private IterableCounter() {
    }

    public static int count(Iterable<?> iterable) {
        return count(iterable.iterator());
    }

    public static int count(Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
