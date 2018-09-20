/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class BrpDocumentTest {

    @Test
    public void test() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpDocumentInhoud(new BrpSoortDocumentCode("X"),
                "ident", "123456-AKT", "Omschrijving", new BrpPartijCode(null, Integer.valueOf("0518"))),
                new BrpDocumentInhoud(new BrpSoortDocumentCode("X"), "ident", "123456-AKT", "Omschrijving",
                        new BrpPartijCode(null, Integer.valueOf("0518"))), new BrpDocumentInhoud(
                        new BrpSoortDocumentCode("Y"), "qwe", "654321-AKT", "Beschrijving", new BrpPartijCode(null,
                                Integer.valueOf("0000"))));
    }
}
