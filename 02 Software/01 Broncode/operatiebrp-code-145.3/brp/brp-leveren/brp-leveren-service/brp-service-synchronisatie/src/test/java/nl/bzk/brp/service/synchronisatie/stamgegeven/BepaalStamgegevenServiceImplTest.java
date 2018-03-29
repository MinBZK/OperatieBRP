/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BepaalStamgegevenServiceImplTest {

    @InjectMocks
    private BepaalStamgegevenServiceImpl service;
    @Mock
    private PartijService partijService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Mock
    private StamTabelService stamTabelService;


    @Test
    public void testHappyflow() throws StapException, AutorisatieException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        final int zendendePartijCode = 123;
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);

        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metCode("000000").build();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(zendendePartij);

        final Autorisatiebundel autorisatiebundel = maakLeveringsInformatie();
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);

        final List<Map<String, Object>> stamgegevens = Lists.newArrayList();
        final StamtabelGegevens stamtabelGegevens = new StamtabelGegevens(null, stamgegevens);

        Mockito.when(stamTabelService.geefStamgegevens(verzoek.getParameters().getStamgegeven())).thenReturn(stamtabelGegevens);
        final BepaalStamgegevenResultaat resultaat = service.maakResultaat(verzoek);

        Assert.assertEquals(zendendePartij, resultaat.getBrpPartij());
        Assert.assertEquals(autorisatiebundel, resultaat.getAutorisatiebundel());
        Assert.assertEquals(stamtabelGegevens, resultaat.getStamgegevens());
        Mockito.verify(partijService).geefBrpPartij();
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verify(stamTabelService).geefStamgegevens(verzoek.getParameters().getStamgegeven());
    }

    @Test
    public void testFoutBijControleerAutorisatie() throws StapException, AutorisatieException {

        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        final int zendendePartijCode = 123;
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);

        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metCode("000000").build();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(zendendePartij);
        //autorisatie controle gaat fout
        Mockito.doThrow(new AutorisatieException(new Melding(Regel.R2130))).when(leveringsautorisatieService).controleerAutorisatie(Mockito.any());

        final BepaalStamgegevenResultaat resultaat = service.maakResultaat(verzoek);

        Assert.assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        Assert.assertEquals(1, meldingList.size());
        Assert.assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        Assert.assertEquals(Regel.R2343, meldingList.get(0).getRegel());
        Assert.assertNull(resultaat.getAutorisatiebundel());
        Assert.assertNull(resultaat.getStamgegevens());
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
    }


    @Test
    public void testStamTabelNietGevonden() throws StapException, AutorisatieException {

        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        final int zendendePartijCode = 123;
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);

        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metCode("000000").build();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(zendendePartij);

        final Autorisatiebundel autorisatiebundel = maakLeveringsInformatie();
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);

        final BepaalStamgegevenResultaat resultaat = service.maakResultaat(verzoek);

        Assert.assertNotNull(resultaat);
        final List<Melding> meldingList = resultaat.getMeldingList();
        Assert.assertEquals(1, meldingList.size());
        Assert.assertEquals(SoortMelding.FOUT, meldingList.get(0).getSoort());
        Assert.assertEquals(Regel.R1331, meldingList.get(0).getRegel());
        Assert.assertEquals(zendendePartij, resultaat.getBrpPartij());
        Assert.assertEquals(autorisatiebundel, resultaat.getAutorisatiebundel());
        Assert.assertNull(resultaat.getStamgegevens());
        Mockito.verify(partijService).geefBrpPartij();
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
    }

    @SuppressWarnings({"unchecked"})
    @Test(expected = RuntimeException.class)
    public void testException() throws StapException {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        final int zendendePartijCode = 123;
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
        Mockito.when(partijService.geefBrpPartij()).thenThrow(RuntimeException.class);
        service.maakResultaat(verzoek);
    }

    private Autorisatiebundel maakLeveringsInformatie() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.SYNCHRONISATIE_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
