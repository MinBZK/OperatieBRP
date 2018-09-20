/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisMultirealiteitregel;
import nl.bzk.brp.testdatageneratie.domain.kern.Multirealiteitregel;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtmultirealiteitregel;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class MultirealiteitGenerator implements Callable<Boolean> {

    private static Logger    log = Logger.getLogger(MultirealiteitGenerator.class);
    private final int        numberOfRecordsToProcess;
    private final int        batchBlockSize;
    private final Ids persoonIds;
    private final Ids relatieIds;
    private final Ids betrokkenheidIds;

    public MultirealiteitGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final Ids persoonIds, final Ids relatieIds, final Ids betrokkenheidIds)
    {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.persoonIds = persoonIds;
        this.relatieIds = relatieIds;
        this.betrokkenheidIds = betrokkenheidIds;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Multirealiteit @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                Multirealiteitregel regel = getMultirealiteit();
                kernSession.save(regel);

                HisMultirealiteitregel hisRegel = new HisMultirealiteitregel(regel);
                kernSession.save(hisRegel);

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("########## Multirealiteit ##############");

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

    protected Multirealiteitregel getMultirealiteit() {
        Multirealiteitregel regel = new Multirealiteitregel();
        Long persoonId = persoonIds.selecteerId();

        if (!RandomUtil.isFractie(10)) {
            regel.setSrtmultirealiteitregel(Srtmultirealiteitregel.PERSOON);
            long persId = persoonIds.selecteerIdBehalve(persoonId);
            regel.setPersByPers(persId);
            regel.setPersByMultirealiteitpers(persoonIds.selecteerIdBehalve(persoonId, persId));
        } else if (RandomUtil.isFractie(2)) {
            regel.setSrtmultirealiteitregel(Srtmultirealiteitregel.RELATIE);
            regel.setRelatie(relatieIds.selecteerId());
        } else {
            regel.setSrtmultirealiteitregel(Srtmultirealiteitregel.BETROKKENHEID);
            regel.setBetr(betrokkenheidIds.selecteerId());
        }

        regel.setPersByGeldigvoor(persoonId);
        return regel;
    }

}
