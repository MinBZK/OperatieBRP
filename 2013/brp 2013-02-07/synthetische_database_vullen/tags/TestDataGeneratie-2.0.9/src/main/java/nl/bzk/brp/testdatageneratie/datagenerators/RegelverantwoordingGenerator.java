/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Regel;
import nl.bzk.brp.testdatageneratie.domain.kern.Regelverantwoording;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class RegelverantwoordingGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(RegelverantwoordingGenerator.class);
    private final int     numberOfRecordsToProcess;
    private final int     batchBlockSize;
    private final Ids     actieIds;

    public RegelverantwoordingGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final Ids actieIds) {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.actieIds = actieIds;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Regelverantwoordingen @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                Regelverantwoording regelverantwoording = new Regelverantwoording();
                regelverantwoording.setRegel(MetaRepo.get(Regel.class));
                Long actieId = RandomUtil.nextLong(actieIds.getRangeSize()) + actieIds.getMin();
                regelverantwoording.setActie(actieId);

                kernSession.save(regelverantwoording);

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("########## Regelverantwoordingen ##############");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null)
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
        }
        return true;
    }

}
