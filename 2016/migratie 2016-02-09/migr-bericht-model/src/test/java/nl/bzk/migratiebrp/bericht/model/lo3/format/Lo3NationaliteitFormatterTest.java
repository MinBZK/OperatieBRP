/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3NationaliteitFormatterTest {
    @Test
    public void testFormat() {
        final Lo3NationaliteitFormatter lo3NationaliteitFormatter = new Lo3NationaliteitFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3NationaliteitInhoud nationaliteitInhoud = Lo3NationaliteitFormatterTest.maakLo3NationaliteitInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_04);
        lo3NationaliteitFormatter.format(nationaliteitInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("000440403905100043010631000301064100030206510001B", formatted);
    }

    private static Lo3NationaliteitInhoud maakLo3NationaliteitInhoud() {

        final Lo3NationaliteitCode nationaliteitCode = new Lo3NationaliteitCode("3010");
        final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode = new Lo3RedenNederlandschapCode("010");
        final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode = new Lo3RedenNederlandschapCode("020");
        final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap =
                new Lo3AanduidingBijzonderNederlandschap(Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.getCode());

        final Lo3NationaliteitInhoud lo3NationaliteitInhoud =
                new Lo3NationaliteitInhoud(
                    nationaliteitCode,
                    redenVerkrijgingNederlandschapCode,
                    redenVerliesNederlandschapCode,
                    aanduidingBijzonderNederlandschap);
        return lo3NationaliteitInhoud;
    }
}
