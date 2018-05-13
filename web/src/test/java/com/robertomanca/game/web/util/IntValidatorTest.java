package com.robertomanca.game.web.util;

import org.junit.Test;

import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class IntValidatorTest {

    @Test
    public void testValidateInt() {

        assertEquals(OptionalInt.of(5), IntValidator.validate("5"));
    }

    @Test
    public void testValidateNegativeInt() {

        assertEquals(OptionalInt.empty(), IntValidator.validate("-5"));
    }

    @Test
    public void testValidateNotValidInt() {

        assertEquals(OptionalInt.empty(), IntValidator.validate("mario"));
    }
}
