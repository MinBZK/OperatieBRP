/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class Sv01BerichtFactoryTest {

    private static final String SV_01_BERICHT_ALS_STRING = "00000000Sv01O2013111400077010720110010843242473701200093066860410210005Cindy0240005Loper"
            + "031000819660821";
    private Bericht sv01Bericht = Mockito.mock(Bericht.class);
    private Dienst mockedDienst = Mockito.mock(Dienst.class);

    private BerichtFactory berichtFactory = Mockito.mock(BerichtFactory.class);
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository = Mockito.mock(Lo3FilterRubriekRepository.class);
    private Persoonslijst persoonslijst = null;

    @InjectMocks
    private final Sv01BerichtFactory subject = new Sv01BerichtFactory(berichtFactory, lo3FilterRubriekRepository);


    @Before
    public void setup() {
        Dienstbundel mockedDienstbundel = Mockito.mock(Dienstbundel.class);
        Mockito.when(mockedDienst.getDienstbundel()).thenReturn(mockedDienstbundel);
        Mockito.when(mockedDienstbundel.getId()).thenReturn(1);
        Mockito.when(sv01Bericht.maakUitgaandBericht()).thenReturn(SV_01_BERICHT_ALS_STRING);
        Mockito.when(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(Matchers.anyInt())).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(berichtFactory.maakSv01Bericht(null)).thenReturn(sv01Bericht);
    }

    @Test
    public void testSv01Bericht() {

        String gemaaktBericht = subject.maakSv01Bericht(mockedDienst, persoonslijst);
        Assert.assertEquals("SV01 klopt inhoudelijk niet", SV_01_BERICHT_ALS_STRING, gemaaktBericht);
    }

}
