package com.robertomanca.game.repository.model;

import com.robertomanca.game.repository.com.robertomanca.game.repository.model.ScoreDO;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class ScoreDOTest {

    @Test
    public void equalsVerifierTest() {

        EqualsVerifier.forClass(ScoreDO.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .usingGetClass()
                .verify();
    }
}
