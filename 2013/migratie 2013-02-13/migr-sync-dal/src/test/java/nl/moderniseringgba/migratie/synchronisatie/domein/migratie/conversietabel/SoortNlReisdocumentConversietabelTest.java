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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class SoortNlReisdocumentConversietabelTest extends AbstractDatabaseTest {

    private static final BrpReisdocumentSoort BRP_REISDOCUMENT_SOORT = new BrpReisdocumentSoort("IA");
    private static final Lo3SoortNederlandsReisdocument LO3_SOORT_NEDERLANDS_REISDOCUMENT =
            new Lo3SoortNederlandsReisdocument("IA");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createSoortReisdocumentConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testNullConversie() {
        assertNull(conversietabel.converteerNaarBrp(null));
        assertNull(conversietabel.converteerNaarLo3(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeConversieNaarBrp() {
        conversietabel.converteerNaarBrp(new Lo3SoortNederlandsReisdocument("ONBEKEND"));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeConversieNaarLo3() {
        conversietabel.converteerNaarLo3(new BrpReisdocumentSoort("ONBEKEND"));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        final BrpReisdocumentSoort verwachtResultaat = BRP_REISDOCUMENT_SOORT;
        final BrpReisdocumentSoort conversieResultaat =
                conversietabel.converteerNaarBrp(LO3_SOORT_NEDERLANDS_REISDOCUMENT);
        assertEquals(verwachtResultaat, conversieResultaat);
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        final Lo3SoortNederlandsReisdocument verwachtResultaat = LO3_SOORT_NEDERLANDS_REISDOCUMENT;
        final Lo3SoortNederlandsReisdocument conversieResultaat =
                conversietabel.converteerNaarLo3(BRP_REISDOCUMENT_SOORT);
        assertEquals(verwachtResultaat, conversieResultaat);
    }

}
