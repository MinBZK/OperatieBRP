/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.Gegeveninonderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOnderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Onderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Personderzoek;
import nl.bzk.brp.testdatageneratie.utils.HisCorrectieUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class OnderzoekGenerator implements Callable<Boolean> {

    private static Logger    log = Logger.getLogger(OnderzoekGenerator.class);
    private final int        numberOfRecordsToProcess;
    private final int        batchBlockSize;
    private final Ids persoonIds;

    public OnderzoekGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final Ids persoonIds)
    {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.persoonIds = persoonIds;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Onderzoeken / Gegevens in onderzoek @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                nl.bzk.brp.testdatageneratie.domain.bronnen.OnderzoekId onderzoekBron =
                    BronnenRepo.getBron(nl.bzk.brp.testdatageneratie.domain.bronnen.Onderzoek.class).getId();

                Onderzoek onderzoek = new Onderzoek();
                onderzoek.setDatbegin(onderzoekBron.getStart());
                onderzoek.setDateinde(onderzoekBron.getEind());

                if (isFractie(2))
                    onderzoek.setOms(RandomStringUtils.randomAlphabetic(50));

                kernSession.save(onderzoek);
                HisOnderzoek hisOnderzoekOpen = new HisOnderzoek(onderzoek);
                kernSession.save(hisOnderzoekOpen);
                if (onderzoek.getDateinde() != null) {
                    HisOnderzoek hisOnderzoekSluiten = new HisOnderzoek(onderzoek);
                    kernSession.save(hisOnderzoekSluiten);
                }

                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisOnderzoek hisOnderzoekCorrectie = (HisOnderzoek) HisCorrectieUtil.creeerHisCorrectie(new HisOnderzoek(onderzoek), hisOnderzoekOpen);
                    kernSession.save(hisOnderzoekCorrectie);
                }

                Gegeveninonderzoek gegeveninonderzoek = new Gegeveninonderzoek();
                gegeveninonderzoek.setOnderzoek(onderzoek);
                gegeveninonderzoek.setDbobject(onderzoekBron.getDbobject());
                gegeveninonderzoek.setIdent(RandomUtil.random.nextLong());

                kernSession.save(gegeveninonderzoek);

                int personderzoekAantal = isFractie(5)? 2: 1;

                Long vorigePersoonId = null;
                for (int j = 1; j <= personderzoekAantal; j++) {
                    Long persoonId = persoonIds.selecteerId();
                    if (!persoonId.equals(vorigePersoonId)) {
                        Personderzoek personderzoek = new Personderzoek();
                        personderzoek.setOnderzoek(onderzoek);
                        personderzoek.setPers(persoonId);

                        vorigePersoonId = persoonId;

                        kernSession.save(personderzoek);
                    }
                }

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("########## Onderzoeken / Gegevens in onderzoek ##############");

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
