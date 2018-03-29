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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PartijRegisterServiceTest {

    @Mock
    private BrpDalService brpDalService;

    private PartijRegisterService subject;

    @Before
    public void setup() {
        subject = new PartijRegisterService(brpDalService);
    }

    @Test
    public void testOk() {
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(maakPartij("Partij een", "123401", (short) 1, true, 19500101, null, null, true));
        partijen.add(maakPartij("Partij twee", "123501", (short) 2, true, 19500101, 19700101, null, true));
        partijen.add(maakPartij("Partij drie", "123601", (short) 3, false, 19500101, null, null, true));
        partijen.add(maakPartij("Partij vier", "123701", (short) 4, true, 19500101, null, 20180101, true));
        partijen.add(maakPartij("Partij vijf", "123801", (short) 5, true, 19600101, 21000101, null, false));

        Mockito.when(brpDalService.geefAllePartijen()).thenReturn(partijen);

        final LeesPartijRegisterVerzoekBericht verzoek = new LeesPartijRegisterVerzoekBericht();
        verzoek.setMessageId(UUID.randomUUID().toString());
        final LeesPartijRegisterAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());

        final PartijRegister partijRegister = antwoord.getPartijRegister();

        // Onbekend gemeente
        Assert.assertNull(partijRegister.zoekPartijOpGemeenteCode("1238"));

        // GBA gemeente
        Assert.assertNotNull(partijRegister.zoekPartijOpGemeenteCode("1234"));
        Assert.assertEquals("1234", partijRegister.zoekPartijOpGemeenteCode("1234").getGemeenteCode());
        Assert.assertEquals("123401", partijRegister.zoekPartijOpGemeenteCode("1234").getPartijCode());
        Assert.assertEquals(Stelsel.GBA, partijRegister.zoekPartijOpGemeenteCode("1234").getStelsel());

        Assert.assertNotNull(partijRegister.zoekPartijOpPartijCode("123401"));
        Assert.assertEquals("1234", partijRegister.zoekPartijOpPartijCode("123401").getGemeenteCode());
        Assert.assertEquals("123401", partijRegister.zoekPartijOpPartijCode("123401").getPartijCode());
        Assert.assertEquals(Stelsel.GBA, partijRegister.zoekPartijOpPartijCode("123401").getStelsel());

        Assert.assertEquals(3, partijRegister.geefAllePartijen().size());
    }

    @Test
    public void testException() {
        Mockito.when(brpDalService.geefAllePartijen()).thenThrow(new RuntimeException("Messsage"));

        final LeesPartijRegisterVerzoekBericht verzoek = new LeesPartijRegisterVerzoekBericht();
        try {
            subject.verwerkBericht(verzoek);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
    }

    @Test
    public void testVerzoekType() {
        Assert.assertEquals(LeesPartijRegisterVerzoekBericht.class, subject.getVerzoekType());
    }

    @Test
    public void testLeesServiceNaam() {
        Assert.assertEquals("PartijRegisterService", subject.getServiceNaam());
    }

    private Partij maakPartij(String naam, String code, short id, boolean actueelEnGeldig, Integer datumIngang, Integer datumEinde, Integer overgangBrp,
                              boolean metGemeente) {
        final Partij partij = new Partij(naam, code);
        partij.setId(id);
        partij.setActueelEnGeldig(actueelEnGeldig);
        partij.setDatumIngang(datumIngang);
        partij.setDatumEinde(datumEinde);
        partij.setDatumOvergangNaarBrp(overgangBrp);
        PartijRol rolAfnemer = new PartijRol(partij, Rol.AFNEMER);
        rolAfnemer.setActueelEnGeldig(true);
        rolAfnemer.setDatumIngang(datumIngang);
        partij.getPartijRolSet().add(rolAfnemer);
        if (metGemeente) {
            partij.getGemeenten().add(new Gemeente(id, naam, code.substring(0, 4), partij));
        }
        return partij;
    }
}
