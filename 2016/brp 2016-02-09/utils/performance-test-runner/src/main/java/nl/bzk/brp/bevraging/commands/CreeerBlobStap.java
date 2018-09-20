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
import nl.bzk.brp.bevraging.app.support.PersoonIdentificatie;
import nl.bzk.brp.bevraging.dataaccess.PersoonsLijstService;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
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
    private PersoonsLijstService service;

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<PersoonIdentificatie> persoonIdentificaties = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Future<BevraagInfo>> futures = new ArrayList<Future<BevraagInfo>>(persoonIdentificaties.size());
        for (PersoonIdentificatie ident : persoonIdentificaties) {
            LOGGER.info("ophalen persoon met BSN '{}'", ident.getBsn());
            futures.add(taskExecutor.submit(new CreeerBlob(ident.getId())));
        }

        List<BevraagInfo> results = new ArrayList<BevraagInfo>(persoonIdentificaties.size());
        for (Future<BevraagInfo> future : futures) {
            BevraagInfo info = future.get();
            results.add(info);
            LOGGER.info("Creëer Blob cyclus ID '{}' in {} ms", info.getTaskName(), info.getTimeMillis());
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return CONTINUE_PROCESSING;
    }

    /**
     * Creeer een blob callable.
     */
    class CreeerBlob implements Callable<BevraagInfo> {
        private final Integer id;

        /**
         * Constructor.
         * @param id de id om de blob voor te creeeren.
         */
        public CreeerBlob(final Integer id) {
            this.id = id;
        }

        @Override
        public BevraagInfo call() {
            boolean success = true;

            long startTimeMillis = System.currentTimeMillis();

            try {
                PersoonHisVolledig model = service.haalPersoonOp(id);
            } catch (Exception e) {
                success = false;
            }

            long duration = System.currentTimeMillis() - startTimeMillis;
            return new BevraagInfo(id.toString(), (success ? "OK" : "FAIL"), duration);
        }
    }
}
