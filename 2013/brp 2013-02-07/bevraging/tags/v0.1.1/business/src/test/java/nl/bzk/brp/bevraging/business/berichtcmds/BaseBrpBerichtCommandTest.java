/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import static nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import org.junit.Test;


/**
 * Unit test voor de {@link AbstractBrpBerichtCommand} class.
 */
public class BaseBrpBerichtCommandTest {

    /**
     * Unit test voor de {@link AbstractBrpBerichtCommand#voegFoutToe(BerichtVerwerkingsFout)} method.
     */
    @Test
    public void testVoegFoutToe() {
        BrpBerichtCommand cmd = new MockBrpBerichtCommand(null, null);

        assertEquals(0, cmd.getFouten().size());

        cmd.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.INFO, "Test1"));
        cmd.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.FOUT, "Test2"));

        assertEquals(2, cmd.getFouten().size());
    }

    /**
     * Inner class die als eenvoudige implementatie dient van de abstract class {@link AbstractBrpBerichtCommand}.
     */
    private class MockBrpBerichtCommand extends AbstractBrpBerichtCommand {

        /**
         * Standaard constructor.
         *
         * @param verzoek het verzoek.
         * @param context de context.
         */
        public MockBrpBerichtCommand(final Object verzoek, final BrpBerichtContext context) {
            super((BRPVerzoek) verzoek, context);
        }

        @Override
        public void voerUit() {
            return;
        }

        @Override
        public SoortBericht getSoortBericht() {
            return SoortBericht.OPVRAGEN_PERSOON_VRAAG;
        }
    }
}
