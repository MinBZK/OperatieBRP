/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.StapException;

/**
 * VerwerkPersoonTaak.
 */
final class VerwerkPersoonTaak implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private VerwerkPersoonService verwerkPersoonService;
    private BlockingQueue<MaakSelectieResultaatOpdracht> taakQueue;
    private Queue<VerwerkPersoonResultaat> resultaatQueue;

    @Override
    public Object call() throws Exception {
        LOGGER.debug("startBericht maak selectie resultaat taak");
        BrpNu.set();
        boolean klaar = false;
        while (!klaar) {
            final MaakSelectieResultaatOpdracht opdracht = taakQueue.poll(10, TimeUnit.MILLISECONDS);
            if (opdracht != null) {
                //poison, klaar
                if (opdracht.isStop()) {
                    klaar = true;
                } else {
                    maakSelectieResultaat(opdracht);
                }
            }
        }
        LOGGER.debug("eind maak selectie resultaat taak");
        return null;
    }

    private void maakSelectieResultaat(MaakSelectieResultaatOpdracht opdracht) throws StapException {
        LOGGER.debug("startBericht maak selectie resultaat voor persoon en autorisaties : {}", opdracht.getAutorisatiebundels().size());
        final List<VerwerkPersoonResultaat> resultaten = verwerkPersoonService.verwerk(opdracht);
        resultaatQueue.addAll(resultaten);
        LOGGER.debug("Einde maak selectie resultaat voor persoon en autorisaties");
    }

    /**
     * @param verwerkPersoonService verwerkPersoonService
     */
    void setVerwerkPersoonService(VerwerkPersoonService verwerkPersoonService) {
        this.verwerkPersoonService = verwerkPersoonService;
    }

    /**
     * @param taakQueue taakQueue
     */
    void setTaakQueue(BlockingQueue<MaakSelectieResultaatOpdracht> taakQueue) {
        this.taakQueue = taakQueue;
    }

    /**
     * @param resultaatQueue resultaatQueue
     */
    void setResultaatQueue(Queue<VerwerkPersoonResultaat> resultaatQueue) {
        this.resultaatQueue = resultaatQueue;
    }
}
