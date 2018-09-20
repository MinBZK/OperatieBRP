/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = false)
public class RedenVerkrijgingVerliesNederlanderschapConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar> verkrijgingConversietabel;
    private Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar> verliesConversietabel;

    @Before
    public void setup() {
        verkrijgingConversietabel = conversietabelFactory.createRedenVerkrijgingNlNationaliteitConversietabel();
        verliesConversietabel = conversietabelFactory.createRedenVerliesNlNationaliteitConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testRedenVerkrijgingConversieTabel() {
        Lo3RedenNederlandschapCode lo3Input = new Lo3RedenNederlandschapCode("020");
        RedenVerkrijgingVerliesPaar paar = verkrijgingConversietabel.converteerNaarBrp(lo3Input);
        assertEquals(new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("020")), paar.getVerkrijging());
        assertNull(paar.getVerlies());

        try {
            lo3Input = new Lo3RedenNederlandschapCode("035");
            paar = verkrijgingConversietabel.converteerNaarBrp(lo3Input);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains(
                    "Er is geen mapping naar BRP voor LO3 waarde 'Lo3RedenNederlandschapCode[code=035]'"));
        }
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testRedenVerliesConversieTabel() {
        Lo3RedenNederlandschapCode lo3Input;
        RedenVerkrijgingVerliesPaar paar;

        try {
            lo3Input = new Lo3RedenNederlandschapCode("020");
            paar = verliesConversietabel.converteerNaarBrp(lo3Input);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains(
                    "Er is geen mapping naar BRP voor LO3 waarde 'Lo3RedenNederlandschapCode[code=020]'"));
        }

        lo3Input = new Lo3RedenNederlandschapCode("035");
        paar = verliesConversietabel.converteerNaarBrp(lo3Input);
        assertEquals(new BrpRedenVerliesNederlandschapCode(new BigDecimal("035")), paar.getVerlies());
        assertNull(paar.getVerkrijging());

        final Lo3RedenNederlandschapCode terug = verliesConversietabel.converteerNaarLo3(paar);
        assertEquals(lo3Input, terug);
    }
}
