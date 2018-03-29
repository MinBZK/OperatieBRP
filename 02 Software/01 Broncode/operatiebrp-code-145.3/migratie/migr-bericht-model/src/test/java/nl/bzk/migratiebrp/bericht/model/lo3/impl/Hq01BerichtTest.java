/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Hq01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();
    private static final String GEVRAAGDE_RUBRIEKEN = "020110020120";
    private static final String AANTAL = String.valueOf(GEVRAAGDE_RUBRIEKEN.length() / 6);

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }

    @Test
    public void empty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());

        final String formatted = bericht.format();
        Assert.assertNotNull(formatted);

        final Lo3Bericht parsed = getFactory().getBericht(formatted);
        Assert.assertNotNull(parsed);

        assertEquals("OngeldigBericht", parsed.getBerichtType());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatGeenIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("00018010130210006Henkie");
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatLegeIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("00012010070110000");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatANummer() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("000220101701100101234567890");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=1234567890}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatBsn() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("00021010160120009123456789");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0120=123456789}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatGeslachtsnaam() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("00018010130240006Jansen");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0240=Jansen}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatPostcode() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("000180801311600061234AA");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1160=1234AA}]]", bericht.getCategorieen().toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatIndicatieOnjuist() throws BerichtInhoudException, BerichtSyntaxException {
        final Hq01Bericht bericht = new Hq01Bericht();
        bericht.parseInhoud("000350101701100101234567890600088410001O");
    }

    @Test
    public void cyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Hq01Bericht hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        hq01Bericht.parseCategorieen(CATEGORIEEN);
        hq01Bericht.setBronPartijCode(BRON_GEMEENTE);
        hq01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        hq01Bericht.setMessageId(MessageIdGenerator.generateId());
        hq01Bericht.setCategorieen(maakCategorieen());

        testFormatAndParseBericht(hq01Bericht);

        assertEquals("Hq01 Start een UC1004-persoon cyclus", "uc1004-persoon", hq01Bericht.getStartCyclus());

        final Hq01Bericht controleBericht = new Hq01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        controleBericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        controleBericht.parseCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(hq01Bericht.getMessageId());
        controleBericht.setCategorieen(maakCategorieen());

        assertEquals(hq01Bericht.hashCode(), controleBericht.hashCode());
        assertEquals(hq01Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(controleBericht.equals(hq01Bericht));
        assertEquals(GEVRAAGDE_RUBRIEKEN, hq01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        assertEquals(Integer.valueOf(AANTAL), Integer.valueOf(hq01Bericht.getHeaderWaarde(Lo3HeaderVeld.AANTAL)));
        assertEquals(CATEGORIEEN, hq01Bericht.getCategorieen());
        assertEquals(hq01Bericht.getGerelateerdeAnummers(), controleBericht.getGerelateerdeAnummers());
    }
}
