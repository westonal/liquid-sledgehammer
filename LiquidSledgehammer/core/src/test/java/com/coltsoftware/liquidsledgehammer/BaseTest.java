package com.coltsoftware.liquidsledgehammer;

import org.junit.Before;

import java.util.Iterator;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public abstract class BaseTest {

    @Before
    public void setDefaultLocale() {
        Locale.setDefault(Locale.UK);
    }

    protected static void assertNotEqual(Object a, Object b) {
        assertFalse(equals(a, b));
    }

    protected static boolean equals(Object a, Object b) {
        if (a == b)
            return true;
        if (a == null)
            return false;
        return a.equals(b);
    }

    protected static int count(Iterable<?> iterable) {
        return IterableCounter.count(iterable.iterator());
    }

    protected static int count(Iterator<?> iterator) {
        assertNotNull(iterator);
        return IterableCounter.count(iterator);
    }

}
