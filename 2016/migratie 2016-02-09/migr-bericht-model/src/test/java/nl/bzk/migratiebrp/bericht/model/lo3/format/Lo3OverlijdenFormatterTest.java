/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3OverlijdenFormatterTest {
    @Test
    public void testFormat() {
        final Lo3OverlijdenFormatter lo3OverlijdenFormatter = new Lo3OverlijdenFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3OverlijdenInhoud overlijdenInhoud = Lo3OverlijdenFormatterTest.maakLo3OverlijdenInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_06);
        lo3OverlijdenFormatter.format(overlijdenInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("00042060370810008201212230820004190408300043010", formatted);
    }

    private static Lo3OverlijdenInhoud maakLo3OverlijdenInhoud() {
        final Lo3Datum datum = new Lo3Datum(Integer.valueOf("20121223"));
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("1904");
        final Lo3LandCode landCode = new Lo3LandCode("3010");

        return new Lo3OverlijdenInhoud(datum, gemeenteCode, landCode);
    }
}
