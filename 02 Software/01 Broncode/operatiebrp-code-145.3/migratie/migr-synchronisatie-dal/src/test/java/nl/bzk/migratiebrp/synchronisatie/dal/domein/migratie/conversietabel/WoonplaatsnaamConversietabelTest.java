/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class WoonplaatsnaamConversietabelTest extends AbstractDatabaseTest {

    private static final String LO3_PLAATSNAAM = "Rijswijk";
    private static final String BRP_PLAATSNAAM = "Rijswijk";

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<String, String> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createWoonplaatsnaamConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        final String brpResultaat = conversietabel.converteerNaarBrp(LO3_PLAATSNAAM);
        assertEquals(BRP_PLAATSNAAM, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        final String lo3Resultaat = conversietabel.converteerNaarLo3(BRP_PLAATSNAAM);
        assertEquals(LO3_PLAATSNAAM, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
