/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PartijRepository;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BetrokkenheidOuderHistorieRepository} class. */
public class BetrokkenheidOuderHistorieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BetrokkenheidOuderHistorieRepository betrokkenheidOuderHistorieRepository;

    @Inject
    private PartijRepository partijRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test() {
        PersistentActie actie = maakActie();
        betrokkenheidOuderHistorieRepository.opslaanHistorie(bouwBetrokkenheid(true), actie, 20120326, null);
        //Omzeilen van unique constraint "br4032"
        actie.setTijdstipRegistratie(new Date(actie.getTijdstipRegistratie().getTime() + 1));
        betrokkenheidOuderHistorieRepository.opslaanHistorie(bouwBetrokkenheid(false), actie, 20120526, null);
        //Omzeilen van unique constraint "br4032"
        actie.setTijdstipRegistratie(new Date(actie.getTijdstipRegistratie().getTime() + 1));
        betrokkenheidOuderHistorieRepository.opslaanHistorie(bouwBetrokkenheid(false), actie, 20120401, 20120420);
    }

    private PersistentBetrokkenheid bouwBetrokkenheid(final Boolean waarde) {
        PersistentBetrokkenheid betrokkenheidOuder = new PersistentBetrokkenheid();
        betrokkenheidOuder.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);

        PersistentPersoon persoon = new PersistentPersoon();
        persoon.setId(3L);
        PersistentRelatie relatie = new PersistentRelatie();
        ReflectionTestUtils.setField(relatie, "id", 1L);

        ReflectionTestUtils.setField(betrokkenheidOuder, "id", 7);
        betrokkenheidOuder.setBetrokkene(persoon);
        betrokkenheidOuder.setRelatie(relatie);
        betrokkenheidOuder.setIndOuder(waarde);
        return betrokkenheidOuder;
    }

    private PersistentActie maakActie() {
        PersistentActie actie = new PersistentActie();
        actie.setTijdstipRegistratie(new Date(System.currentTimeMillis() - 1));
        actie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actie.setPartij(
                partijRepository.findOne(4)
        );
        em.persist(actie);
        return actie;
    }
}
