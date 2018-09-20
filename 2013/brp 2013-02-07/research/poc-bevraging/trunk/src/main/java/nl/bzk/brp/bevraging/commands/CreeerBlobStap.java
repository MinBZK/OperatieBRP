/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stap die PersoonHisModel instanties (de)serializeert.
 */
@Component
public class CreeerBlobStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreeerBlobStap.class);

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Future<BevraagInfo>> futures = new ArrayList<Future<BevraagInfo>>(bsns.size());
        for (Integer bsn : bsns) {
            futures.add(taskExecutor.submit(new CreeerBlob(bsn)));
        }

        List<BevraagInfo> results = new ArrayList<BevraagInfo>(bsns.size());
        for (Future<BevraagInfo> future : futures) {
            BevraagInfo info = future.get();
            results.add(info);
            LOGGER.info("Creëer Blob cyclus BSN '{}' in {} ms", info.getTaskName(), info.getTimeMillis());
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return false;
    }

    /**
     * Creeer een blob callable.
     */
    class CreeerBlob implements Callable<BevraagInfo> {
        private final Integer bsn;

        /**
         * Constructor.
         * @param bsn de bsn om de blob voor te creeeren.
         */
        public CreeerBlob(final Integer bsn) {
            this.bsn = bsn;
        }

        @Override
        public BevraagInfo call() {
            long startTimeMillis = System.currentTimeMillis();
            boolean success = true;

            try {
                LOGGER.info("ophalen persoon met BSN '{}'", bsn);
                PersoonModel persoon =
                        persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(bsn.toString()));
                LOGGER.info("ophalen hisModel: {}", persoon.getID());
                PersoonHisVolledig model = null;
                model = persoonHisVolledigRepository.haalPersoonOp(persoon.getID());
                LOGGER.info("gevonden hisModel: {}", model.getID());
            } catch (Exception e) {
                LOGGER.error("fout bij creeren blob", e);
            }

            long duration = System.currentTimeMillis() - startTimeMillis;
            return new BevraagInfo(bsn.toString(), (success ? "OK" : "FAIL"), duration);
        }
    }
}
