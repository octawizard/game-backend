package com.robertomanca.game.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class IntegersTest {

    @Test
    public void constructor() throws NoSuchMethodException {

        Constructor<Integers> c = Integers.class.getDeclaredConstructor();
        c.setAccessible(true);
        try {
            c.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            assertEquals(e.getCause().getClass(), AssertionError.class);
        }
    }

    @Test
    public void testNotNegativePredicate() throws NoSuchMethodException {

        Assert.assertTrue(Integers.IS_NOT_NEGATIVE.test(1));
        Assert.assertTrue(Integers.IS_NOT_NEGATIVE.test(0));
        Assert.assertFalse(Integers.IS_NOT_NEGATIVE.test(-1));
    }

    @Test
    public void testNegativePredicate() throws NoSuchMethodException {

        Assert.assertFalse(Integers.IS_NEGATIVE.test(1));
        Assert.assertFalse(Integers.IS_NEGATIVE.test(0));
        Assert.assertTrue(Integers.IS_NEGATIVE.test(-1));
    }
}
