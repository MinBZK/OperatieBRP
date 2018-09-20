/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import javax.inject.Inject;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.webservice.business.service.ArchiveringService;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;


/**
 * Processor om de archivering van het te versturen bericht te regelen.
 *
 * @brp.bedrijfsregel R1268
 * @brp.bedrijfsregel R1997
 */
@Component
@Regels({ Regel.R1268, Regel.R1997 })
public class ArchiveerStap {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ArchiveringService archiveringService;

    /**
     * Archiveert de verzending.
     *
     * @param context context van verzending
     * @throws Exception mogelijke exceptie
     */
    @Profiled(tag = "ArchiveerStap", logFailuresSeparately = true, level = "DEBUG")
    public void process(final BerichtContext context) throws Exception {
        LOGGER.debug("Archiveer verzending");
        final long start = System.currentTimeMillis();
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = context.getSynchronisatieBerichtGegevens();

        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(synchronisatieBerichtGegevens.getAdministratieveHandelingId()));

        LOGGER.info("Archiveren van uitgaand bericht mbt administratieve handeling id: [{}] naar toegang leveringsautoriatie {}",
            synchronisatieBerichtGegevens.getAdministratieveHandelingId(), synchronisatieBerichtGegevens.getToegangLeveringsautorisatieId());

        final BerichtModel bericht = archiveringService.archiveer(synchronisatieBerichtGegevens);
        context.setBerichtArchiefModel(bericht);
        context.getVerwerkContext().addArchiveerTijd(System.currentTimeMillis() - start);
    }
}
