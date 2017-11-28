/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.persoonsbeelden;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.selectie.algemeen.Configuratie;

/**
 * MaakPersoonslijstBatchTaak.
 */
final class MaakPersoonslijstBatchTaak implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private PersoonslijstService persoonslijstService;
    private Queue<Persoonslijst> resultaatQueue;
    private BlockingQueue<MaakPersoonslijstBatchOpdracht> taakQueue;

    @Override
    public Object call() throws Exception {
        BrpNu.set();
        LOGGER.debug("startBericht maak persoonslijst batch taak");
        boolean klaar = false;
        while (!klaar) {
            final MaakPersoonslijstBatchOpdracht taak = taakQueue.poll(Configuratie.QUEUE_POLLING_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (taak != null) {
                if (taak.isStop()) {
                    klaar = true;
                } else {
                    maakPersoonslijstBatch(taak);
                }
            }
        }
        LOGGER.debug("einde maak persoonslijst batch taak");
        return null;
    }

    private void maakPersoonslijstBatch(MaakPersoonslijstBatchOpdracht taak) {
        LOGGER.debug("startBericht maak persoonslijst batch voor personen [{}]", taak.getCaches().size());
        final List<SelectiePersoonBericht> persoonCaches = taak.getCaches();
        final List<Persoonslijst> persoonslijsten = new ArrayList<>(persoonCaches.size());
        for (SelectiePersoonBericht selectiePersoonBericht : persoonCaches) {
            byte[] persoonData = new byte[]{};
            if (selectiePersoonBericht.getPersoonHistorieVolledigGegevens() != null) {
                persoonData = Base64.getDecoder().decode(selectiePersoonBericht.getPersoonHistorieVolledigGegevens());
            }
            byte[] afnemerindicatieData = new byte[]{};
            if (selectiePersoonBericht.getAfnemerindicatieGegevens() != null) {
                afnemerindicatieData = Base64.getDecoder().decode(selectiePersoonBericht.getAfnemerindicatieGegevens());
            }
            persoonslijsten.add(persoonslijstService.maak(persoonData, afnemerindicatieData, 0L));
        }
        resultaatQueue.addAll(persoonslijsten);
        LOGGER.debug("einde maak persoonslijst batch voor personen");
    }

    /**
     * @param queue queue
     */
    public void setResultaatQueue(Queue<Persoonslijst> queue) {
        this.resultaatQueue = queue;
    }

    /**
     * @param taakQueue taakQueue
     */
    public void setTaakQueue(BlockingQueue<MaakPersoonslijstBatchOpdracht> taakQueue) {
        this.taakQueue = taakQueue;
    }

    /**
     * @param persoonslijstService persoonslijstService
     */
    public void setPersoonslijstService(PersoonslijstService persoonslijstService) {
        this.persoonslijstService = persoonslijstService;
    }
}
