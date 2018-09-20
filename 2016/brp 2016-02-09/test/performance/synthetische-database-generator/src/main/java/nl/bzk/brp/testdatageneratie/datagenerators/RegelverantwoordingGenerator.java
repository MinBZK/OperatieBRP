/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Regel;
import nl.bzk.brp.testdatageneratie.domain.kern.Regelverantwoording;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;


public class RegelverantwoordingGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(RegelverantwoordingGenerator.class);
    private final Ids     actieIds;

    public RegelverantwoordingGenerator(final int numberOfRecordsToProcess, final int batchBlockSize,
            final int threadIndex, final Ids actieIds) {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.actieIds = actieIds;
    }

    @Override
    public GeneratorAdminData call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Regelverantwoordingen @@@@@@@@@@@@@@@@");

            for (int i = 1; i <= getNumberOfRecordsToProcess(); i++) {

                Regelverantwoording regelverantwoording = new Regelverantwoording();
                regelverantwoording.setId(SequenceUtil.getMax(Regelverantwoording.class.getSimpleName()));
                regelverantwoording.setRegel(MetaRepo.get(Regel.class).getId());
                Long actieId = actieIds.selecteerId();
                regelverantwoording.setActie(actieId);

                kernSession.save(regelverantwoording);

                updateCurrentCount(kernSession, i);
            }

            log.info("########## Regelverantwoordingen ##############");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) {
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
        finish();
        return getAdminData();
    }

}
