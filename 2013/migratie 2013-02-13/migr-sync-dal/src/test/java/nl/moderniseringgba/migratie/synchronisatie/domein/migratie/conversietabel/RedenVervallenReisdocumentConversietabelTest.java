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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class RedenVervallenReisdocumentConversietabelTest extends AbstractDatabaseTest {

    private static final Lo3AanduidingInhoudingVermissingNederlandsReisdocument LO3_INGEHOUDEN =
            Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement();
    private static final BrpReisdocumentRedenOntbreken BRP_REDEN_ONTBREKEN = new BrpReisdocumentRedenOntbreken("I");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createReisdocumentRedenOntbrekenConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        final BrpReisdocumentRedenOntbreken brpResultaat = conversietabel.converteerNaarBrp(LO3_INGEHOUDEN);
        assertEquals(BRP_REDEN_ONTBREKEN, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument lo3Resultaat =
                conversietabel.converteerNaarLo3(BRP_REDEN_ONTBREKEN);
        assertEquals(LO3_INGEHOUDEN, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
