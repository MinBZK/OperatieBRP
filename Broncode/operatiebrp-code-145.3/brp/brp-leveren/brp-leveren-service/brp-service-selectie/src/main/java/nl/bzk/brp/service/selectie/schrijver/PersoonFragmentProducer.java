/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;

/**
 * Producer welke alle fragment bestanden inleest van een gegeven selectieautorisatie en
 * elke regel (persoon xml) op de queue zet zodat het gecombineerd kan worden tot een selectiebestand.
 */
final class PersoonFragmentProducer implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private SelectieFileService selectieFileService;
    private BlockingQueue<String> queue;
    private MaakSelectieResultaatTaak selectieMaakSelectieResultaatTaak;


    @Override
    public Object call() throws Exception {
        LOGGER.info("Start producer persoon fragmenten");
        BrpNu.set();
        final List<Path> paths = selectieFileService.geefFragmentFiles(selectieMaakSelectieResultaatTaak);
        for (Path resultaatPath : paths) {
            try (Stream<String> lines = selectieFileService.leesFragmentRegels(resultaatPath)) {
                lines.forEach(this::verwerkRegel);
            }
        }
        LOGGER.info("Einde producer persoon fragmenten");
        return null;
    }

    private void verwerkRegel(String xmlRegel) {
        try {
            if (xmlRegel != null && !xmlRegel.isEmpty()) {
                queue.put(xmlRegel);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BrpServiceRuntimeException(e);
        }
    }

    /**
     * @param selectieMaakSelectieResultaatTaak selectieMaakSelectieResultaatTaak
     */
    public void setSelectieMaakSelectieResultaatTaak(final MaakSelectieResultaatTaak selectieMaakSelectieResultaatTaak) {
        this.selectieMaakSelectieResultaatTaak = selectieMaakSelectieResultaatTaak;
    }

    /**
     * @param queue queue
     */
    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    /**
     * @param selectieFileService selectieFileService
     */
    public void setSelectieFileService(SelectieFileService selectieFileService) {
        this.selectieFileService = selectieFileService;
    }
}
