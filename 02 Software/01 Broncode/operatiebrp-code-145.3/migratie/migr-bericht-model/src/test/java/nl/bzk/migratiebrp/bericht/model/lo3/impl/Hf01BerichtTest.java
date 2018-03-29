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

public class Hf01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();
    private static final String GEVRAAGDE_RUBRIEKEN = "020110020120";
    private static final String AANTAL = String.valueOf(GEVRAAGDE_RUBRIEKEN.length() / 6);

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Hf01Bericht bericht = new Hf01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);

        Assert.assertEquals(0, bericht.getCategorieen().size());

        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "V");
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.GEMEENTE, "0517");
        testFormatAndParseBericht(bericht);
        Assert.assertEquals(0, bericht.getCategorieen().size());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Hf01Bericht Hf01Bericht = new Hf01Bericht();
        Hf01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        Hf01Bericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        Hf01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        Hf01Bericht.setCategorieen(CATEGORIEEN);
        Hf01Bericht.setBronPartijCode(BRON_GEMEENTE);
        Hf01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        Hf01Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(Hf01Bericht);

        Assert.assertNull(Hf01Bericht.getStartCyclus());

        final Hf01Bericht controleBericht = new Hf01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        controleBericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        controleBericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        controleBericht.setCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(Hf01Bericht.getMessageId());

        Assert.assertEquals(Hf01Bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(Hf01Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(Hf01Bericht.equals(Hf01Bericht));
        Assert.assertTrue(controleBericht.equals(Hf01Bericht));
        Assert.assertEquals(GEVRAAGDE_RUBRIEKEN, Hf01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals(Integer.valueOf(AANTAL), Integer.valueOf(Hf01Bericht.getHeaderWaarde(Lo3HeaderVeld.AANTAL)));
        Assert.assertEquals(CATEGORIEEN, Hf01Bericht.getCategorieen());

        Assert.assertFalse(Hf01Bericht.equals(new Lq01Bericht()));

        Hf01Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, "0517");
        Assert.assertEquals("0517", Hf01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEMEENTE));
    }

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }
}
