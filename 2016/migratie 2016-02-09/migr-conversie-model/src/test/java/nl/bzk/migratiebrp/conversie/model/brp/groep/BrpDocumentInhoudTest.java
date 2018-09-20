/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

public class BrpDocumentInhoudTest {


    public static BrpDocumentInhoud createInhoud() {
        return new BrpDocumentInhoud(
            BrpSoortDocumentCode.HISTORIE_CONVERSIE,
            new BrpString("12"),
            new BrpString("200"),
            new BrpString("omschrijving"),
            BrpPartijCode.ONBEKEND);

    }
}
