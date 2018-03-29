/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.service.cache.PartijCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link PartijServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PartijServiceImplTest {

    @InjectMocks
    private PartijServiceImpl service;

    @Mock
    private PartijCache partijCache;
    @Mock
    private Partij partijMock;

    @Test
    public void vindPartijOpCode() throws Exception {
        when(partijCache.geefPartij("000001")).thenReturn(partijMock);

        final Partij partij = service.vindPartijOpCode("000001");

        assertThat(partij, is(partijMock));
    }

    @Test
    public void vindPartijOpCodeGeenResultaat() throws Exception {
        final Partij partij = service.vindPartijOpCode("000001");

        assertNull(partij);
    }

    @Test
    public void vindPartijOpId() throws Exception {
        when(partijCache.geefPartijMetId((short) 1)).thenReturn(partijMock);

        final Partij partij = service.vindPartijOpId((short) 1);

        assertThat(partij, is(partijMock));
    }

    @Test
    public void vindPartijOpIdGeenResultaat() throws Exception {
        final Partij partij = service.vindPartijOpId((short) 1);

        assertNull(partij);
    }

    @Test
    public void vindPartijOpOin() throws Exception {
        when(partijCache.geefPartijMetOin("123")).thenReturn(partijMock);

        final Partij partij = service.vindPartijOpOin("123");

        assertThat(partij, is(partijMock));
    }

    @Test
    public void vindPartijIdOpCode() {
        when(partijCache.geefPartij("000001")).thenReturn(partijMock);
        when(partijMock.getId()).thenReturn((short) 123);

        final Short id = service.vindPartijIdOpCode("000001");

        assertThat((short) 123, is(id));
    }

    @Test
    public void vindPartijIdOpCodeGeenResultaat() {
        when(partijCache.geefPartij("000001")).thenReturn(null);

        final Short id = service.vindPartijIdOpCode("000001");

        assertThat(null, is(id));
    }

    @Test
    public void geefBrpPartij() throws Exception {
        when(partijCache.geefPartij(Partij.PARTIJ_CODE_BRP)).thenReturn(partijMock);

        final Partij partij = service.geefBrpPartij();

        verify(partijCache).geefPartij(Partij.PARTIJ_CODE_BRP);
        assertThat(partij, is(partijMock));
    }

}
