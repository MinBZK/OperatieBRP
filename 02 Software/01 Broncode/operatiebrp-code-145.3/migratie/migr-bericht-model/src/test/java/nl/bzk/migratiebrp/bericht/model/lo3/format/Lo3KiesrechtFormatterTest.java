/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3KiesrechtFormatterTest {
    @Test
    public void testFormat() {
        final Lo3KiesrechtFormatter lo3KiesrechtFormatter = new Lo3KiesrechtFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3KiesrechtInhoud kiesrechtInhoud = Lo3KiesrechtFormatterTest.maakLo3KiesrechtInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_13);
        lo3KiesrechtFormatter.format(kiesrechtInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("0005113046311000123120008200507013810001A382000820040802", formatted);
    }

    private static Lo3KiesrechtInhoud maakLo3KiesrechtInhoud() {

        final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht =
                new Lo3AanduidingEuropeesKiesrecht(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.getCode());
        final Lo3Datum datumEuropeesKiesrecht = new Lo3Datum(Integer.valueOf("20050701"));
        final Lo3Datum einddatumUitsluitingEuropeesKiesrecht = null;
        final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht =
                new Lo3AanduidingUitgeslotenKiesrecht(Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.getCode());
        final Lo3Datum einddatumUitsluitingKiesrecht = new Lo3Datum(Integer.valueOf("20040802"));

        final Lo3KiesrechtInhoud lo3KiesrechtInhoud =
                new Lo3KiesrechtInhoud(
                        aanduidingEuropeesKiesrecht,
                        datumEuropeesKiesrecht,
                        einddatumUitsluitingEuropeesKiesrecht,
                        aanduidingUitgeslotenKiesrecht,
                        einddatumUitsluitingKiesrecht);
        return lo3KiesrechtInhoud;
    }
}
