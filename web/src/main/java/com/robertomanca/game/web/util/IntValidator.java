package com.robertomanca.game.web.util;

import java.util.OptionalInt;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class IntValidator {

    public static OptionalInt validate(final String stringInt) {
        try {
            Integer integer = Integer.valueOf(stringInt);
            if (integer < 0) {
                return OptionalInt.empty();
            }
            return OptionalInt.of(integer);
        } catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }
}
