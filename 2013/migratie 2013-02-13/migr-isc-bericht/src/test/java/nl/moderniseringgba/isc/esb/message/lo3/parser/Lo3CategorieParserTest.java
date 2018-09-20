/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;

import org.junit.Test;

public class Lo3CategorieParserTest {

    @Test
    public void testHistorieParse() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final Map<Lo3ElementEnum, String> elementen = new HashMap<Lo3ElementEnum, String>();
        elementen.put(Lo3ElementEnum.ELEMENT_8410, "0");
        elementen.put(Lo3ElementEnum.ELEMENT_8510, "20121214");
        elementen.put(Lo3ElementEnum.ELEMENT_8610, "20121219");

        final Lo3Historie referentieHistorie =
                new Lo3Historie(new Lo3IndicatieOnjuist("0"), new Lo3Datum(20121214), new Lo3Datum(20121219));

        Lo3Historie gegenereerdeHistorie = parser.parseLo3Historie(elementen);

        Assert.assertEquals(referentieHistorie.getDatumVanOpneming().getDatum(), gegenereerdeHistorie
                .getDatumVanOpneming().getDatum());
        Assert.assertEquals(referentieHistorie.getIndicatieOnjuist().getCode(), gegenereerdeHistorie
                .getIndicatieOnjuist().getCode());
        Assert.assertEquals(referentieHistorie.getIngangsdatumGeldigheid().getDatum(), gegenereerdeHistorie
                .getIngangsdatumGeldigheid().getDatum());
        Assert.assertEquals(true, gegenereerdeHistorie.isOnjuist());
        gegenereerdeHistorie = referentieHistorie.verwijderIndicatieOnjuist();
        Assert.assertEquals(false, gegenereerdeHistorie.isOnjuist());
        Assert.assertEquals(false, gegenereerdeHistorie.equals(referentieHistorie.getIndicatieOnjuist()));

    }

    @Test(expected = NullPointerException.class)
    public void testHistorieParseNullPointer() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final Map<Lo3ElementEnum, String> elementen = null;

        try {
            parser.parseLo3Historie(elementen);
        } catch (final NullPointerException exception) {
            throw exception;
        }

    }
}
