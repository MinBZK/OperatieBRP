/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class BrpDocumentTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new BrpDocumentInhoud(new BrpSoortDocumentCode("X"), new BrpString("ident"), new BrpString(
            "123456-AKT"), new BrpString("Omschrijving"), new BrpPartijCode(Integer.valueOf("0518"))), new BrpDocumentInhoud(
            new BrpSoortDocumentCode("X"),
            new BrpString("ident"),
            new BrpString("123456-AKT"),
            new BrpString("Omschrijving"),
            new BrpPartijCode(Integer.valueOf("0518"))), new BrpDocumentInhoud(new BrpSoortDocumentCode("Y"), new BrpString("qwe"), new BrpString(
            "654321-AKT"), new BrpString("Beschrijving"), new BrpPartijCode(Integer.valueOf("0000"))));
    }
}
