/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.junit.Test;

public class Lo3CategorieParserTest {

    @Test
    public void testHistorieParse() {
        final Map<Lo3ElementEnum, String> elementen = new HashMap<>();
        elementen.put(Lo3ElementEnum.ELEMENT_8410, "0");
        elementen.put(Lo3ElementEnum.ELEMENT_8510, "20121214");
        elementen.put(Lo3ElementEnum.ELEMENT_8610, "20121219");

        final Lo3Historie referentieHistorie = new Lo3Historie(new Lo3IndicatieOnjuist("0"), new Lo3Datum(20121214), new Lo3Datum(20121219));

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();
        Lo3Historie gegenereerdeHistorie = parser.parseLo3Historie(elementen, null, null);

        Assert.assertEquals(referentieHistorie.getDatumVanOpneming().getWaarde(), gegenereerdeHistorie.getDatumVanOpneming().getWaarde());
        Assert.assertEquals(referentieHistorie.getIndicatieOnjuist().getWaarde(), gegenereerdeHistorie.getIndicatieOnjuist().getWaarde());
        Assert.assertEquals(referentieHistorie.getIngangsdatumGeldigheid().getWaarde(), gegenereerdeHistorie.getIngangsdatumGeldigheid().getWaarde());
        Assert.assertEquals(true, gegenereerdeHistorie.isOnjuist());
        gegenereerdeHistorie = referentieHistorie.verwijderIndicatieOnjuist();
        Assert.assertEquals(false, gegenereerdeHistorie.isOnjuist());
        Assert.assertEquals(false, referentieHistorie.getIndicatieOnjuist().equals(gegenereerdeHistorie.getIndicatieOnjuist()));

    }

    @Test(expected = NullPointerException.class)
    public void testHistorieParseNullPointer() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        parser.parseLo3Historie(null, null, null);
    }
}
