/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.junit.Test;

public class Lo3VerblijfplaatsInhoudTest {

    private static final Lo3String TEST_DATA = Lo3String.wrap("testdata");
    private static final Lo3String PUNT = Lo3String.wrap(".");
    private static final String STRING_1234 = "1234";
    private static final String STRAATNAAM = "straatnaam";
    private static final String OPENBARE_RUIMTE = "openbareRuimte";
    private static final String WOONPLAATSNAAM = "woonplaatsnaam";
    private static final String ID_VERBLIJFPLAATS = "idVerblijfplaats";
    private static final String ID_NUMMER = "idNummer";

    @Test
    @Definitie(Definities.DEF021)
    public void testPuntadres() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.straatnaam(PUNT);

        Lo3VerblijfplaatsInhoud inhoud = builder.build();
        assertTrue(inhoud.isPuntAdres());

        builder.gemeenteDeel(TEST_DATA);
        inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());

        builder.gemeenteDeel(null);
        builder.identificatiecodeNummeraanduiding(TEST_DATA);
        inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());

        builder.identificatiecodeNummeraanduiding(null);
        builder.identificatiecodeVerblijfplaats(TEST_DATA);
        inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());

        builder.identificatiecodeNummeraanduiding(TEST_DATA);
        builder.identificatiecodeVerblijfplaats(TEST_DATA);
        inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());
    }

    @Test
    @Definitie(Definities.DEF022)
    public void testNederlandsAdres() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.straatnaam(PUNT);
        builder.gemeenteDeel(TEST_DATA);
        Lo3VerblijfplaatsInhoud inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());
        assertTrue(inhoud.isNederlandsAdres());

        builder.straatnaam(TEST_DATA);
        builder.gemeenteDeel(null);
        inhoud = builder.build();
        assertFalse(inhoud.isPuntAdres());
        assertTrue(inhoud.isNederlandsAdres());

        builder.straatnaam(null);
        builder.locatieBeschrijving(TEST_DATA);
        assertTrue(inhoud.isNederlandsAdres());
    }

    @Test
    public void testEmigratie() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.straatnaam(TEST_DATA);
        Lo3VerblijfplaatsInhoud inhoud = builder.build();
        assertTrue(inhoud.isNederlandsAdres());
        assertFalse(inhoud.isEmigratie());

        builder.straatnaam(null);
        builder.landAdresBuitenland(new Lo3LandCode(STRING_1234));
        builder.vertrekUitNederland(new Lo3Datum(20120101));
        builder.adresBuitenland1(TEST_DATA);
        builder.adresBuitenland2(TEST_DATA);
        builder.adresBuitenland3(TEST_DATA);

        inhoud = builder.build();
        assertTrue(inhoud.isEmigratie());
    }

    @Test
    public void testImmigratie() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.straatnaam(TEST_DATA);
        Lo3VerblijfplaatsInhoud inhoud = builder.build();
        assertTrue(inhoud.isNederlandsAdres());
        assertFalse(inhoud.isImmigratie());

        builder.landVanwaarIngeschreven(new Lo3LandCode(STRING_1234));
        builder.vestigingInNederland(new Lo3Datum(20120101));

        inhoud = builder.build();
        assertTrue(inhoud.isImmigratie());
    }

    @Test
    public void tesGroepen04() {
        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3String.wrap("locatieBeschrijving"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen05() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        Lo3String.wrap(STRAATNAAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen06() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        Lo3String.wrap(STRAATNAAM),
                        Lo3String.wrap(OPENBARE_RUIMTE),
                        new Lo3Huisnummer(101),
                        null,
                        null,
                        null,
                        null,
                        Lo3String.wrap(WOONPLAATSNAAM),
                        Lo3String.wrap(ID_VERBLIJFPLAATS),
                        Lo3String.wrap(ID_NUMMER),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen08() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        Lo3String.wrap(STRAATNAAM),
                        Lo3String.wrap(OPENBARE_RUIMTE),
                        new Lo3Huisnummer(101),
                        Lo3Character.wrap('A'),
                        null,
                        null,
                        null,
                        Lo3String.wrap(WOONPLAATSNAAM),
                        Lo3String.wrap(ID_VERBLIJFPLAATS),
                        Lo3String.wrap(ID_NUMMER),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen09() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        Lo3String.wrap(STRAATNAAM),
                        Lo3String.wrap(OPENBARE_RUIMTE),
                        new Lo3Huisnummer(101),
                        Lo3Character.wrap('A'),
                        Lo3String.wrap("toev"),
                        null,
                        null,
                        Lo3String.wrap(WOONPLAATSNAAM),
                        Lo3String.wrap(ID_VERBLIJFPLAATS),
                        Lo3String.wrap(ID_NUMMER),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen10() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(),
                        null,
                        new Lo3Datum(20120101),
                        Lo3String.wrap(STRAATNAAM),
                        Lo3String.wrap(OPENBARE_RUIMTE),
                        new Lo3Huisnummer(101),
                        Lo3Character.wrap('A'),
                        Lo3String.wrap("toev"),
                        null,
                        Lo3String.wrap("1234AB"),
                        Lo3String.wrap(WOONPLAATSNAAM),
                        Lo3String.wrap(ID_VERBLIJFPLAATS),
                        Lo3String.wrap(ID_NUMMER),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen13() {

        final String message =
                maakLo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(STRING_1234),
                        new Lo3Datum(20120101),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new Lo3LandCode(STRING_1234),
                        new Lo3Datum(20120101),
                        Lo3String.wrap("adres1"),
                        Lo3String.wrap("adres2"),
                        Lo3String.wrap("adres3"),
                        null,
                        null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                        null);
        assertEquals("", message);

    }

    private String maakLo3VerblijfplaatsInhoud(
            final Lo3GemeenteCode gemeenteInschrijving,
            final Lo3Datum datumInschrijving,
            final Lo3FunctieAdres functieAdres,
            final Lo3String gemeenteDeel,
            final Lo3Datum aanvangAdreshouding,
            final Lo3String straatnaam,
            final Lo3String naamOpenbareRuimte,
            final Lo3Huisnummer huisnummer,
            final Lo3Character huisletter,
            final Lo3String huisnummertoevoeging,
            final Lo3AanduidingHuisnummer aanduidingHuisnummer,
            final Lo3String postcode,
            final Lo3String woonplaatsnaam,
            final Lo3String identificatiecodeVerblijfplaats,
            final Lo3String identificatiecodeNummeraanduiding,
            final Lo3String locatieBeschrijving,
            final Lo3LandCode landAdresBuitenland,
            final Lo3Datum vertrekUitNederland,
            final Lo3String adresBuitenland1,
            final Lo3String adresBuitenland2,
            final Lo3String adresBuitenland3,
            final Lo3LandCode landVanwaarIngeschreven,
            final Lo3Datum vestigingInNederland,
            final Lo3AangifteAdreshouding aangifteAdreshouding,
            final Lo3IndicatieDocument indicatieDocument) {
        new Lo3VerblijfplaatsInhoud(
                gemeenteInschrijving,
                datumInschrijving,
                functieAdres,
                gemeenteDeel,
                aanvangAdreshouding,
                straatnaam,
                naamOpenbareRuimte,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                aanduidingHuisnummer,
                postcode,
                woonplaatsnaam,
                identificatiecodeVerblijfplaats,
                identificatiecodeNummeraanduiding,
                locatieBeschrijving,
                landAdresBuitenland,
                vertrekUitNederland,
                adresBuitenland1,
                adresBuitenland2,
                adresBuitenland3,
                landVanwaarIngeschreven,
                vestigingInNederland,
                aangifteAdreshouding,
                indicatieDocument);
        return "";
    }
}
