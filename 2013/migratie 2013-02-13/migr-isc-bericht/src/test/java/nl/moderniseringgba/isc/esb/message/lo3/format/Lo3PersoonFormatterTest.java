/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Test;

public class Lo3PersoonFormatterTest {
    @Test
    public void testFormat() throws Exception {
        final Lo3PersoonFormatter lo3PersoonFormatter = new Lo3PersoonFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3PersoonInhoud persoonInhoud = Lo3PersoonFormatterTest.maakLo3PersoonInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        lo3PersoonFormatter.format(persoonInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
                "00119011140110010234932634401200090234567890210005Billy0240009Barendsen" +
                        "03100082012102403200040518033000460300410001M6110001E",
                formatted);
    }

    private static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Long aNummer = 2349326344L;
        final Long burgerservicenummer = 23456789L;
        final String voornamen = "Billy";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = "Barendsen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("0518");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }
}
