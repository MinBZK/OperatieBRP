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
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Autorisatie;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienstbundel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.SoortDienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AutorisatieRegisterServiceTest {

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final AutorisatieRegisterService subject = new AutorisatieRegisterService();

    @Test
    public void testOk() {
        final List<Leveringsautorisatie> leveringsautorisaties = new ArrayList<>();
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisaties.add(leveringsautorisatie);

        final Partij partij = new Partij("Partij", 599010);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie toegang = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegang.setId(65846987);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(toegang);

        final Dienstbundel dienstbundelPlaatsen = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.getDienstbundelSet().add(dienstbundelPlaatsen);
        final Dienst dienstPlaatsen = new Dienst(dienstbundelPlaatsen, SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        dienstPlaatsen.setId(78987978);
        dienstbundelPlaatsen.getDienstSet().add(dienstPlaatsen);
        dienstbundelPlaatsen.getDienstSet().add(new Dienst(dienstbundelPlaatsen, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE));

        final Dienstbundel dienstbundelSelectie = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.getDienstbundelSet().add(dienstbundelSelectie);
        dienstbundelSelectie.getDienstSet().add(new Dienst(dienstbundelSelectie, SoortDienst.SELECTIE));

        final Dienstbundel dienstbundelBevragen = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.getDienstbundelSet().add(dienstbundelBevragen);
        final Dienst dienstZoekPersoon = new Dienst(dienstbundelBevragen, SoortDienst.ZOEK_PERSOON);
        dienstZoekPersoon.setId(12312);
        dienstbundelBevragen.getDienstSet().add(dienstZoekPersoon);

        Mockito.when(brpDalService.geefAlleGbaAutorisaties()).thenReturn(leveringsautorisaties);

        final LeesAutorisatieRegisterVerzoekBericht verzoek = new LeesAutorisatieRegisterVerzoekBericht();
        verzoek.setMessageId(UUID.randomUUID().toString());
        final LeesAutorisatieRegisterAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());

        final AutorisatieRegister autorisatieRegister = antwoord.getAutorisatieRegister();

        // Onbekend afnemer
        Assert.assertNull(autorisatieRegister.zoekAutorisatieOpPartijCode("499010"));

        Assert.assertEquals(1, autorisatieRegister.geefAlleAutorisaties().size());
        final Autorisatie autorisatie = autorisatieRegister.zoekAutorisatieOpPartijCode("599010");
        Assert.assertNotNull(autorisatie);
        Assert.assertEquals("599010", autorisatie.getPartijCode());
        Assert.assertEquals(65846987, autorisatie.getToegangLeveringsautorisatieId());
        Assert.assertEquals(Integer.valueOf(78987978), autorisatie.getPlaatsenAfnemersindicatieDienstId());
        Assert.assertEquals(Integer.valueOf(12312), autorisatie.getBevragenPersoonDienstId());
        Assert.assertNull(autorisatie.getBevragenAdresDienstId());
    }

    @Test
    public void testException() {
        Mockito.when(brpDalService.geefAlleGbaAutorisaties()).thenThrow(new RuntimeException("Messsage"));

        final LeesAutorisatieRegisterVerzoekBericht verzoek = new LeesAutorisatieRegisterVerzoekBericht();
        try {
            subject.verwerkBericht(verzoek);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
    }
}
