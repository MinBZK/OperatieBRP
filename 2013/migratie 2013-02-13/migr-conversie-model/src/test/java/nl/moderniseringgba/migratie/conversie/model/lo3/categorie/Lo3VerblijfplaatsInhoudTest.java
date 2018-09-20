/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import static org.junit.Assert.assertEquals;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Test;

public class Lo3VerblijfplaatsInhoudTest {

    @Test
    public void tesGroepen04() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), null, null, null,
                        null, null, null, null, null, null, null, "locatieBeschrijving", null, null, null, null,
                        null, null, null, Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen05() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), "straatnaam", null,
                        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        null, Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen06() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), "straatnaam",
                        "openbareRuimte", new Lo3Huisnummer(101), null, null, null, null, "woonplaatsnaam",
                        "idVerblijfplaats", "idNummer", null, null, null, null, null, null, null, null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen08() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), "straatnaam",
                        "openbareRuimte", new Lo3Huisnummer(101), 'A', null, null, null, "woonplaatsnaam",
                        "idVerblijfplaats", "idNummer", null, null, null, null, null, null, null, null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen09() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), "straatnaam",
                        "openbareRuimte", new Lo3Huisnummer(101), 'A', "toev", null, null, "woonplaatsnaam",
                        "idVerblijfplaats", "idNummer", null, null, null, null, null, null, null, null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen10() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101),
                        Lo3FunctieAdresEnum.BRIEFADRES.asElement(), null, new Lo3Datum(20120101), "straatnaam",
                        "openbareRuimte", new Lo3Huisnummer(101), 'A', "toev", null, "1234AB", "woonplaatsnaam",
                        "idVerblijfplaats", "idNummer", null, null, null, null, null, null, null, null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);
    }

    @Test
    public void tesGroepen13() {

        final String message =
                maakLo3VerblijfplaatsInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(20120101), null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, new Lo3LandCode("1234"),
                        new Lo3Datum(20120101), "adres1", "adres2", "adres3", null, null,
                        Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(), null);
        assertEquals("", message);

    }

    // CHECKSTYLE:OFF - Parameters & returns
    public String maakLo3VerblijfplaatsInhoud(
    // CHECKSTYLE:ON
            final Lo3GemeenteCode gemeenteInschrijving,
            final Lo3Datum datumInschrijving,
            final Lo3FunctieAdres functieAdres,
            final String gemeenteDeel,
            final Lo3Datum aanvangAdreshouding,
            final String straatnaam,
            final String naamOpenbareRuimte,
            final Lo3Huisnummer huisnummer,
            final Character huisletter,
            final String huisnummertoevoeging,
            final Lo3AanduidingHuisnummer aanduidingHuisnummer,
            final String postcode,
            final String woonplaatsnaam,
            final String identificatiecodeVerblijfplaats,
            final String identificatiecodeNummeraanduiding,
            final String locatieBeschrijving,
            final Lo3LandCode landWaarnaarVertrokken,
            final Lo3Datum vertrekUitNederland,
            final String adresBuitenland1,
            final String adresBuitenland2,
            final String adresBuitenland3,
            final Lo3LandCode landVanwaarIngeschreven,
            final Lo3Datum vestigingInNederland,
            final Lo3AangifteAdreshouding aangifteAdreshouding,
            final Lo3IndicatieDocument indicatieDocument) {
        try {
            // final Lo3VerblijfplaatsInhoud inhoud =
            new Lo3VerblijfplaatsInhoud(gemeenteInschrijving, datumInschrijving, functieAdres, gemeenteDeel,
                    aanvangAdreshouding, straatnaam, naamOpenbareRuimte, huisnummer, huisletter,
                    huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                    identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, locatieBeschrijving,
                    landWaarnaarVertrokken, vertrekUitNederland, adresBuitenland1, adresBuitenland2,
                    adresBuitenland3, landVanwaarIngeschreven, vestigingInNederland, aangifteAdreshouding,
                    indicatieDocument);
            // inhoud.valideer();
        } catch (final NullPointerException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        } catch (final IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }
}
