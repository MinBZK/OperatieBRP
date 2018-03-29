/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

public class Sf01BerichtFactoryTest {

    private static final String SF_01_BERICHT_ALS_STRING = "00000000Sf01000220101701100103450924321"
            + "031000819660821";
    private Bericht sf01Bericht = Mockito.mock(Bericht.class);

    private BerichtFactory berichtFactory = Mockito.mock(BerichtFactory.class);
    private Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);

    @InjectMocks
    private final Sf01BerichtFactory subject = new Sf01BerichtFactory(berichtFactory);


    @Before
    public void setup() {
        Dienstbundel mockedDienstbundel = Mockito.mock(Dienstbundel.class);
        Mockito.when(sf01Bericht.maakUitgaandBericht()).thenReturn(SF_01_BERICHT_ALS_STRING);
        Mockito.when(berichtFactory.maakSf01Bericht(persoonslijst)).thenReturn(sf01Bericht);
    }

    @Test
    public void testSv01Bericht() {
        String gemaaktBericht = subject.maakSf01Bericht(persoonslijst, "0599");
        Assert.assertEquals("Sf01 klopt inhoudelijk niet", SF_01_BERICHT_ALS_STRING, gemaaktBericht);
    }

}
