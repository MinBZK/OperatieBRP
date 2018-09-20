/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtbetr;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtrelatie;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class FamilieGenerator extends AbstractRelatieGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(FamilieGenerator.class);
    private PersoonIds persoonIds;
    private final int threadIndex;
    private final int numberOfThreads;

    public FamilieGenerator(final PersoonIds persoonIds, final int threadIndex, final int numberOfThreads) {
        this.persoonIds = persoonIds;
        this.threadIndex = threadIndex;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public Boolean call() {

        int rangeSize = persoonIds.getRangeSize();
        int localRangeSize = rangeSize / numberOfThreads;
        int displacement = (threadIndex - 1) * localRangeSize;
        if (threadIndex == numberOfThreads) {
            localRangeSize += rangeSize % numberOfThreads;
        }

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@@@@@@@ Familie @@@@@@@@@@@@@@@@@@@@@");

            for (long i = 1; i <= localRangeSize; i++) {

                voegFamilieToe(kernSession, persoonIds.getMin() - 1 + i + displacement);

                if (i % persoonIds.getBatchBlockSize() == 0) {
                    log.debug(threadIndex+":"+i);
                    if (Settings.SAVE) {
                        kernSession.getTransaction().commit();
                        kernSession.clear();
                        kernSession.getTransaction().begin();
                    }
                }
            }

            log.info("########### Familie #########################");

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

    private void voegFamilieToe(final Session kernSession, final long kindId) {
        Relatie relatie = creeerRelatie(Srtrelatie.F);

        Betr betrKind = new Betr(Srtbetr.K, relatie, kindId);
        long moederId = persoonIds.selecteerPersoonBehalve(kindId);
        Betr betrMoeder = creeerOuderschap(relatie, moederId);
        if (Settings.SAVE) {
            kernSession.save(relatie);
            opslaanBetrokkenheidMetHistorie(kernSession, betrKind);
            opslaanBetrokkenheidMetHistorie(kernSession, betrMoeder);
            kernSession.save(new HisRelatie(relatie));
        }

        if (!RandomService.isFractie(100)) {
            long vaderId = persoonIds.selecteerPersoonBehalve(kindId, moederId);
            Betr betrVader = creeerOuderschap(relatie, vaderId);
            if (Settings.SAVE) {
                opslaanBetrokkenheidMetHistorie(kernSession, betrVader);
            }
        }


    }

    private Betr creeerOuderschap(final Relatie relatie, final long persoonId) {
        Betr ouderschap = new Betr(Srtbetr.O, relatie, persoonId);
        ouderschap.setIndouder(true);
        ouderschap.setIndouderheeftgezag(!RandomService.isFractie(100));
        return ouderschap;
    }

}
