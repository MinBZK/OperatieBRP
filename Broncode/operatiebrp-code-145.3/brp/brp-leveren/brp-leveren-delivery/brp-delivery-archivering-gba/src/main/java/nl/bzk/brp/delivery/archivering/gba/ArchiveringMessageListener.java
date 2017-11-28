/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.archivering.gba;

import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.jms.TextMessageReader;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Message listener voor GBA archivering berichten.
 */
public class ArchiveringMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private ArchiefService archiefService;

    /**
     * Constructor.
     * @param archiefService archief service
     */
    @Inject
    public ArchiveringMessageListener(final ArchiefService archiefService) {
        this.archiefService = archiefService;
    }

    private static ArchiveringOpdracht parseOpdracht(final String opdracht) {
        return new JsonStringSerializer().deserialiseerVanuitString(opdracht, ArchiveringOpdracht.class);
    }

    @Override
    @Transactional(value = "archiveringTransactionManager", propagation = Propagation.REQUIRED)
    public void onMessage(final Message message) {
        final TextMessageReader reader = new TextMessageReader(message);
        archiefService.archiveer(parseOpdracht(reader.readMessage()));
        LOG.info("Bericht gearchiveerd");
    }
}
