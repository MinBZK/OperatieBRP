/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

public class BrpAdresInhoudTest {

    private static final String STRING_6030 = "6030";

    public static BrpStapel<BrpAdresInhoud> createStapel() {
        List<BrpGroep<BrpAdresInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpAdresInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static BrpAdresInhoud createInhoud() {
        return new BrpAdresInhoud(
            BrpSoortAdresCode.W,
            new BrpRedenWijzigingVerblijfCode('V'),
            new BrpAangeverCode('A'),
            new BrpDatum(20100101, null),
            new BrpString("1312312"),
            new BrpString("123123213"),
            new BrpGemeenteCode(Short.parseShort("0518")),
            new BrpString("naam"),
            new BrpString("afk"),
            new BrpString("gemeentedeel"),
            new BrpInteger(123),
            new BrpCharacter('A'),
            new BrpString("-123"),
            new BrpString("1234AB"),
            new BrpString("woonplaatsnaam"),
            new BrpAanduidingBijHuisnummerCode("TO"),
            new BrpString("woonboot"),
            new BrpString("regel 1"),
            new BrpString("regel 2"),
            new BrpString("regel 3"),
            null,
            null,
            null,
            new BrpLandOfGebiedCode(Short.parseShort(STRING_6030)),
            null);
    }

}
