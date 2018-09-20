/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;


import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.creeerRelatie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.getRelatieStartPlaats;

import java.util.Date;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuurId;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtbetr;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class RelatieHistorieGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(RelatieHistorieGenerator.class);
    private final Ids persoonIds;
    private final int numberOfRecordsToProcess;
    private final int batchBlockSize;

    public RelatieHistorieGenerator(final Ids persoonIds, final int numberOfRecordsToProcess, final int batchBlockSize) {
        this.persoonIds = persoonIds;
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Historische Relaties @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                Locatie locatieAanvang = getRelatieStartPlaats();
                RelatieDuurId relatiePeriode = BronnenRepo.getBron(RelatieDuur.class).getId();
                int datumAanvang = relatiePeriode.getStart();
                Relatie relatie = creeerRelatie(RandomUtil.getSrtrelatie(), locatieAanvang, datumAanvang);

                Locatie locatieEinde = getRelatieStartPlaats();
                relatie.setLandByLandeinde(locatieEinde.getLand());
                relatie.setPartijByGemeinde(locatieEinde.getPartij());

                Date javaDatumEinde;
                if (datumAanvang < 10000000) {
                    javaDatumEinde = RandomUtil.getPastTimestamp();
                } else {
                    Date javaDatumAanvang = GenUtil.vanBrpDatum(datumAanvang);
                    javaDatumEinde = RandomUtil.getPastTimestamp(System.currentTimeMillis(), System.currentTimeMillis() - javaDatumAanvang.getTime());
                }
                int brpDatumEinde = GenUtil.naarBrpDatum(javaDatumEinde);
                relatie.setDateinde(brpDatumEinde);

                relatie.setRdnbeeindrelatie(RandomUtil.getRedenEindeRelatie());
                if (locatieEinde.isNederland()) {
                    // NL
                    relatie.setPlaatsByWpleinde(RandomUtil.getWplGeboorte());
                } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
                    //Land onbekend, gebruik omschrijving:
                    relatie.setOmsloceinde(locatieAanvang.getPlaats());
                } else {
                    // Buitenland
                    relatie.setBlplaatseinde(locatieEinde.getPlaats());
                    if (RandomUtil.isFractie(10)) {
                        relatie.setBlregioeinde(RandomStringUtils.randomAlphabetic(35));
                    }
                }

                kernSession.save(relatie);

                HisRelatie hisRelatieVervallen = new HisRelatie(relatie);
                hisRelatieVervallen.setTsverval(RandomUtil.getTimestamp(brpDatumEinde));
                hisRelatieVervallen.setActieByActieverval(RandomUtil.getActie());

                HisRelatie hisRelatieBeeindigd = new HisRelatie(relatie);
                hisRelatieBeeindigd.setDateinde(brpDatumEinde);

                long persoon1 = persoonIds.selecteerId();
                Betr b1 = new Betr(Srtbetr.P, relatie, persoon1);
                Betr b2 = new Betr(Srtbetr.P, relatie, persoonIds.selecteerIdBehalve(persoon1));

                kernSession.save(b1);
                kernSession.save(b2);
                kernSession.save(hisRelatieVervallen);
                kernSession.save(hisRelatieBeeindigd);

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("########## Historische Relaties ##############");

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
