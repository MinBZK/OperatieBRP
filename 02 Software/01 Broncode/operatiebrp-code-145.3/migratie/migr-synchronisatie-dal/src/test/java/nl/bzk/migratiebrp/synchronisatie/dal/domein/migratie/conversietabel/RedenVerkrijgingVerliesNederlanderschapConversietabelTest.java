/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class RedenVerkrijgingVerliesNederlanderschapConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> verkrijgingConversietabel;
    private Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> verliesConversietabel;

    @Before
    public void setup() {
        verkrijgingConversietabel = conversietabelFactory.createRedenOpnameNationaliteitConversietabel();
        verliesConversietabel = conversietabelFactory.createRedenBeeindigingNationaliteitConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testRedenVerkrijgingConversieTabel() {
        Lo3RedenNederlandschapCode lo3Input = new Lo3RedenNederlandschapCode("020");
        final BrpRedenVerkrijgingNederlandschapCode verkrijg = verkrijgingConversietabel.converteerNaarBrp(lo3Input);
        assertEquals(new BrpRedenVerkrijgingNederlandschapCode("020"), verkrijg);

        try {
            lo3Input = new Lo3RedenNederlandschapCode("035");
            verkrijgingConversietabel.converteerNaarBrp(lo3Input);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains(
                    "Er is geen mapping naar BRP voor LO3 waarde 'Lo3RedenNederlandschapCode[waarde=035,onderzoek=<null>]'"));
        }
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testRedenVerliesConversieTabel() {
        Lo3RedenNederlandschapCode lo3Input;
        final BrpRedenVerliesNederlandschapCode verkrijg;

        try {
            lo3Input = new Lo3RedenNederlandschapCode("020");
            verliesConversietabel.converteerNaarBrp(lo3Input);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains(
                    "Er is geen mapping naar BRP voor LO3 waarde 'Lo3RedenNederlandschapCode[waarde=020,onderzoek=<null>]'"));
        }

        lo3Input = new Lo3RedenNederlandschapCode("035");
        verkrijg = verliesConversietabel.converteerNaarBrp(lo3Input);
        assertEquals(new BrpRedenVerliesNederlandschapCode("035"), verkrijg);

        final Lo3RedenNederlandschapCode terug = verliesConversietabel.converteerNaarLo3(verkrijg);
        assertEquals(lo3Input, terug);
    }
}
