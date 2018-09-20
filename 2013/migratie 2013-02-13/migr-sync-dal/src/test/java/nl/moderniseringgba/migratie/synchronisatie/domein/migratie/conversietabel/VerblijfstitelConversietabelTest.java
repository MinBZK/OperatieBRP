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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class VerblijfstitelConversietabelTest extends AbstractDatabaseTest {

    private static final BrpVerblijfsrechtCode BRP_AANDUIDING_VERBLIJFSTITEL = new BrpVerblijfsrechtCode(
            "Art. 10 van de Vreemdelingenwet");

    private static final Lo3AanduidingVerblijfstitelCode LO3_VERBLIJFSTITEL_CODE =
            new Lo3AanduidingVerblijfstitelCode("10");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createVerblijfsrechtConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        final BrpVerblijfsrechtCode brpResultaat = conversietabel.converteerNaarBrp(LO3_VERBLIJFSTITEL_CODE);
        assertEquals(BRP_AANDUIDING_VERBLIJFSTITEL, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        final Lo3AanduidingVerblijfstitelCode lo3Resultaat =
                conversietabel.converteerNaarLo3(BRP_AANDUIDING_VERBLIJFSTITEL);
        assertEquals(LO3_VERBLIJFSTITEL_CODE, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
