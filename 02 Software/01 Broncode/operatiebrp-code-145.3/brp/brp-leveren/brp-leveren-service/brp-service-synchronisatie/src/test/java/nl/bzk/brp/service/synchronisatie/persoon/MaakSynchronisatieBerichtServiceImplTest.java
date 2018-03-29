/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.VerzoekAsynchroonBerichtQueueService;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaakSynchronisatieBerichtServiceImplTest {

    @InjectMocks
    private MaakSynchronisatieBerichtServiceImpl service;

    @Mock
    private PartijService partijService;
    @Mock
    private SynchroniseerPersoonAutorisatieService synchroniseerPersoonAutorisatieService;
    @Mock
    private SelecteerPersoonService selecteerPersoonService;
    @Mock
    private VerzoekAsynchroonBerichtQueueService zetPersoonberichtOpQueue;
    @Mock
    private SynchronisatieBerichtFactory synchronisatieBerichtFactory;


    @Test
    public void testHappyflow() throws StapException, AutorisatieException {
        String bsn = "111222333";
        String zendendePartijCode = "000123";
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(bsn));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        final Partij partij = TestPartijBuilder.maakBuilder().metCode(zendendePartijCode).build();
        when(partijService.vindPartijOpCode(zendendePartijCode)).thenReturn(partij);

        final Persoonslijst t = TestBuilders.PERSOON_MET_HANDELINGEN;
        when(selecteerPersoonService.selecteerPersoonMetBsn(anyString(), any())).thenReturn(t);
        final VerwerkPersoonBericht persoonBericht = new VerwerkPersoonBericht(null, null,
                Collections.singletonList(new BijgehoudenPersoon.Builder(null, null).build()))
                ;
        when(synchronisatieBerichtFactory.apply(any(), any())).thenReturn(persoonBericht);

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        assertNotNull(resultaat);
        verify(partijService).vindPartijOpCode(zendendePartijCode);
        verify(synchroniseerPersoonAutorisatieService).controleerAutorisatie(Mockito.any());
        verify(selecteerPersoonService).selecteerPersoonMetBsn(anyString(), any());
        verify(synchronisatieBerichtFactory).apply(any(), any());
        verify(zetPersoonberichtOpQueue).plaatsQueueberichtVoorVerzoek(any(), any(), eq(null));
    }


    @Test(expected = NullPointerException.class)
    public void testOnverwachteFout() {
        //geen bsn in verzoek, eigenlijk niet mogelijk via xsd
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        service.verwerkVerzoek(verzoek);

        verify(partijService, Mockito.never()).vindPartijOpCode(anyString());
    }

    @Test
    public void testBsnValidatieFaalt() throws StapException, AutorisatieException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(1234));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf("123"));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        verifyZeroInteractions(partijService);
        Mockito.verifyZeroInteractions(synchroniseerPersoonAutorisatieService);
        Mockito.verifyZeroInteractions(selecteerPersoonService);
        Mockito.verifyZeroInteractions(synchronisatieBerichtFactory);
        Mockito.verifyZeroInteractions(zetPersoonberichtOpQueue);

        assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        assertEquals(1, meldingList.size());
        assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        assertEquals(Regel.R1587, meldingList.get(0).getRegel());
        assertNull(resultaat.getAutorisatiebundel());
    }

    private void verifyZeroInteractions(final PartijService partijService) {
    }

    @Test
    public void testAutorisatieControleFaaltOpRegel() throws StapException, AutorisatieException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(111222333));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf("123"));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        //autorisatiecontrole mislukt
        Mockito.doThrow(new AutorisatieException(new Melding(Regel
                .R2130))).when(synchroniseerPersoonAutorisatieService).controleerAutorisatie(Mockito.any());

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        Mockito.verifyZeroInteractions(partijService);
        verify(synchroniseerPersoonAutorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verifyZeroInteractions(selecteerPersoonService);
        Mockito.verifyZeroInteractions(synchronisatieBerichtFactory);
        Mockito.verifyZeroInteractions(zetPersoonberichtOpQueue);

        assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        assertEquals(1, meldingList.size());
        assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        assertEquals(Regel.R2343, meldingList.get(0).getRegel());
        assertNull(resultaat.getAutorisatiebundel());
        assertNull(resultaat.getZendendePartij());
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testControleerAutorisatieFaaltAutorisatieFout() throws StapException, AutorisatieException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(111222333));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf("123"));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);

        when(synchroniseerPersoonAutorisatieService.controleerAutorisatie(Mockito.any())).thenThrow(new AutorisatieException(new Melding(Regel.R2243)));

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        Mockito.verifyZeroInteractions(partijService);
        verify(synchroniseerPersoonAutorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verifyZeroInteractions(selecteerPersoonService);
        Mockito.verifyZeroInteractions(synchronisatieBerichtFactory);
        Mockito.verifyZeroInteractions(zetPersoonberichtOpQueue);

        assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        assertEquals(1, meldingList.size());
        assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        assertEquals(Regel.R2343, meldingList.get(0).getRegel());

    }

    @Test
    public void testBerichtOpQueueZettenFaalt() throws StapException, AutorisatieException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(111222333));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf("123"));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        Mockito.doThrow(StapException.class).when(zetPersoonberichtOpQueue).plaatsQueueberichtVoorVerzoek(Mockito.any(), Mockito.any(), Mockito.any());
        final VerwerkPersoonBericht persoonBericht = new VerwerkPersoonBericht(null, null,
                Collections.singletonList(new BijgehoudenPersoon.Builder(null, null).build()));
        when(synchronisatieBerichtFactory.apply(any(), any())).thenReturn(persoonBericht);

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        verify(partijService).vindPartijOpCode(anyString());
        verify(synchroniseerPersoonAutorisatieService).controleerAutorisatie(Mockito.any());
        verify(selecteerPersoonService).selecteerPersoonMetBsn(anyString(), any());
        verify(synchronisatieBerichtFactory).apply(any(), any());
        verify(zetPersoonberichtOpQueue).plaatsQueueberichtVoorVerzoek(any(), any(), eq(null));

        assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        assertEquals(1, meldingList.size());
        assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        assertEquals(Regel.ALG0001, meldingList.get(0).getRegel());
    }

    @Test
    public void bijLeegPersoonVerstuurtGeenBericht() throws Exception {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.SYNCHRONISATIE_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        String bsn = "111222333";
        String zendendePartijCode = "000123";
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getZoekCriteriaPersoon().setBsn(String.valueOf(bsn));
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf("123"));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode(zendendePartijCode).build();
        when(partijService.vindPartijOpCode(zendendePartijCode)).thenReturn(partij);
        when(synchroniseerPersoonAutorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);
        final Persoonslijst t = TestBuilders.PERSOON_MET_HANDELINGEN;
        when(selecteerPersoonService.selecteerPersoonMetBsn(anyString(), any())).thenReturn(t);
        final VerwerkPersoonBericht persoonBericht = VerwerkPersoonBericht.LEEG_BERICHT;
        when(synchronisatieBerichtFactory.apply(any(), any())).thenReturn(persoonBericht);

        final MaakSynchronisatieBerichtResultaat resultaat = service.verwerkVerzoek(verzoek);

        assertTrue(resultaat.getMeldingList().isEmpty());
        Mockito.verifyZeroInteractions(zetPersoonberichtOpQueue);
    }
}
