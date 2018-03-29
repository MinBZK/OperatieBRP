/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.delivery.dal;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroonElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProtocolleringRepositoryImplTest.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:protocollering-delivery-dal-test-context.xml")
public class ProtocolleringRepositoryImplTest {

    @Inject
    private ProtocolleringRepository protocolleringRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.protocollering")
    private EntityManager em;

    @Test
    public void testSlaOp() {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Leveringsaantekening leveringsaantekening = new Leveringsaantekening(1, 2, timestamp, timestamp);
        leveringsaantekening.setAdministratieveHandeling(1L);
        leveringsaantekening.setSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT);
        leveringsaantekening.setDatumTijdAanvangFormelePeriodeResultaat(timestamp);

        final LeveringsaantekeningPersoon
                leveringPersoonModel =
                new LeveringsaantekeningPersoon(leveringsaantekening, 2L, timestamp, leveringsaantekening.getDatumTijdKlaarzettenLevering());
        leveringsaantekening.addLeveringsaantekeningPersoon(leveringPersoonModel);
        protocolleringRepository.opslaanNieuweLevering(leveringsaantekening);
    }

    @Test
    public void testOpslaanNieuwScopePatroon() {
        final ScopePatroon scopePatroon = new ScopePatroon();
        final ScopePatroonElement element = new ScopePatroonElement();
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId());
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId());
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId());
        scopePatroon.addScopePatroonElement(element);

        protocolleringRepository.opslaanNieuwScopePatroon(scopePatroon);

        final List<ScopePatroon> scopePatronen = protocolleringRepository.getScopePatronen();

        Assert.assertEquals(1, scopePatronen.size());
        Assert.assertNotNull(scopePatroon.getId());
    }


    @Test
    public void testOpslaanBestaandScopePatroon() {

        final ScopePatroon scopePatroon = new ScopePatroon();
        final ScopePatroonElement element = new ScopePatroonElement();
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId());
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId());
        element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId());
        scopePatroon.addScopePatroonElement(element);

        protocolleringRepository.opslaanNieuwScopePatroon(scopePatroon);

        //detach patroon
        em.detach(scopePatroon);

        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Leveringsaantekening leveringsaantekening = new Leveringsaantekening(1, 2, timestamp, timestamp);
        leveringsaantekening.setAdministratieveHandeling(1L);
        leveringsaantekening.setSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT);
        leveringsaantekening.setDatumTijdAanvangFormelePeriodeResultaat(timestamp);
        leveringsaantekening.setScopePatroon(scopePatroon);

        final LeveringsaantekeningPersoon
                leveringPersoonModel =
                new LeveringsaantekeningPersoon(leveringsaantekening, 2L, timestamp, leveringsaantekening.getDatumTijdKlaarzettenLevering());
        leveringsaantekening.addLeveringsaantekeningPersoon(leveringPersoonModel);
        protocolleringRepository.opslaanNieuweLevering(leveringsaantekening);
    }

    @Test
    public void testGetScopePatronen() {

        {
            final ScopePatroon patroon = new ScopePatroon();
            final ScopePatroonElement element = new ScopePatroonElement();
            element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId());
            patroon.addScopePatroonElement(element);
            protocolleringRepository.opslaanNieuwScopePatroon(patroon);
        }
        {
            final ScopePatroon patroon = new ScopePatroon();
            final ScopePatroonElement element = new ScopePatroonElement();
            element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId());
            patroon.addScopePatroonElement(element);
            protocolleringRepository.opslaanNieuwScopePatroon(patroon);
        }
        {
            final ScopePatroon patroon = new ScopePatroon();
            final ScopePatroonElement element = new ScopePatroonElement();
            element.setElementId(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId());
            patroon.addScopePatroonElement(element);
            protocolleringRepository.opslaanNieuwScopePatroon(patroon);
        }

        final List<ScopePatroon> scopePatronen = protocolleringRepository.getScopePatronen();
        Assert.assertEquals(3, scopePatronen.size());

        final Set<Integer> elementSet = new HashSet<>();
        for (final ScopePatroon scopePatroon : scopePatronen) {
            Assert.assertEquals(1, scopePatroon.getScopePatroonElementSet().size());
            elementSet.add(scopePatroon.getScopePatroonElementSet().iterator().next().getElementId());
        }

        Assert.assertTrue(elementSet.containsAll(Sets.newSet(
                Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(),
                Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(),
                Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId()
        )));
    }

}
