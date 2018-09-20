/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;


import java.util.Date;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.domain.kern.Actie;
import nl.bzk.brp.testdatageneratie.domain.kern.Admhnd;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Actie generator, genereert acties en administratieve handelingen.
 */
public class ActieGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(ActieGenerator.class);

    /**
     * Instantieert een nieuwe Actie generator.
     *
     * @param numberOfRecordsToProcess aantal records dat doorlopen wordt
     * @param batchBlockSize batch block size
     * @param threadIndex thread index
     */
    public ActieGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex) {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
    }

    @Override
    public GeneratorAdminData call() {
        try {
            int sleeptime = Constanten.VIJFTIG + RandomUtil.random.nextInt(256);
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return getAdminData();
        }

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();
            final long numberOfRecordsToProcess = getNumberOfRecordsToProcess();

            for (int i = 1; i <= numberOfRecordsToProcess; i++) {

                final Admhnd admhnd = new Admhnd();
                admhnd.setId(SequenceUtil.getMax(Admhnd.class.getSimpleName()));
                Short partij = null;
                while (partij == null) {
                    partij = RandomUtil.getPartijByBijhgem();
                }
                admhnd.setPartij(partij);

                if (RandomUtil.isFractie(Constanten.TWEE)) {
                    admhnd.setToelichtingontlening(RandomStringUtils.randomAlphabetic(Constanten.VIJFTIG));
                }
                admhnd.setSrt((short) RandomUtil.getRandom(SoortAdministratieveHandeling.values()).ordinal());

                final Date tsReg = RandomUtil.getPastTimestamp();
                admhnd.setTsreg(tsReg);
                admhnd.setTslev(tsReg);

                kernSession.save(admhnd);

                final Actie actie = new Actie();
                actie.setId(SequenceUtil.getMax(Actie.class.getSimpleName()));
                actie.setAdmhnd(admhnd.getId());
                actie.setPartij(admhnd.getPartij());
                actie.setSrt((short) RandomUtil.getRandom(SoortActie.values()).ordinal());
                actie.setTsreg(tsReg);
                kernSession.save(actie);

                updateCurrentCount(kernSession, i);
            }

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
