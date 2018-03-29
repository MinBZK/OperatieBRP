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

public class Xf01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();
    private static final String GEVRAAGDE_RUBRIEKEN = "020110020120";
    private static final String AANTAL = String.valueOf(GEVRAAGDE_RUBRIEKEN.length() / 6);
    private static final String ADRESFUNCTIE = "W";
    private static final String IDENTIFICATIE = "A";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xf01Bericht bericht = new Xf01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);

        Assert.assertEquals(0, bericht.getCategorieen().size());

        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "X");
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);
        testFormatAndParseBericht(bericht);
        Assert.assertEquals(0, bericht.getCategorieen().size());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xf01Bericht bericht = new Xf01Bericht();
        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        bericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);
        bericht.setCategorieen(CATEGORIEEN);
        bericht.setBronPartijCode(BRON_GEMEENTE);
        bericht.setDoelPartijCode(DOEL_GEMEENTE);
        bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(bericht);

        Assert.assertNull(bericht.getStartCyclus());

        final Xf01Bericht controleBericht = new Xf01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        controleBericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        controleBericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        controleBericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        controleBericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);
        controleBericht.setCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());

        Assert.assertEquals(bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(bericht.toString(), controleBericht.toString());
        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));
        Assert.assertEquals(GEVRAAGDE_RUBRIEKEN, bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals(Integer.valueOf(AANTAL), Integer.valueOf(bericht.getHeaderWaarde(Lo3HeaderVeld.AANTAL)));
        Assert.assertEquals(ADRESFUNCTIE, bericht.getHeaderWaarde(Lo3HeaderVeld.ADRESFUNCTIE));
        Assert.assertEquals(IDENTIFICATIE, bericht.getHeaderWaarde(Lo3HeaderVeld.IDENTIFICATIE));
        Assert.assertEquals(CATEGORIEEN, bericht.getCategorieen());

        Assert.assertFalse(bericht.equals(new Lq01Bericht()));
    }

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }
}
