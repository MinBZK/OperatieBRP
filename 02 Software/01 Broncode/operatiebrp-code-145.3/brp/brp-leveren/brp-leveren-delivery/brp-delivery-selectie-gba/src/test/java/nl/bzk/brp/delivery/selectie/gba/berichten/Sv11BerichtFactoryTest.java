/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

public class Sv11BerichtFactoryTest {

    private static final String SV_11_BERICHT_ALS_STRING = "00000000Sv11000000000";
    private Bericht sv11Bericht = Mockito.mock(Bericht.class);

    private BerichtFactory berichtFactory = Mockito.mock(BerichtFactory.class);

    @InjectMocks
    private final Sv11BerichtFactory subject = new Sv11BerichtFactory(berichtFactory);


    @Before
    public void setup() {
        Mockito.when(sv11Bericht.maakUitgaandBericht()).thenReturn(SV_11_BERICHT_ALS_STRING);
        Mockito.when(berichtFactory.maakSv11Bericht()).thenReturn(sv11Bericht);
    }

    @Test
    public void testSv11Bericht() {
        String gemaaktBericht = subject.maakSv11Bericht();
        Assert.assertEquals("SV11 klopt inhoudelijk niet", SV_11_BERICHT_ALS_STRING, gemaaktBericht);
    }
}
