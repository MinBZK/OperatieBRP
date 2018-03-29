/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroonElement;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ScopePatroonServiceImplTest {

    @InjectMocks
    private ScopePatroonServiceImpl patroonService;

    @Mock
    private ProtocolleringRepository protocolleringRepository;

    @Test
    public void testOpslaanNieuwScopePatroon() {
        final ScopePatroon scopePatroon = patroonService.getScopePatroon(Sets.newSet(1, 2, 3));
        Mockito.verify(protocolleringRepository).opslaanNieuwScopePatroon(scopePatroon);
    }

    @Test
    public void testPatroonZelfdeVolgorde() {
        final ScopePatroon scopePatroon = patroonService.getScopePatroon(Sets.newSet(1, 2, 3));
        Assert.notNull(scopePatroon);
        final ScopePatroon scopePatroon2 = patroonService.getScopePatroon(Sets.newSet(1, 2, 3));
        Assert.isTrue(scopePatroon == scopePatroon2);
        Mockito.verify(protocolleringRepository, Mockito.times(1)).opslaanNieuwScopePatroon(scopePatroon);
    }

    @Test
    public void testPatroonAndereVolgorde() {
        final ScopePatroon scopePatroon = patroonService.getScopePatroon(Sets.newSet(1, 2, 3));
        Assert.notNull(scopePatroon);
        final ScopePatroon scopePatroon2 = patroonService.getScopePatroon(Sets.newSet(3, 1, 2));
        Assert.isTrue(scopePatroon == scopePatroon2);
        Mockito.verify(protocolleringRepository, Mockito.times(1)).opslaanNieuwScopePatroon(scopePatroon);
    }

    @Test
    public void testVerschillendePatronen() {
        patroonService.getScopePatroon(Sets.newSet(1));
        patroonService.getScopePatroon(Sets.newSet(2));
        patroonService.getScopePatroon(Sets.newSet(3));
        Mockito.verify(protocolleringRepository, Mockito.times(3)).opslaanNieuwScopePatroon(Mockito.any());
    }

    @Test
    public void testInitieelCacheGevuld() {

        final ScopePatroon patroon1 = maakScopePatroon(1, 1, 2, 3);
        final ScopePatroon patroon2 = maakScopePatroon(2, 4, 5, 6);
        Mockito.when(protocolleringRepository.getScopePatronen()).thenReturn(Lists.newArrayList(
                patroon1, patroon2
        ));
        patroonService.naConstructie();
        Mockito.verify(protocolleringRepository).getScopePatronen();
        Assert.isTrue(patroonService.getScopePatroon(Sets.newSet(1, 2, 3)) == patroon1);
        Assert.isTrue(patroonService.getScopePatroon(Sets.newSet(4, 5, 6)) == patroon2);

    }


    /**
     * Patronen die dezelfde hash hebben maar geen gelijke inhoud hebben moeten niet als gelijk behandeld worden en de test zal dus tot een nieuw patroon
     * leiden
     */
    @Test
    public void testAnderPatroonZelfdeHash() {

        final ScopePatroon patroon1 = maakScopePatroon(1, 2, 5, 7);
        final ScopePatroon patroon2 = maakScopePatroon(2, 2, 6, 8);

        Mockito.when(protocolleringRepository.getScopePatronen()).thenReturn(Lists.newArrayList(
                patroon1, patroon2
        ));
        patroonService.naConstructie();
        Mockito.verify(protocolleringRepository).getScopePatronen();

        Assert.isTrue(patroonService.getScopePatroon(Sets.newSet(2, 5, 9)) != patroon2);
    }

    /**
     * Er kunnen meerdere keren dezelfde patronen in de database staan. Dit is OK, Dit kan theoretisch enkel wanneer protocollering horizontaal geschaald
     * wordt. Er vindt namelijk geen synchronisatie plaats tussen deze nodes om uniciteit te garanderen.
     */
    @Test
    public void testInitieelCacheGevuldMetGelijkePatronen() {

        final ScopePatroon patroon1 = maakScopePatroon(1, 1, 2, 3);
        final ScopePatroon patroon2 = maakScopePatroon(2, 1, 2, 3);
        Mockito.when(protocolleringRepository.getScopePatronen()).thenReturn(Lists.newArrayList(
                patroon1, patroon2
        ));
        patroonService.naConstructie();
        Mockito.verify(protocolleringRepository).getScopePatronen();

        // gelijke patronen worden nu overschreven. Omdat patroon2 later in de lijst
        // voorkomt overschrijft dit patroon1
        Assert.isTrue(patroonService.getScopePatroon(Sets.newSet(1, 2, 3)) == patroon2);
    }

    private ScopePatroon maakScopePatroon(int patroonId, int... elementIds) {
        final ScopePatroon patroon = new ScopePatroon();
        patroon.setId(patroonId);
        for (int elementId : elementIds) {
            final ScopePatroonElement scopePatroonElement = new ScopePatroonElement();
            scopePatroonElement.setElementId(elementId);
            patroon.addScopePatroonElement(scopePatroonElement);
        }
        return patroon;
    }


}
