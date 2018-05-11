package com.robertomanca.game.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class LevelTest {

    @Test
    public void equalsVerifierTest() {

        EqualsVerifier.forClass(Level.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .usingGetClass()
                .verify();
    }

}
