/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;

import org.junit.Test;

/**
 * Unittest voor de static methodes van {@link Entiteit}.
 */
public class EntiteitTest {

    @Test
    public void testVerzamelAlleHistorieAttributenVoorEntiteit() {
        assertEquals(7, Entiteit.verzamelAlleHistorieAttributen(PersoonGeboorteHistorie.class).length);
        assertEquals(10, Entiteit.verzamelAlleHistorieAttributen(PersoonNationaliteitHistorie.class).length);
        assertEquals(2, Entiteit.verzamelAlleHistorieAttributen(DienstHistorie.class).length);
    }

    @Test
    public void testIsFieldConstant() {
        int aantalConstantFields = 0;
        int aantalGeenConstantFields = 0;
        for (Field veld : TestClass.class.getDeclaredFields()) {
            if (!veld.isSynthetic()) {
                if (Entiteit.isFieldConstant(veld)) {
                    aantalConstantFields++;
                } else {
                    aantalGeenConstantFields++;
                }
            }
        }
        assertEquals(2, aantalConstantFields);
        // Declared fields leest ook een "this" uit.
        assertEquals(1, aantalGeenConstantFields);
    }

    @Test
    public void testTimestampNull() {
        assertNull(Entiteit.timestamp(null));
    }

    @Test
    public void testTimeStamp() {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Timestamp kopie = Entiteit.timestamp(timestamp);
        assertNotSame(timestamp, kopie);
        assertEquals(timestamp, kopie);
    }

    private class TestClass {
        public static final String PUBLIC_CONSTANTE = "Public Constante";
        private static final String PRIVATE_CONSTANTE = "Private Constante";
        private String geenConstante = "geenConstante";
    }
}
