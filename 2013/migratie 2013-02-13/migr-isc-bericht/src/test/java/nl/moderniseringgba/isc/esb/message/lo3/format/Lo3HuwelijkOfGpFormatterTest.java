/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

import org.junit.Test;

public class Lo3HuwelijkOfGpFormatterTest {
    @Test
    public void testFormat() throws Exception {
        final Lo3HuwelijkOfGpFormatter lo3HuwelijkOfGpFormatter = new Lo3HuwelijkOfGpFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud = Lo3HuwelijkOfGpFormatterTest.maakLo3HuwelijkOfGpInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_05);
        lo3HuwelijkOfGpFormatter.format(huwelijkOfGpInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("002180521301100100012345678012001098751232120210005Carla0220001H0230003van0240007"
                + "Leeuwen03100081950101203200041904033000430100410001V06100082002060806200041905063000430100710008"
                + "2012050107200041904073000430100740001S1510001H", formatted);
    }

    private static Lo3HuwelijkOfGpInhoud maakLo3HuwelijkOfGpInhoud() {

        final Long aNummer = Long.valueOf("12345678");
        final Long burgerservicenummer = Long.valueOf("9875123212");
        final String voornamen = "Carla";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = new Lo3AdellijkeTitelPredikaatCode("H");
        final String voorvoegselGeslachtsnaam = "van";
        final String geslachtsnaam = "Leeuwen";
        final Lo3Datum geboortedatum = new Lo3Datum(Integer.valueOf("19501012"));
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("1904");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("3010");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("V");
        final Lo3Datum datumSluitingHuwelijkOfAangaanGp = new Lo3Datum(Integer.valueOf("20020608"));
        final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp = new Lo3GemeenteCode("1905");
        final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp = new Lo3LandCode("3010");
        final Lo3Datum datumOntbindingHuwelijkOfGp = new Lo3Datum(Integer.valueOf("20120501"));
        final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp = new Lo3GemeenteCode("1904");
        final Lo3LandCode landCodeOntbindingHuwelijkOfGp = new Lo3LandCode("3010");
        final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode =
                new Lo3RedenOntbindingHuwelijkOfGpCode("S");
        final Lo3SoortVerbintenis soortVerbintenis =
                new Lo3SoortVerbintenis(Lo3SoortVerbintenisEnum.HUWELIJK.getCode());

        final Lo3HuwelijkOfGpInhoud lo3HuwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, datumSluitingHuwelijkOfAangaanGp,
                        gemeenteCodeSluitingHuwelijkOfAangaanGp, landCodeSluitingHuwelijkOfAangaanGp,
                        datumOntbindingHuwelijkOfGp, gemeenteCodeOntbindingHuwelijkOfGp,
                        landCodeOntbindingHuwelijkOfGp, redenOntbindingHuwelijkOfGpCode, soortVerbintenis);
        return lo3HuwelijkOfGpInhoud;
    }
}
