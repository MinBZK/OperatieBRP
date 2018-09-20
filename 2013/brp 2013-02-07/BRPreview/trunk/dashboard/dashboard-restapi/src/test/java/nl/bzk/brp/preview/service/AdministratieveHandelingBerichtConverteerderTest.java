/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.model.AdministratieveHandelingBericht;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.junit.Test;

public class AdministratieveHandelingBerichtConverteerderTest {

    private static final String        PARTIJ               = "testpartij";
    private static final String        TEST_BERICHT         = "test bericht";
    private static final String        TEST_BERICHT_DETAILS = "test bericht details";
    private static final Calendar      TEST_DATUM_CAL       = Calendar.getInstance();
    private static final List<Integer> TEST_BSN_LIJST       = Arrays.asList(new Integer[]{123, 456, 789});

    @Test
    public void testConverteerNaarBericht() {
        AdministratieveHandelingBericht administratieveHandelingBericht = mock(AdministratieveHandelingBericht.class);
        when(administratieveHandelingBericht.getPartij()).thenReturn(PARTIJ);
        when(administratieveHandelingBericht.creeerBerichtTekst()).thenReturn(TEST_BERICHT);
        when(administratieveHandelingBericht.creeerDetailsTekst()).thenReturn(TEST_BERICHT_DETAILS);
        when(administratieveHandelingBericht.getTijdRegistratie()).thenReturn(TEST_DATUM_CAL.getTime());
        when(administratieveHandelingBericht.getSoortBijhouding()).thenReturn(OndersteundeBijhoudingsTypes.ONBEKEND);
        when(administratieveHandelingBericht.creeerBsnLijst()).thenReturn(TEST_BSN_LIJST);

        Bericht bericht =
                AdministratieveHandelingBerichtConverteerder.converteerNaarBericht(administratieveHandelingBericht);

        assertEquals(PARTIJ, bericht.getPartij());
        assertEquals(TEST_BERICHT, bericht.getBericht());
        assertEquals(TEST_BERICHT_DETAILS, bericht.getBerichtDetails());
        assertEquals(TEST_DATUM_CAL, bericht.getVerzondenOp());
        assertEquals(OndersteundeBijhoudingsTypes.ONBEKEND, bericht.getSoortBijhouding());
        assertArrayEquals(TEST_BSN_LIJST.toArray(), bericht.getBurgerservicenummers().toArray());
    }

}
