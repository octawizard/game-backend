package com.robertomanca.game.util;

import java.util.function.Predicate;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class Integers {

    public static final Predicate<Integer> IS_POSITIVE = i -> i > 0;

    public static final Predicate<Integer> IS_NEGATIVE = i -> i < 0;

    public static final Predicate<Integer> IS_NOT_NEGATIVE = IS_NEGATIVE.negate();
}
