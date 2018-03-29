/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

/**
 * Helper class voor synchronisatie.
 */
public final class SynchronisatieHelper {

    private SynchronisatieHelper() {
        // Niet instantieerbaar.
    }

    /**
     * Maak een synch antwoord.
     * @param verzoek het verzoek waaraan het antwoord gecorreleerd moet zijn
     * @param status status
     * @param kandidaten kandidaten
     * @return antwoord
     */
    public static SynchroniseerNaarBrpAntwoordBericht maakAntwoord(final SynchroniseerNaarBrpVerzoekBericht verzoek, final StatusType status) {
        SynchronisatieLogging.setResultaat(status);

        final SynchroniseerNaarBrpAntwoordBericht result = new SynchroniseerNaarBrpAntwoordBericht();
        result.setMessageId(MessageId.generateSyncMessageId());
        result.setCorrelationId(verzoek.getMessageId());

        result.setLogging(Logging.getLogging().getRegels());
        result.setMelding(SynchronisatieLogging.getMelding());
        result.setStatus(status);

        return result;
    }
}
