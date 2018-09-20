/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.levering.MutatieBericht;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MarshallServiceImplTest {

    @InjectMocks
    private MarshallServiceImpl marshallService = new MarshallServiceImpl();

    @Mock
    private MarshallingContextFactory marshallingContextFactory;

    @Mock
    private IMarshallingContext iMarshallingContext;

    @Mock
    private MutatieBericht kennisgevingBericht;

    @Before
    public final void setUp() throws JiBXException {
        Mockito.when(marshallingContextFactory.nieuweMarshallingContext(AbstractSynchronisatieBericht.class))
                .thenReturn(iMarshallingContext);
    }

    @Test
    public final void testMaakBericht() throws JiBXException {
        final String resultaat = marshallService.maakBericht(kennisgevingBericht);

        Assert.assertNotNull(resultaat);
        Assert.assertEquals("", resultaat);
    }
}
