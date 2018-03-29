/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class RNIDeelnemerConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createRNIDeelnemerConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        assertEquals(new BrpPartijCode("250001"), conversietabel.converteerNaarBrp(new Lo3RNIDeelnemerCode("0101")));
        assertEquals(new BrpPartijCode("000000"), conversietabel.converteerNaarBrp(new Lo3RNIDeelnemerCode("0000")));
        assertEquals(new BrpPartijCode("999999"), conversietabel.converteerNaarBrp(new Lo3RNIDeelnemerCode("9999")));
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        assertEquals(new Lo3RNIDeelnemerCode("0101"), conversietabel.converteerNaarLo3(new BrpPartijCode("250001")));
        assertEquals(new Lo3RNIDeelnemerCode("0000"), conversietabel.converteerNaarLo3(new BrpPartijCode("000000")));
        assertEquals(new Lo3RNIDeelnemerCode("9999"), conversietabel.converteerNaarLo3(new BrpPartijCode("999999")));
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
