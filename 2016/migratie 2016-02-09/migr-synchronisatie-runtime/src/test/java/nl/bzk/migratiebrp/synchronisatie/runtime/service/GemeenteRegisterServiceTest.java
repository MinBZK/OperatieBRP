/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GemeenteRegisterServiceTest {

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final GemeenteRegisterService subject = new GemeenteRegisterService();

    @Test
    public void testOk() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente((short) 1, "Gemeente", (short) 599, new Partij("Partij", 599010)));

        Mockito.when(brpDalService.geefAlleGemeenten()).thenReturn(gemeenten);

        final LeesGemeenteRegisterVerzoekBericht verzoek = new LeesGemeenteRegisterVerzoekBericht();
        verzoek.setMessageId(UUID.randomUUID().toString());
        final LeesGemeenteRegisterAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());

        final GemeenteRegister gemeenteRegister = antwoord.getGemeenteRegister();

        // Onbekend gemeente
        Assert.assertNull(gemeenteRegister.zoekGemeenteOpGemeenteCode("0499"));

        // GBA gemeente
        Assert.assertNotNull(gemeenteRegister.zoekGemeenteOpGemeenteCode("0599"));
        Assert.assertEquals("0599", gemeenteRegister.zoekGemeenteOpGemeenteCode("0599").getGemeenteCode());
        Assert.assertEquals("599010", gemeenteRegister.zoekGemeenteOpGemeenteCode("0599").getPartijCode());
        Assert.assertEquals(Stelsel.GBA, gemeenteRegister.zoekGemeenteOpGemeenteCode("0599").getStelsel());
        // RNI
        Assert.assertNotNull(gemeenteRegister.zoekGemeenteOpPartijCode("199901"));
    }

    @Test
    public void testException() {
        Mockito.when(brpDalService.geefAlleGemeenten()).thenThrow(new RuntimeException("Messsage"));

        final LeesGemeenteRegisterVerzoekBericht verzoek = new LeesGemeenteRegisterVerzoekBericht();
        try {
            subject.verwerkBericht(verzoek);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
    }
}
