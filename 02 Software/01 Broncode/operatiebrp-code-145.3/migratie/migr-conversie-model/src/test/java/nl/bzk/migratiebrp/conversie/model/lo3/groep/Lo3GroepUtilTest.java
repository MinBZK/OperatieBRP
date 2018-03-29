/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.groep;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import org.junit.Assert;
import org.junit.Test;

public class Lo3GroepUtilTest {

    private Lo3OuderInhoud.Builder builder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.anummer(Lo3String.wrap("1069532945"));
        builder.burgerservicenummer(Lo3String.wrap("179543489"));

        builder.voornamen(Lo3String.wrap("Jaap"));
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.geslachtsnaam(Lo3String.wrap("Joppen"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.geboorteLandCode(new Lo3LandCode("6030"));

        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.familierechtelijkeBetrekking(new Lo3Datum(20010101));

        return builder;
    }

    @Test
    public void testTemp() {
        final Lo3OuderInhoud inhoud = builder().build();
        Assert.assertTrue(Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud));
        Assert.assertFalse(Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP88, inhoud));

        final Lo3OuderInhoud.Builder inhoud2 = builder();
        inhoud2.burgerservicenummer(null);
        Assert.assertTrue(Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud2.build()));

        inhoud2.anummer(null);
        Assert.assertFalse(Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud2.build()));
    }
}
