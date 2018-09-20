/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.RedenOntbindingHuwelijkPartnerschapConversietabel;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = false)
public class RedenOntbindingHuwelijkPartnerschapConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory factory;

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testRedenOntbindingConversieTabel() {
        final RedenOntbindingHuwelijkPartnerschapConversietabel tabel =
                (RedenOntbindingHuwelijkPartnerschapConversietabel) factory.createRedenEindeRelatieConversietabel();
        final Lo3RedenOntbindingHuwelijkOfGpCode lo3Input = new Lo3RedenOntbindingHuwelijkOfGpCode("O");
        final BrpRedenEindeRelatieCode brpRedenEindeRelatieCode = tabel.converteerNaarBrp(lo3Input);
        assertEquals(new BrpRedenEindeRelatieCode("O"), brpRedenEindeRelatieCode);

        final Lo3RedenOntbindingHuwelijkOfGpCode terug = tabel.converteerNaarLo3(brpRedenEindeRelatieCode);
        assertEquals(lo3Input, terug);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWaardeNietGevondenLo3NaarBrp() {
        final RedenOntbindingHuwelijkPartnerschapConversietabel tabel =
                (RedenOntbindingHuwelijkPartnerschapConversietabel) factory.createRedenEindeRelatieConversietabel();
        final BrpRedenEindeRelatieCode result = tabel.converteerNaarBrp(new Lo3RedenOntbindingHuwelijkOfGpCode("X"));
        fail("Exceptie verwacht: waarde was: " + result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWaardeNietGevondenBrpNaarLo3() {
        final RedenOntbindingHuwelijkPartnerschapConversietabel tabel =
                (RedenOntbindingHuwelijkPartnerschapConversietabel) factory.createRedenEindeRelatieConversietabel();
        final Lo3RedenOntbindingHuwelijkOfGpCode result = tabel.converteerNaarLo3(new BrpRedenEindeRelatieCode("X"));
        fail("Exceptie verwacht: waarde was: " + result);
    }
}
