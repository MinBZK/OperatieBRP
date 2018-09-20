/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class VoorvoegselConversietabelTest extends AbstractDatabaseTest {

    private static final VoorvoegselScheidingstekenPaar BRP_VOORVOEGSEL = new VoorvoegselScheidingstekenPaar(
            "Aus 'm", ' ');
    private static final String LO3_VOORVOEGSEL = "Aus 'm";

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<String, VoorvoegselScheidingstekenPaar> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createVoorvoegselScheidingstekenConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        final VoorvoegselScheidingstekenPaar brpResultaat = conversietabel.converteerNaarBrp(LO3_VOORVOEGSEL);
        assertEquals(BRP_VOORVOEGSEL, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        final String lo3Resultaat = conversietabel.converteerNaarLo3(BRP_VOORVOEGSEL);
        assertEquals(LO3_VOORVOEGSEL, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
