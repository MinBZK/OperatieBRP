/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.business.service.BerichtVerwerker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link BijhoudingService} class.
 */
public class BijhoudingServiceTest {

    private BijhoudingService bijhoudingService;
    @Mock
    private BerichtVerwerker berichtVerwerker;


    @Test
    public void testBijhouden() {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        Mockito.when(berichtVerwerker.verwerkBericht((BRPBericht) Mockito.any())).thenReturn(resultaat);

        Assert.assertEquals(resultaat, bijhoudingService.bijhouden(null));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        bijhoudingService = new BijhoudingServiceImpl();
        ReflectionTestUtils.setField(bijhoudingService, "berichtVerwerker", berichtVerwerker);
    }

}
