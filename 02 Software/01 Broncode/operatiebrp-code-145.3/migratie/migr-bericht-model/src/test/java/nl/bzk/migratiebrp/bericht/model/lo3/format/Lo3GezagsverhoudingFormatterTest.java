/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3GezagsverhoudingFormatterTest {
    @Test
    public void testFormat() {
        final Lo3GezagsverhoudingFormatter lo3GezagsverhoudingFormatter = new Lo3GezagsverhoudingFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3GezagsverhoudingInhoud gezagsverhoudingInhoud = Lo3GezagsverhoudingFormatterTest.maakLo3GezagsverhoudingInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_11);
        lo3GezagsverhoudingFormatter.format(gezagsverhoudingInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("00021110163210001133100011", formatted);
    }

    private static Lo3GezagsverhoudingInhoud maakLo3GezagsverhoudingInhoud() {
        final Lo3IndicatieGezagMinderjarige lo3IndicatieGezagMinderjarige =
                new Lo3IndicatieGezagMinderjarige(Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.getCode());
        final Lo3IndicatieCurateleregister indicatieCurateleregister =
                new Lo3IndicatieCurateleregister(Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.getCode());

        final Lo3GezagsverhoudingInhoud lo3GezagsverhoudingInhoud =
                new Lo3GezagsverhoudingInhoud(lo3IndicatieGezagMinderjarige, indicatieCurateleregister);
        return lo3GezagsverhoudingInhoud;
    }
}
