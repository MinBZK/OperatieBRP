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
import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.bevraging.dataaccess.PersoonsLijstJdbcService;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Stap verantwoordelijk voor het bevragen van de database op basis van een BSN lijst.
 */
@Component
public class BevraagPersoonslijstMetHistorieJdbcStap implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(BevraagPersoonslijstMetHistorieJdbcStap.class);

    private ThreadPoolTaskExecutor taskExecutor;

    @Inject
    private PersoonsLijstJdbcService persoonsLijstService;

    @Override
    public boolean execute(final Context context) throws Exception {
        configureTaskExecutor(context);

        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Future<BevraagInfo>> futures = new ArrayList<Future<BevraagInfo>>(bsns.size());

        for (Integer bsn : bsns) {
            futures.add(taskExecutor.submit(new BevraagDB(bsn)));
        }

        List<BevraagInfo> results = new ArrayList<BevraagInfo>(bsns.size());
        for (Future<BevraagInfo> future : futures) {
            BevraagInfo info = future.get();
            results.add(info);
            LOGGER.info("Gevraagd BSN '{}' in {} ms", info.getTaskName(), info.getTimeMillis());
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return false;
    }

    private void configureTaskExecutor(final Context context) {
        // configureer volgens de settings in context
        this.taskExecutor = new ThreadPoolTaskExecutor();

        Integer queueSize = (Integer) context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN);
        this.taskExecutor.setQueueCapacity(queueSize + queueSize/10);
        this.taskExecutor.setCorePoolSize((Integer) context.get(ContextParameterNames.AANTAL_THREADS));
        this.taskExecutor.setMaxPoolSize((Integer) context.get(ContextParameterNames.AANTAL_THREADS));

        this.taskExecutor.afterPropertiesSet();
    }

    /**
     * Class voor het vasthouden van de duur per persoonslijst voor rapportage doeleinden.
     */
    class BevraagDB implements Callable<BevraagInfo> {
        private final Integer bsn;

        public BevraagDB(final Integer bsn) {
            this.bsn = bsn;
        }

        @Override
        public BevraagInfo call() {
            long startTimeMillis = System.currentTimeMillis();

            PersoonsLijst lijst = persoonsLijstService.getPersoonsLijst(bsn);
            long duration = System.currentTimeMillis() - startTimeMillis;

            return new BevraagInfo(bsn.toString(), (lijst != null ? "OK" : "FAIL"), duration);
        }
    }
}
