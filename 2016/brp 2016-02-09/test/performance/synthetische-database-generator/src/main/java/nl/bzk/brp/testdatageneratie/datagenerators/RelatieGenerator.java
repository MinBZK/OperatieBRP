/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.creeerRelatie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.getRelatieStartPlaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.PersonenIds;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuurId;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.helper.Huwelijk;
import nl.bzk.brp.testdatageneratie.helper.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.helper.brpbuilder.BrpRelatieBuilder;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;


public class RelatieGenerator extends AbstractBasicGenerator {

    private static Logger       log = Logger.getLogger(RelatieGenerator.class);

    private final int     threadIndex;
    private final int     numberOfThreads;
    private final PersonenIds           persoonIds;
    private final Huwelijksboot huwelijksboot;
    private final ArtHuishoudingDto brpHuishouding;

    public RelatieGenerator(final PersonenIds persoonIds, final Huwelijksboot huwelijksboot,
            final int threadIndex, final int numberOfThreads,
            final ArtHuishoudingDto brpHuishouding
            )
    {
        super(huwelijksboot.getAantalHuwelijken(), persoonIds.getBatchBlockSize(), threadIndex);
        this.threadIndex = threadIndex;
        this.numberOfThreads = numberOfThreads;
        this.persoonIds = persoonIds;
        this.huwelijksboot = huwelijksboot;
        this.brpHuishouding = brpHuishouding;
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
            final BrpRelatieBuilder brpBuilder = new BrpRelatieBuilder(
                    this, kernSession, brpHuishouding, threadIndex, numberOfThreads, persoonIds);

            log.info("@@@@@@@@@ Relaties @@@@@@@@@@@@@@@@");

            Huwelijk huwelijk;
            int i = 0;
            log.debug("# huwelijken in boot: " + huwelijksboot.getAantalHuwelijken());
            while ((huwelijk = huwelijksboot.volgendeHuwelijk()) != null) {

                if(isFractie(2)) {
                    Integer saved = null;
                    // we hebben geen limiet aantal voor huwelijken, het is aantal personen dat gevraagd wordt.
                    saved = brpBuilder.voegAlleRelatiesBehalveFamilieToe();
                    if (null != saved) {
                        updateCurrentCount(kernSession, i++);
                    }
                }

                voegRelatieToe(kernSession, huwelijk);
                updateCurrentCount(kernSession, i++);

            }

            brpBuilder.voegRestOfHuidigSetToe(
                    BrpRelatieBuilder.SRT_HUWELIJK, BrpRelatieBuilder.SRT_GEREGPARTNER,
                    BrpRelatieBuilder.SRT_ERKENNING_OV, BrpRelatieBuilder.SRT_ONTKENNNING_OV,
                    BrpRelatieBuilder.SRT_NAAMKEUZE);
            log.info("########## Relaties ##############");

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

    private void voegRelatieToe(final Session kernSession, final Huwelijk huwelijk) {
        Locatie locatieAanvang = getRelatieStartPlaats();
        RelatieDuur relatieDuur = BronnenRepo.getBron(RelatieDuur.class);
        RelatieDuurId relatiePeriode = null == relatieDuur ? null : relatieDuur.getId();
        Relatie relatie = creeerRelatie(RandomUtil.getSrtrelatie(), locatieAanvang,
                    null == relatiePeriode ? null : relatiePeriode.getStart());
        relatie.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());
        kernSession.save(relatie);

        Betr b1 = new Betr((short) SoortBetrokkenheid.PARTNER.ordinal(), relatie.getId(), huwelijk.getPersIdMan());
//        if (((PersonenIds)persoonIds).isBrp(b1.getPers())) {
//            log.error("Oeps ... " + b1.getPers() + " is brp");
//        }

        b1.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        kernSession.save(b1);
        Betr b2 = new Betr((short) SoortBetrokkenheid.PARTNER.ordinal(), relatie.getId(), huwelijk.getPersIdVrouw());
//        if (((PersonenIds)persoonIds).isBrp(b2.getPers())) {
//            log.error("Oeps ... " + b2.getPers() + " is brp");
//        }
        b2.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        kernSession.save(b2);

        HisHuwelijkgeregistreerdpar hisHuwelijkgeregistreerdpar = HisUtil.creeerHisHuwelijkgeregistreerdpar(relatie);
        hisHuwelijkgeregistreerdpar.setId(SequenceUtil.getMax(HisHuwelijkgeregistreerdpar.class.getSimpleName())
                                                  .intValue());
        kernSession.save(hisHuwelijkgeregistreerdpar);

        if (RandomUtil.isFractie(His.CORRECTIE_FRACTIE)) {
            HisHuwelijkgeregistreerdpar hisHuwelijkgeregistreerdparCorrectie =
                (HisHuwelijkgeregistreerdpar) HisUtil.creeerHisCorrectie(
                        HisUtil.creeerHisHuwelijkgeregistreerdpar(relatie), hisHuwelijkgeregistreerdpar);
            hisHuwelijkgeregistreerdparCorrectie.setId(SequenceUtil.getMax(HisHuwelijkgeregistreerdpar.class
                                                                                   .getSimpleName()).intValue());
            kernSession.save(hisHuwelijkgeregistreerdparCorrectie);
        }
    }
}
