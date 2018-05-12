package com.robertomanca.game.injector;

import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class InjectorManagerTest {

    private InjectorManager injectorManager;

    @Before
    public void setUp() {
        injectorManager = InjectorManager.INSTANCE;
    }

    @After
    public void clear() throws NoSuchFieldException, IllegalAccessException {
        final Field singletonsField = InjectorManager.class.getDeclaredField("singletons");
        singletonsField.setAccessible(true);
        singletonsField.set(injectorManager, new HashMap<>());

        final Field bindingsField = InjectorManager.class.getDeclaredField("bindings");
        bindingsField.setAccessible(true);
        bindingsField.set(injectorManager, new HashMap<>());
    }

    @Test
    public void testConfigureSingleton() {

        final String object = new String();
        injectorManager.configureSingleton(Object.class, object);

        assertEquals(object, injectorManager.getInstance(Object.class));
    }

    @Test
    public void testGetInstanceMissing() {

        assertNull(injectorManager.getInstance(Object.class));
    }

    @Test
    public void testGetBindingInstanceMissing() {

        injectorManager.configureSingleton(Object.class, new String());
        assertNull(injectorManager.getInstance(List.class));
    }

    @Test
    public void testConfigureBinding() {

        injectorManager.configureBinding(List.class, ArrayList.class);

        assertEquals(new ArrayList<>(), injectorManager.getInstance(List.class));
    }
}
