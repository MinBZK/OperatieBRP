/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Tf21BerichtTest extends AbstractLo3BerichtTestBasis {

    @Test
    public void testLeeg() {
        final Tf21Bericht tf21Bericht = new Tf21Bericht();
        tf21Bericht.setMessageId(MessageIdGenerator.generateId());
        assertEquals("Tf21", tf21Bericht.getBerichtType());
        assertEquals(null, tf21Bericht.getStartCyclus());
    }

    @Test
    public void testFoutredenG() throws IOException, BerichtInhoudException, ClassNotFoundException {

        final List<Lo3CategorieWaarde> tb02Inhoud = maakLijst3ASluiting();
        final Tb02Bericht tb02Bericht = new Tb02Bericht();
        tb02Bericht.setCategorieen(tb02Inhoud);
        tb02Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        tb02Bericht.setBronPartijCode("3333");
        tb02Bericht.setDoelPartijCode("5555");

        final Tf21Bericht tf21Bericht = new Tf21Bericht(tb02Bericht, Tf21Bericht.Foutreden.G, "3333");
        tf21Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf21Bericht);
        assertEquals("Tf21", tf21Bericht.getBerichtType());
        assertEquals(null, tf21Bericht.getStartCyclus());
        assertEquals(Collections.emptyList(), tf21Bericht.getGerelateerdeAnummers());
    }

    @Test
    public void testFoutredenV() throws IOException, BerichtInhoudException, ClassNotFoundException {

        final List<Lo3CategorieWaarde> tb02Inhoud = maakLijst3ASluiting();
        final Tb02Bericht tb02Bericht = new Tb02Bericht();
        tb02Bericht.setCategorieen(tb02Inhoud);
        tb02Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        tb02Bericht.setBronPartijCode("3333");
        tb02Bericht.setDoelPartijCode("5555");

        final Tf21Bericht tf21Bericht = new Tf21Bericht(tb02Bericht, Tf21Bericht.Foutreden.V, "3333");
        tf21Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf21Bericht);
        assertEquals("Tf21", tf21Bericht.getBerichtType());
        assertEquals(null, tf21Bericht.getStartCyclus());
        assertEquals(Collections.emptyList(), tf21Bericht.getGerelateerdeAnummers());
    }


    private List<Lo3CategorieWaarde> maakLijst3ASluiting() {
        return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
                put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                put(Lo3ElementEnum.ELEMENT_0230, "van");
                put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                put(Lo3ElementEnum.ELEMENT_0310, "1970101");
                put(Lo3ElementEnum.ELEMENT_0320, "0599");
                put(Lo3ElementEnum.ELEMENT_0330, "6030");
                put(Lo3ElementEnum.ELEMENT_0410, "V");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0210, "Karel");
                put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
                put(Lo3ElementEnum.ELEMENT_0310, "19800101");
                put(Lo3ElementEnum.ELEMENT_0320, "0626");
                put(Lo3ElementEnum.ELEMENT_0330, "6030");
                put(Lo3ElementEnum.ELEMENT_0410, "M");
                put(Lo3ElementEnum.ELEMENT_0610, "20160101");
                put(Lo3ElementEnum.ELEMENT_0620, "0626");
                put(Lo3ElementEnum.ELEMENT_0630, "6030");
                put(Lo3ElementEnum.ELEMENT_1510, "H");
                put(Lo3ElementEnum.ELEMENT_8110, "3333");
                put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                put(Lo3ElementEnum.ELEMENT_8510, "20150917");
            }
        }));
    }
}
