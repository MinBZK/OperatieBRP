/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;


import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.creeerRelatie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.getRelatieStartPlaats;

import java.util.Date;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.PersonenIds;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuurId;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class RelatieHistorieGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(RelatieHistorieGenerator.class);
    private final Ids persoonIds;


    /**
     * Instantieert Relatie historie generator.
     *
     * @param threadIndex thread index
     * @param persoonIds persoon ids
     * @param numberOfRecordsToProcess number of records to process
     * @param batchBlockSize batch block size
     */
    public RelatieHistorieGenerator(final int threadIndex, final Ids persoonIds, final int numberOfRecordsToProcess, final int batchBlockSize) {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.persoonIds = persoonIds;
    }

    @Override
    public GeneratorAdminData call() {
        try {
            Thread.sleep(50 + RandomUtil.random.nextInt(256));
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return getAdminData();
        }

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Historische Relaties @@@@@@@@@@@@@@@@");

            for (int i = 1; i <= getNumberOfRecordsToProcess(); i++) {

                Locatie locatieAanvang = getRelatieStartPlaats();
                RelatieDuur relatieDuur = BronnenRepo.getBron(RelatieDuur.class);
                RelatieDuurId relatiePeriode = BronnenRepo.getBron(RelatieDuur.class).getId();
                int datumAanvang = relatiePeriode.getStart();
                Relatie relatie = creeerRelatie(RandomUtil.getSrtrelatie(), locatieAanvang, datumAanvang);
                relatie.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());

                Locatie locatieEinde = getRelatieStartPlaats();
                relatie.setLandgebiedeinde(locatieEinde.getLandgebied().getId());
                Gem gem = locatieEinde.getGemeente();
                relatie.setGemeinde(null == gem ? null : gem.getId());

                Date javaDatumEinde;
                if (datumAanvang < 10000000) {
                    javaDatumEinde = RandomUtil.getPastTimestamp();
                } else {
                    Date javaDatumAanvang = GenUtil.vanBrpDatum(datumAanvang);
                    javaDatumEinde = RandomUtil
                            .getPastTimestamp(System.currentTimeMillis(),
                                              System.currentTimeMillis() - javaDatumAanvang.getTime());
                }
                int brpDatumEinde = GenUtil.naarBrpDatum(javaDatumEinde);
                relatie.setDateinde(brpDatumEinde);

                relatie.setRdneinde(RandomUtil.getRedenEindeRelatieShort());
                if (locatieEinde.isNederland()) {
                    // NL
                    relatie.setWplnaameinde(RandomUtil.getWplGeboorte().getNaam());
                } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
                    //Land onbekend, gebruik omschrijving:
                    relatie.setOmsloceinde(locatieAanvang.getPlaats());
                } else {
                    // Buitenland
                    String blPlaatsEinde = locatieEinde.getPlaats();
                    if (null != blPlaatsEinde && "".equals(blPlaatsEinde)) {
                        // constraint of NULL of length > 0
                        blPlaatsEinde = "random blPlaatsEinde";
                    }
                    relatie.setBlplaatseinde(blPlaatsEinde);
                    if (RandomUtil.isFractie(Constanten.TIEN)) {
                        relatie.setBlregioeinde(RandomStringUtils.randomAlphabetic(Constanten.VIJFENDERTIG));
                    }
                }
                kernSession.save(relatie);

                HisHuwelijkgeregistreerdpar hisVervallen = HisUtil.creeerHisHuwelijkgeregistreerdpar(relatie);
                hisVervallen.setTsverval(RandomUtil.getTimestamp(brpDatumEinde));
                hisVervallen.setActieverval(RandomUtil.getActie());
                hisVervallen.setId(SequenceUtil.getMax(HisHuwelijkgeregistreerdpar.class.getSimpleName()).intValue());
                kernSession.save(hisVervallen);

                HisHuwelijkgeregistreerdpar hisBeeindigd = HisUtil.creeerHisHuwelijkgeregistreerdpar(relatie);
                hisBeeindigd.setDateinde(brpDatumEinde);
                hisBeeindigd.setId(SequenceUtil.getMax(HisHuwelijkgeregistreerdpar.class.getSimpleName()).intValue());
                kernSession.save(hisBeeindigd);

                int persoon1 = (int) persoonIds.selecteerId();
                if (((PersonenIds) persoonIds).isBrp(persoon1)) {
                    log.error("Oeps ... " + persoon1 + " is brp");
                }

                Betr b1 = new Betr((short) SoortBetrokkenheid.PARTNER.ordinal(), relatie.getId(), persoon1);
                b1.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
                kernSession.save(b1);
                Betr b2 = new Betr((short) SoortBetrokkenheid.PARTNER.ordinal(), relatie.getId(),
                        (int) persoonIds.selecteerIdBehalve((long) persoon1));
                b2.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
                kernSession.save(b2);

                updateCurrentCount(kernSession, i);
            }

            log.info("########## Historische Relaties ##############");

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
