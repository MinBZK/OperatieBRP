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

public class Xq01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();
    private static final String GEVRAAGDE_RUBRIEKEN = "010110020110020120";
    private static final String AANTAL = String.valueOf(GEVRAAGDE_RUBRIEKEN.length() / 6);
    private static final String ADRESFUNCTIE = "W";
    private static final String IDENTIFICATIE = "P";

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        Assert.assertEquals(0, bericht.getCategorieen().size());
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);

        final String formatted = bericht.format();
        Assert.assertNotNull(formatted);

        final Lo3Bericht parsed = getFactory().getBericht(formatted);
        Assert.assertNotNull(parsed);

        assertEquals("OngeldigBericht", parsed.getBerichtType());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatGeenPersoonsIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.parseInhoud("00018010130210006Henkie");
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatGeenAdresIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.parseInhoud("000220101301100101234567890");
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatLegePersoonsIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.parseInhoud("00012010070110000");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=}]]", bericht.getCategorieen().toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatLegeAdresIdentificerendeRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.parseInhoud("00012080071110000");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatANummer() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("000220101701100101234567890");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=1234567890}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatBsn() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00021010160120009123456789");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0120=123456789}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatGeslachtsnaam() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00018010130240006Jansen");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0240=Jansen}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatPostcode() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("000180801311600061234AA");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1160=1234AA}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatStraatnaam() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00023080181110011Dorpsstraat");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=Dorpsstraat}]]", bericht.getCategorieen().toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatNietGevraagdeRubriekAnummer() throws BerichtSyntaxException, BerichtInhoudException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.parseInhoud("00023080181110011Dorpsstraat");
    }

    @Test
    public void bevatNaamOpenbareRuimte() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00026080211115014Openbareruimte");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1115=Openbareruimte}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatPostcodeAdresIdentificatie() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("000180801311600061234AA");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1160=1234AA}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatIdentificatiecodeVerblijfsplaats() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00028080231180016abcdefghijklmnop");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1180=abcdefghijklmnop}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatIdentificatiecodeNummeraanduiding() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00028080231190016abcdefghijklmnop");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1190=abcdefghijklmnop}]]", bericht.getCategorieen().toString());
    }

    @Test
    public void bevatLocatiebeschrijving() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110");
        bericht.parseInhoud("00031080261210019Locatiebeschrijving");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1210=Locatiebeschrijving}]]", bericht.getCategorieen().toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatNietToegestaneOptioneleRubriek() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "A");
        bericht.parseInhoud("00045080181110011Dorpsstraat0101301100101234567890");
    }

    @Test(expected = BerichtInhoudException.class)
    public void bevatIndicatieOnjuist() throws BerichtInhoudException, BerichtSyntaxException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        bericht.parseInhoud("000350101701100101234567890600088410001O");
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xq01Bericht bericht = new Xq01Bericht();
        bericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);
        bericht.parseCategorieen(CATEGORIEEN);
        bericht.setBronPartijCode(BRON_GEMEENTE);
        bericht.setDoelPartijCode(DOEL_GEMEENTE);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setCategorieen(maakCategorieen());

        testFormatAndParseBericht(bericht);

        Assert.assertEquals("Xq01 Start een UC1004-persoon cyclus", "uc1004-adres", bericht.getStartCyclus());

        final Xq01Bericht controleBericht = new Xq01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        controleBericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, GEVRAAGDE_RUBRIEKEN);
        controleBericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, ADRESFUNCTIE);
        controleBericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, IDENTIFICATIE);
        controleBericht.parseCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());
        controleBericht.setCategorieen(maakCategorieen());

        Assert.assertEquals(bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(bericht.toString(), controleBericht.toString());
        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));
        Assert.assertEquals(GEVRAAGDE_RUBRIEKEN, bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals(Integer.valueOf(AANTAL), Integer.valueOf(bericht.getHeaderWaarde(Lo3HeaderVeld.AANTAL)));
        Assert.assertEquals(CATEGORIEEN, bericht.getCategorieen());
        Assert.assertEquals(bericht.getGerelateerdeAnummers(), controleBericht.getGerelateerdeAnummers());

        Assert.assertFalse(bericht.equals(new Lq01Bericht()));
    }
}
