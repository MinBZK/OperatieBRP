/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

import org.junit.Test;

public class Lo3ReisdocumentFormatterTest {
    @Test
    public void testFormat() throws Exception {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud = new Lo3ReisdocumentInhoud(
                new Lo3SoortNederlandsReisdocument("PN"),
                "P12345678",
                new Lo3Datum(20010101),
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                new Lo3Datum(20100101),
                new Lo3Datum(20080101),
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                new Lo3LengteHouder(180),
                null,
                null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
                "00104120993510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I" +
                        "3580003180",
                formatted);
    }

    @Test
    public void testFormatLengte2Cijfers() throws Exception {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud = new Lo3ReisdocumentInhoud(
                new Lo3SoortNederlandsReisdocument("PN"),
                "P12345678",
                new Lo3Datum(20010101),
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                new Lo3Datum(20100101),
                new Lo3Datum(20080101),
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                new Lo3LengteHouder(90),
                null,
                null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
                "00104120993510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I" +
                        "3580003090",
                formatted);
    }

    @Test
    public void testFormatStandaardLengte() throws Exception {
        final Lo3ReisdocumentFormatter lo3ReisdocumentFormatter = new Lo3ReisdocumentFormatter();
        final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();
        final Lo3ReisdocumentInhoud reisdocumentInhoud = new Lo3ReisdocumentInhoud(
                new Lo3SoortNederlandsReisdocument("PN"),
                "P12345678",
                new Lo3Datum(20010101),
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("1234"),
                new Lo3Datum(20100101),
                new Lo3Datum(20080101),
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement(),
                new Lo3LengteHouder(0),
                null,
                null);
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_12);
        lo3ReisdocumentFormatter.format(reisdocumentInhoud, lo3Formatter);
        final String formatted = Lo3Inhoud.formatInhoud(lo3Formatter.getList());
        Assert.assertEquals(
                "00104120993510002PN3520009P12345678353000820010101354000412343550008201001013560008200801013570001I" +
                        "3580003000",
                formatted);
    }
}
