/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingEuropeesKiesrechtConversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;

import org.junit.Test;

public class AanduidingEuropeesKiesrechtConversietabelTest {

    @Test
    public void testNaarBrp() {
        final AanduidingEuropeesKiesrechtConversietabel tabel = new AanduidingEuropeesKiesrechtConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement()));
        assertFalse(tabel.converteerNaarBrp(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement()));
    }

    @Test
    public void testNaarLo3() {
        final AanduidingEuropeesKiesrechtConversietabel tabel = new AanduidingEuropeesKiesrechtConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement(), tabel.converteerNaarLo3(true));
        assertEquals(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement(), tabel.converteerNaarLo3(false));
    }
}
