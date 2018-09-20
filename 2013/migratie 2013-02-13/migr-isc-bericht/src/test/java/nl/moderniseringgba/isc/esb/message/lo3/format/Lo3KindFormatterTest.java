/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Test;

public class Lo3KindFormatterTest {
    @Test
    public void testFormat() throws Exception {
        final Lo3KindFormatter lo3KindFormatter = new Lo3KindFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3KindInhoud kindInhoud = Lo3KindFormatterTest.maakLo3KindInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_09);
        lo3KindFormatter.format(kindInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("00121091160110010234932634401200090234567890210005Billy0220001H0230003van0240009"
                + "Barendsen0310008201210240320004051803300046030", formatted);
    }

    private static Lo3KindInhoud maakLo3KindInhoud() {
        final Long aNummer = Long.valueOf("2349326344");
        final Long burgerservicenummer = Long.valueOf("23456789");
        final String voornamen = "Billy";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = new Lo3AdellijkeTitelPredikaatCode("H");
        final String voorvoegselGeslachtsnaam = "van";
        final String geslachtsnaam = "Barendsen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("0518");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");

        final Lo3KindInhoud lo3KindInhoud =
                new Lo3KindInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, true);
        return lo3KindInhoud;
    }
}
