/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;


import static nl.bzk.brp.testdatageneratie.RandomService.isFractie;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.BronnenRepo;
import nl.bzk.brp.testdatageneratie.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.Huwelijk;
import nl.bzk.brp.testdatageneratie.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.PersoonIds;
import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.Settings;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuurId;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtbetr;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class RelatieGenerator extends AbstractRelatieGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(RelatieGenerator.class);

    private final PersoonIds persoonIds;
    private final Huwelijksboot huwelijksboot;

    public RelatieGenerator(final PersoonIds persoonIds, final Huwelijksboot huwelijksboot) {
        this.persoonIds = persoonIds;
        this.huwelijksboot = huwelijksboot;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Relaties @@@@@@@@@@@@@@@@");

            Huwelijk huwelijk;
            int i = 0;
            while ((huwelijk = huwelijksboot.volgendeHuwelijk()) != null) {

                voegRelatieToe(kernSession, huwelijk);

                if (i++ % persoonIds.getBatchBlockSize() == 0) {
                    log.debug(i);
                    if (Settings.SAVE) {
                        kernSession.getTransaction().commit();
                        kernSession.clear();
                        kernSession.getTransaction().begin();
                    }
                }
            }

            log.info("########## Relaties ##############");

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

    private void voegRelatieToe(final Session kernSession, final Huwelijk huwelijk) {
        Locatie locatieAanvang = getRelatieStartPlaats();
        RelatieDuurId relatiePeriode = BronnenRepo.getBron(RelatieDuur.class).getId();
        Relatie relatie = creeerRelatie(RandomService.getSrtrelatie(), locatieAanvang, relatiePeriode.getStart());

        if (Settings.SAVE) kernSession.save(relatie);

        Betr b1 = new Betr(Srtbetr.P, relatie, huwelijk.getPersIdMan());
        Betr b2 = new Betr(Srtbetr.P, relatie, huwelijk.getPersIdVrouw());

        if (Settings.SAVE) {
            opslaanBetrokkenheidMetHistorie(kernSession, b1);
            opslaanBetrokkenheidMetHistorie(kernSession, b2);

            HisRelatie hisRelatie = new HisRelatie(relatie);
            kernSession.save(hisRelatie);

            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisRelatie hisRelatieCorrectie = (HisRelatie) HisCorrectieGenerator.creeerHisCorrectie(new HisRelatie(relatie), hisRelatie);
                kernSession.save(hisRelatieCorrectie);
            }
        }
    }
}
