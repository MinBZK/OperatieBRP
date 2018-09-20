/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;

import org.junit.Test;

public class Lo3DocumentatieTest {

    @Test
    public void testConstructor() {
        new Lo3Documentatie(1L, new Lo3GemeenteCode("1234"), "1234567", new Lo3GemeenteCode("1234"), new Lo3Datum(
                20010101), "beschrijvingDocument", null, null);
    }

}
