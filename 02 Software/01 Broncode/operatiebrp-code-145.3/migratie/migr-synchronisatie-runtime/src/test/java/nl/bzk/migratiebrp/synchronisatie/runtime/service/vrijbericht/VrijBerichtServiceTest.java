/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VrijBerichtVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor de vrijberichtservice.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtServiceTest {

    @Mock
    private VrijBerichtNaarBrpVerzender verzender;

    @InjectMocks
    private VrijBerichtService vrijBerichtService;

    @Test
    public void testGetVerzoekType() {
        Assert.assertEquals(VrijBerichtVerzoekBericht.class, vrijBerichtService.getVerzoekType());
    }

    @Test
    public void testGoedeZoekOpdracht() throws Exception {
        VrijBerichtVerzoekType verzoek = new VrijBerichtVerzoekType();
        verzoek.setReferentienummer("REF-123");
        verzoek.setBericht("BERICHT");
        verzoek.setOntvangendePartij("062601");
        verzoek.setVerzendendePartij("059901");
        Mockito.doNothing().when(verzender)
                .verstuurVrijBericht(Mockito.any(VrijBerichtOpdracht.class), Mockito.anyString(), Mockito.anyString());

        vrijBerichtService.verwerkBericht(new VrijBerichtVerzoekBericht(verzoek));

        Mockito.verify(verzender, Mockito.atLeastOnce()).verstuurVrijBericht(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testServiceNaam() {
        Assert.assertEquals("VrijBerichtService", vrijBerichtService.getServiceNaam());
    }
}
