/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.cache;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.PartijRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PartijCacheTest {

    @Mock
    private PartijRepository partijRepository;

    @InjectMocks
    private final PartijCacheImpl partijCache = new PartijCacheImpl();

    private Integer partijIdEenCode = 1;

    @Before
    public final void voorTest() {
        final List<Partij> partijen = new ArrayList<>();
        Partij partijEen = TestPartijBuilder.maker().metCode(partijIdEenCode).maak();
        partijen.add(partijEen);
        Mockito.when(partijRepository.geefAllePartijen()).thenReturn(partijen);
    }

    @Test
    public void testGeefPartijMetCode() {
        partijCache.naMaak();
        final Partij partij = partijCache.geefPartij(partijIdEenCode);
        Assert.assertNotNull(partij);
        Assert.assertEquals(partijIdEenCode, partij.getCode().getWaarde());
    }
}
