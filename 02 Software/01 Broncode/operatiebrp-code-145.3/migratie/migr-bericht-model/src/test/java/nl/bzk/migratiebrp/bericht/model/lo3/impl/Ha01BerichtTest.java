/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.List;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

public class Ha01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ha01Bericht bericht = new Ha01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);

        Assert.assertEquals(0, bericht.getCategorieen().size());

        bericht.setHeader(Lo3HeaderVeld.STATUS, "A");
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.DATUM, "20160101");
        testFormatAndParseBericht(bericht);
        Assert.assertEquals(0, bericht.getCategorieen().size());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ha01Bericht Ha01Bericht = new Ha01Bericht();
        Ha01Bericht.setHeader(Lo3HeaderVeld.STATUS, "A");
        Ha01Bericht.setHeader(Lo3HeaderVeld.DATUM, "20160101");
        Ha01Bericht.setCategorieen(CATEGORIEEN);
        Ha01Bericht.setBronPartijCode(BRON_GEMEENTE);
        Ha01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        Ha01Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(Ha01Bericht);

        Assert.assertNull(Ha01Bericht.getStartCyclus());

        final Ha01Bericht controleBericht = new Ha01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.STATUS, "A");
        controleBericht.setHeader(Lo3HeaderVeld.DATUM, "20160101");
        controleBericht.setCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(Ha01Bericht.getMessageId());

        Assert.assertEquals(Ha01Bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(Ha01Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(Ha01Bericht.equals(Ha01Bericht));
        Assert.assertTrue(controleBericht.equals(Ha01Bericht));
        Assert.assertEquals("20160101", Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.DATUM));
        Assert.assertEquals(Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.DATUM), Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.DATUM));
        Assert.assertEquals("A", Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.STATUS));
        Assert.assertEquals(Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.STATUS), Ha01Bericht.getHeaderWaarde(Lo3HeaderVeld.STATUS));
        Assert.assertEquals(CATEGORIEEN, Ha01Bericht.getCategorieen());

        Assert.assertFalse(Ha01Bericht.equals(new Lq01Bericht()));
    }

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }
}
