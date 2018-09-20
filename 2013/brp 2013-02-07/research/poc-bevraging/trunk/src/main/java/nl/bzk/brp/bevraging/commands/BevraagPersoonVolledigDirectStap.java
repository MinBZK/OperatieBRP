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
import org.springframework.transaction.annotation.Transactional;

/**
 * Bevraag de database met behulp van JPA.
 */
@Component
public class BevraagPersoonVolledigDirectStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BevraagPersoonVolledigDirectStap.class);

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    @Transactional
    public boolean doExecute(final Context context) throws Exception {
        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Future<BevraagInfo>> futures = new ArrayList<Future<BevraagInfo>>(bsns.size());

        for (Integer bsn : bsns) {
            futures.add(this.taskExecutor.submit(new BevraagDB(bsn)));
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

    /**
     * Bevraag de database.
     */
    class BevraagDB implements Callable<BevraagInfo> {
        private Integer bsn;

        /**
         * Constructor.
         * @param bsn om de DB mee te bevragen
         */
        public BevraagDB(final Integer bsn) {
            this.bsn = bsn;
        }

        @Override
        public BevraagInfo call() {
            PersoonModel model = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(bsn));

            long startTimeMillis = System.currentTimeMillis();

            PersoonHisVolledig volledig = persoonHisVolledigRepository.leesGenormalizeerdModel(model.getID());

            long duration = System.currentTimeMillis() - startTimeMillis;
            return new BevraagInfo(bsn.toString(), (volledig != null ? "OK" : "FAIL"), duration);
        }
    }
}
