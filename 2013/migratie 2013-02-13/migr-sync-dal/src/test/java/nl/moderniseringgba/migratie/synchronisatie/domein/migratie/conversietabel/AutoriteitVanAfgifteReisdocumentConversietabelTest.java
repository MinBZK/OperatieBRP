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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class AutoriteitVanAfgifteReisdocumentConversietabelTest extends AbstractDatabaseTest {

    private static final Lo3AutoriteitVanAfgifteNederlandsReisdocument LO3_AUTORITEIT =
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument("BI0518");
    private static final BrpReisdocumentAutoriteitVanAfgifte BRP_AUTORITEIT =
            new BrpReisdocumentAutoriteitVanAfgifte("BI0518");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createReisdocumentAutoriteitVanAfgifteConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        final BrpReisdocumentAutoriteitVanAfgifte brpResultaat = conversietabel.converteerNaarBrp(LO3_AUTORITEIT);
        assertEquals(BRP_AUTORITEIT, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument lo3Resultaat =
                conversietabel.converteerNaarLo3(BRP_AUTORITEIT);
        assertEquals(LO3_AUTORITEIT, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
