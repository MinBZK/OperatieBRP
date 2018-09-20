/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Test;

public class Lo3ReisdocumentFormatterTest {
    @Test
    public void testFormat() {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(
                    new Lo3SoortNederlandsReisdocument("PN"),
                    Lo3String.wrap("P12345678"),
                    new Lo3Datum(20010101),
                    new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                    new Lo3Datum(20100101),
                    new Lo3Datum(20080101),
                    Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                    null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
"00094120893510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I",
            formatted);
    }

    @Test
    public void testFormatLengte2Cijfers() {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(
                    new Lo3SoortNederlandsReisdocument("PN"),
                    Lo3String.wrap("P12345678"),
                    new Lo3Datum(20010101),
                    new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                    new Lo3Datum(20100101),
                    new Lo3Datum(20080101),
                    Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                    null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
"00094120893510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I",
            formatted);
    }

    @Test
    public void testFormatStandaardLengte() {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(
                    new Lo3SoortNederlandsReisdocument("PN"),
                    Lo3String.wrap("P12345678"),
                    new Lo3Datum(20010101),
                    new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                    new Lo3Datum(20100101),
                    new Lo3Datum(20080101),
                    Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                    null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
"00094120893510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I",
            formatted);
    }
}
