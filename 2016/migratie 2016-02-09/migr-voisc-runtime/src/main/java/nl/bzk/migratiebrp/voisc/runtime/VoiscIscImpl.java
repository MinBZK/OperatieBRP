/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * VOISC ISC Implementatie.
 */
public final class VoiscIscImpl implements VoiscIsc {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoiscDatabase voiscDatabase;
    @Inject
    private VoiscQueue voiscQueue;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.isc.voisc.Voiscisc#verstuurBerichtenNaarIsc(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public void verstuurBerichtenNaarIsc(final List<Bericht> messagesToSend) {
        for (final Bericht bericht : messagesToSend) {
            LOGGER.info("[Bericht {}] Bericht verwerken", bericht.getId());
            try {
                voiscQueue.verstuurBerichtNaarIsc(bericht);
                bericht.setTijdstipVerzonden(new Date());
                bericht.setStatus(StatusEnum.SENT_TO_ISC);

                LOGGER.info("[Bericht {}] Bericht opslaan.", bericht.getId());
                voiscDatabase.saveBericht(bericht);

            } catch (final VoiscException ve) {
                LOGGER.error("[Bericht {}]: Fout bij verzenden van bericht naar ISC", bericht.getId(), ve);
            }
            LOGGER.info("[Bericht {}] Bericht verwerking gereed.", bericht.getId());
        }
    }

}
