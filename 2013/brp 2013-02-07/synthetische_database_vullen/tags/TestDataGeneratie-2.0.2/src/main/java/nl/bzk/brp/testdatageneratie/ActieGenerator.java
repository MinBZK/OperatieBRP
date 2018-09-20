/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;


import java.util.Date;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.domain.kern.Actie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtactie;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class ActieGenerator extends AbstractRelatieGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(ActieGenerator.class);
    private final int numberOfRecordsToProcess;
    private final int batchBlockSize;

    public ActieGenerator(final int numberOfRecordsToProcess, final int batchBlockSize) {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Acties @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                Actie actie = new Actie();
                actie.setPartij(RandomService.getPartijByBijhgem());
                actie.setSrtactie(RandomService.getRandom(Srtactie.values()));
                Date tijdstip = RandomService.getPastTimestamp();
                actie.setTijdstipontlening(tijdstip);
                actie.setTijdstipreg(tijdstip);
                if (Settings.SAVE) kernSession.save(actie);

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    if (Settings.SAVE) {
                        kernSession.getTransaction().commit();
                        kernSession.clear();
                        kernSession.getTransaction().begin();
                    }
                }
            }

            log.info("########## Acties ##############");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) try {
                kernSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
        return true;
    }

}
