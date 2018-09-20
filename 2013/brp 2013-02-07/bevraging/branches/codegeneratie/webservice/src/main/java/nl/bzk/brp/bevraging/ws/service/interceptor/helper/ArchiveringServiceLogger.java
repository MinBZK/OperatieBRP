/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.business.service.impl.StandaardBerichtUitvoerderService;
import nl.bzk.brp.domein.ber.Richting;

import org.slf4j.LoggerFactory;


/**
 * Specifieke logger ten behoeve van het archiveren/loggen van berichten. Deze logger schrijft de te loggen berichten
 * weg via de archiveringsservice (zie {@link ArchiveringService}.
 */
public class ArchiveringServiceLogger extends Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StandaardBerichtUitvoerderService.class);

    @Inject
    private ArchiveringService            archiveringService;

    /**
     * Standaard constructor die de logger initialiseert en een lokale handler zet die naar de archivering service
     * logt.
     *
     * @param richting De richting van de berichten die deze logger logt.
     */
    public ArchiveringServiceLogger(final Richting richting) {
        super("uitgaandeArchiveringServiceLogger", null);

        setUseParentHandlers(false);
        // De standaard log level voor een handler is Level.ALL, dus moet de logger zelf ook op dit level worden
        // geinitialiseerd.
        setLevel(Level.ALL);
        addHandler(new ArchivingServiceLoggingHandler(richting));
    }

    /**
     * Een handler die log berichten krijgt vanuit de <tt>Logger</tt> en deze berichten archiveert.
     */
    private final class ArchivingServiceLoggingHandler extends Handler {

        private final Richting richting;

        /**
         * Constructor voor de handler die de richting van het te loggen bericht direct zet.
         *
         * @param richting de richting van het te loggen bericht.
         */
        private ArchivingServiceLoggingHandler(final Richting richting) {
            this.richting = richting;
        }

        @Override
        public void publish(final LogRecord record) {
            if (Richting.INGAAND == richting) {
                logIngaandBerichtEnOnthoudId(record);
            } else {
                logUitgaandBerichtEnVerwijderId(record);
            }
        }

        /**
         * Archiveert het inkomende bericht (uitgelezen uit het opgegeven {@link LogRecord}) en slaat de ID van het
         * gearchiveerde bericht op in de {@link BerichtIdsThreadLocal} voor latere referentie. Tevens wordt ook
         * reeds een leeg uitgaand bericht gearchiveerd, waarvan de ID ook aan de {@link BerichtIdsThreadLocal} wordt
         * toegevoegd.
         *
         * @param record het log record dat gearchiveerd dient te worden.
         */
        private void logIngaandBerichtEnOnthoudId(final LogRecord record) {
            Long ingaandBerichtId = null;
            Long uitgaandBerichtId = null;
            try {
                ingaandBerichtId = archiveringService.archiveer(record.getMessage(), Richting.INGAAND);
                uitgaandBerichtId = archiveringService.archiveer("<Wordt nader bepaald>", Richting.UITGAAND);
            } catch (Exception e) {
                LOGGER.error("fout bij archiveren ingaand bericht", e);
            } finally {
                // Voor ingaande en uitgaande berichten zullen we het berichId later nog nodig hebben,
                // dus slaan we het op via ThreadLocal.
                BerichtIdsThreadLocal.setBerichtenIds(ingaandBerichtId, uitgaandBerichtId);
            }
        }

        /**
         * Archiveert het uitgaande bericht door het reeds eerder al opgeslagen, maar toen nog lege, uitgaande
         * bericht aan te passen. Hiervoor wordt de ID van het reeds opgeslagen, lege uitgaande bericht van de
         * {@link BerichtIdsThreadLocal} gehaald (en verwijderd).
         *
         * @param record het log record dat gearchiveerd dient te worden.
         */
        private void logUitgaandBerichtEnVerwijderId(final LogRecord record) {
            // Uitgaande richting: Werk het uitgaande bericht bij met de data en vul het veld antwoordOp
            // met het inkomende bericht id.
            Long uitgaandBerichtId = BerichtIdsThreadLocal.getBerichtenIds().getUitgaandBerichtId();
            Long ingaandBerichtId = BerichtIdsThreadLocal.getBerichtenIds().getIngaandBerichtId();
            try {
                archiveringService.werkDataBij(ingaandBerichtId, uitgaandBerichtId, record.getMessage());
            } catch (Exception e) {
                LOGGER.error("fout bij archiveren uitgaand bericht", e);
            } finally {
                // ThreadLocal is nu niet meer nodig. Ruim op.
                BerichtIdsThreadLocal.verwijder();
            }
        }

        @Override
        public void flush() {
            // Do Nothing
        }

        @Override
        public void close() {
            // Do Nothing
        }

    }
}
