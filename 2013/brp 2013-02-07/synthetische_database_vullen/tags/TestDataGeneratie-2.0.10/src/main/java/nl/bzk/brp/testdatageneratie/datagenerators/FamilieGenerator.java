/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.creeerRelatie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.opslaanBetrokkenheidMetHistorie;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtbetr;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtrelatie;
import nl.bzk.brp.testdatageneratie.utils.HisCorrectieUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class FamilieGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(FamilieGenerator.class);
    private final Ids persoonIds;
    private final int threadIndex;
    private final int numberOfThreads;

    public FamilieGenerator(final Ids persoonIds, final int threadIndex, final int numberOfThreads) {
        this.persoonIds = persoonIds;
        this.threadIndex = threadIndex;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public Boolean call() {

        long rangeSize = persoonIds.getRangeSize();
        long localRangeSize = rangeSize / numberOfThreads;
        long displacement = (threadIndex - 1) * localRangeSize;
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
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
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
        Relatie relatie = creeerRelatie(Srtrelatie.F, RandomUtil.getGeboortePlaats(), RandomUtil.getDatGeboorte());

        Betr betrKind = new Betr(Srtbetr.K, relatie, kindId);
        long moederId = persoonIds.selecteerIdBehalve(kindId);
        Betr betrMoeder = creeerOuderschap(relatie, moederId);
        kernSession.save(relatie);
        opslaanBetrokkenheidMetHistorie(kernSession, betrKind);
        opslaanBetrokkenheidMetHistorie(kernSession, betrMoeder);
        HisRelatie hisRelatie = new HisRelatie(relatie);
        kernSession.save(hisRelatie);

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            HisRelatie hisRelatieCorrectie = (HisRelatie) HisCorrectieUtil.creeerHisCorrectie(new HisRelatie(relatie), hisRelatie);
            kernSession.save(hisRelatieCorrectie);
        }

        if (!RandomUtil.isFractie(100)) {
            long vaderId = persoonIds.selecteerIdBehalve(kindId, moederId);
            Betr betrVader = creeerOuderschap(relatie, vaderId);
            opslaanBetrokkenheidMetHistorie(kernSession, betrVader);
        }


    }

    private Betr creeerOuderschap(final Relatie relatie, final long persoonId) {
        Betr ouderschap = new Betr(Srtbetr.O, relatie, persoonId);
        ouderschap.setIndouder(true);
        ouderschap.setIndouderheeftgezag(!RandomUtil.isFractie(100));
        return ouderschap;
    }

}
