/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa.BetrokkenheidRepositoryImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BetrokkenheidRepositoryImpl betrokkenheidRepository;

    @Test
    public void testConcurrentModification() {
        final Relatie doelRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid doel = new Betrokkenheid(SoortBetrokkenheid.KIND, doelRelatie);

        doel.addBetrokkenheidOuderHistorie(maakBetrokkenheidOuderHistorie(doel));
        doel.addBetrokkenheidOuderHistorie(maakBetrokkenheidOuderHistorie(doel));
        doel.addBetrokkenheidOuderHistorie(maakBetrokkenheidOuderHistorie(doel));

        doel.addBetrokkenheidOuderlijkGezagHistorie(maakBetrokkenheidOuderlijkGezagHistorie(doel, true));
        doel.addBetrokkenheidOuderlijkGezagHistorie(maakBetrokkenheidOuderlijkGezagHistorie(doel, false));
        doel.addBetrokkenheidOuderlijkGezagHistorie(maakBetrokkenheidOuderlijkGezagHistorie(doel, true));

        doelRelatie.addBetrokkenheid(doel);

        // Bron heeft zowel voor OuderHistorie als OuderlijkGezagHistorie 2 minder gegevens dan Doel.
        // Dit triggerde tot voor kort een ConcurrentModificationException, maar als deze test slaagt
        // uiteraard niet meer.
        final Relatie bronRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid bron = new Betrokkenheid(SoortBetrokkenheid.KIND, bronRelatie);

        bron.addBetrokkenheidOuderHistorie(maakBetrokkenheidOuderHistorie(bron));
        bron.addBetrokkenheidOuderlijkGezagHistorie(maakBetrokkenheidOuderlijkGezagHistorie(bron, true));

        bronRelatie.addBetrokkenheid(bron);

        betrokkenheidRepository.overschrijfBetrokkenheid(doel, bron);
    }

    private BetrokkenheidOuderHistorie maakBetrokkenheidOuderHistorie(final Betrokkenheid betrokkenheid) {
        final BetrokkenheidOuderHistorie result = new BetrokkenheidOuderHistorie(betrokkenheid);
        result.setDatumTijdRegistratie(new Timestamp(1L));
        result.setDatumAanvangGeldigheid(20010101);
        return result;
    }

    private BetrokkenheidOuderlijkGezagHistorie maakBetrokkenheidOuderlijkGezagHistorie(
            final Betrokkenheid betrokkenheid,
            final boolean indicatieOuderHeeftGezag) {
        final BetrokkenheidOuderlijkGezagHistorie result = new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, indicatieOuderHeeftGezag);
        result.setDatumTijdRegistratie(new Timestamp(1L));
        result.setDatumAanvangGeldigheid(20010101);
        return result;
    }
}
