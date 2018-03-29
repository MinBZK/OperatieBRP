/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3VerblijfstitelFormatterTest {
    @Test
    public void testFormat() {
        final Lo3VerblijfstitelFormatter lo3VerblijfstitelFormatter = new Lo3VerblijfstitelFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3VerblijfstitelInhoud verblijfstitelInhoud = maakLo3VerblijfstitelInhoud();
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_10);
        lo3VerblijfstitelFormatter.format(verblijfstitelInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals("0003510030392000820130101393000820090701", formatted);
    }

    private Lo3VerblijfstitelInhoud maakLo3VerblijfstitelInhoud() {
        final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode = new Lo3AanduidingVerblijfstitelCode("");
        final Lo3Datum datumEindeVerblijfstitel = new Lo3Datum(Integer.valueOf("20130101"));
        final Lo3Datum datumAanvangVerblijfstitel = new Lo3Datum(Integer.valueOf("20090701"));

        return new Lo3VerblijfstitelInhoud(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel, datumAanvangVerblijfstitel);
    }
}
