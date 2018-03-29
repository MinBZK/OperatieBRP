/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import javax.persistence.OptimisticLockException;
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
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatie.PlaatsAfnemerIndicatieService;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatie.VerwijderAfnemerIndicatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatieVerzoekServiceImplTest {

    private static final int LEVERINGS_AUTORISATIE_ID = 123;
    private static final String PARTIJ_CODE = "000789";
    private static final Partij PARTIJ = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();

    @InjectMocks
    private AfnemerindicatieVerzoekServiceImpl service;

    @Mock
    private SelecteerPersoonService selecteerPersoonService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Mock
    private PlaatsAfnemerIndicatieService plaatsService;
    @Mock
    private VerwijderAfnemerIndicatieService verwijderService;
    @Mock
    private PartijService partijService;

    @Before
    public void voorTest() {
        when(partijService.vindPartijOpCode(anyString())).thenReturn(PARTIJ);
    }

    @Test
    public void testPlaatsenHappyflow() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final Autorisatiebundel autorisatiebundel = maakLeveringsInformatie(afnemerindicatieVerzoek);

        when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);

        final OnderhoudResultaat resultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        final ArgumentCaptor<AutorisatieParams> argumentCaptor = ArgumentCaptor.forClass(AutorisatieParams.class);
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(argumentCaptor.capture());

        final AutorisatieParams value = argumentCaptor.getValue();
        Assert.assertEquals(PARTIJ_CODE, value.getZendendePartijCode());
        Assert.assertEquals(123, value.getLeveringautorisatieId());
        Assert.assertNull(value.getOin().getOinWaardeOndertekenaar());
        Assert.assertNull(value.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(afnemerindicatieVerzoek.getSoortDienst(), value.getSoortDienst());

        Mockito.verify(plaatsService).plaatsAfnemerindicatie(any());
        Mockito.verify(verwijderService, Mockito.never()).verwijderAfnemerindicatie(any());
        Assert.assertNotNull(resultaat.getAutorisatiebundel());
    }

    @Test
    public void testVerwijderenHappyflow() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final Autorisatiebundel autorisatiebundel = maakLeveringsInformatie(afnemerindicatieVerzoek);

        when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);
        final OnderhoudResultaat resultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        final ArgumentCaptor<AutorisatieParams> argumentCaptor = ArgumentCaptor.forClass(AutorisatieParams.class);
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(argumentCaptor.capture());

        final AutorisatieParams value = argumentCaptor.getValue();
        Assert.assertEquals(PARTIJ_CODE, value.getZendendePartijCode());
        Assert.assertEquals(123, value.getLeveringautorisatieId());
        Assert.assertNull(value.getOin().getOinWaardeOndertekenaar());
        Assert.assertNull(value.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(afnemerindicatieVerzoek.getSoortDienst(), value.getSoortDienst());

        Mockito.verify(verwijderService).verwijderAfnemerindicatie(any());
        Mockito.verify(plaatsService, Mockito.never()).plaatsAfnemerindicatie(any());
        Assert.assertNotNull(resultaat.getAutorisatiebundel());
    }

    @Test(expected = IllegalStateException.class)
    public void testOnbekendeDienst() throws AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.ZOEK_PERSOON);
        final Autorisatiebundel autorisatiebundel = maakLeveringsInformatie(afnemerindicatieVerzoek);

        when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);
        final OnderhoudResultaat resultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);
        service.verwerkVerzoek(afnemerindicatieVerzoek);

        Mockito.verifyZeroInteractions(verwijderService);
        Mockito.verifyZeroInteractions(plaatsService);
        Assert.assertEquals(1, resultaat.getMeldingList().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingList().get(0).getSoort());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingList().get(0).getRegel());
    }

    @Test
    public void testOnderhoudenVoorAnderePartij_zendendePartijOngelijkAfnIndpartij() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        int zendendePartijCode = 456;
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));

        final OnderhoudResultaat verzoekResultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(any(AutorisatieParams.class));
        Assert.assertFalse(verzoekResultaat.getMeldingList().isEmpty());
        Assert.assertEquals(Regel.R2061, verzoekResultaat.getMeldingList().get(0).getRegel());
    }

    @Test
    public void testPlaatsenAutorisatiefout() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);

        when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenThrow(new AutorisatieException(new Melding(Regel.R1244)));

        final OnderhoudResultaat resultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        final ArgumentCaptor<AutorisatieParams> argumentCaptor = ArgumentCaptor.forClass(AutorisatieParams.class);
        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(argumentCaptor.capture());
        Assert.assertEquals(1, resultaat.getMeldingList().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingList().get(0).getSoort());
        Assert.assertEquals(Regel.R2343, resultaat.getMeldingList().get(0).getRegel());
    }

    @Test
    public void testOnderhoudenVoorAnderePartij_zendendePartijOngelijkAfnIndpartij_zendendePartijOngelijkDummyAfnCode()
            throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final String zendendePartijCode = "789";
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        afnemerindicatieVerzoek.setDummyAfnemerCode("456");

        final OnderhoudResultaat verzoekResultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(any(AutorisatieParams.class));
        Assert.assertFalse(verzoekResultaat.getMeldingList().isEmpty());
        Assert.assertEquals(Regel.R2061, verzoekResultaat.getMeldingList().get(0).getRegel());
    }


    @Test
    public void testOnderhoudenVoorAnderePartij_zendendePartijGelijkAanAfnIndPartij_OngelijkAanDummyAfnCode() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final String zendendePartijCode = "000789";
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        afnemerindicatieVerzoek.setDummyAfnemerCode("456");

        final OnderhoudResultaat verzoekResultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(any(AutorisatieParams.class));
        Assert.assertFalse(verzoekResultaat.getMeldingList().isEmpty());
        Assert.assertEquals(Regel.R2061, verzoekResultaat.getMeldingList().get(0).getRegel());
    }

    @Test
    public void testOnderhoudenVoorAnderePartij_zendendePartijGelijkAanAfnIndPartij_dummyAfnCodeNull() throws StapException, AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final String zendendePartijCode = "000789";
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        afnemerindicatieVerzoek.setDummyAfnemerCode(null);

        final OnderhoudResultaat verzoekResultaat = service.verwerkVerzoek(afnemerindicatieVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(any(AutorisatieParams.class));
        Assert.assertTrue(verzoekResultaat.getMeldingList().isEmpty());
    }


    @Test(expected = OptimisticLockException.class)
    public void testOptimisticLockException_geenBrpKoppelVlakVerzoek() throws AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        afnemerindicatieVerzoek.setBrpKoppelvlakVerzoek(false);
        final String zendendePartijCode = "000789";
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        Mockito.doThrow(OptimisticLockException.class).when(leveringsautorisatieService).controleerAutorisatie(Mockito.any());

        service.verwerkVerzoek(afnemerindicatieVerzoek);
    }


    @Test(expected = OptimisticLockException.class)
    public void testOptimisticLockException_isBrpKoppelVlakVerzoek() throws AutorisatieException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = maakAfnemerIndicatieVerzoek(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        afnemerindicatieVerzoek.setBrpKoppelvlakVerzoek(true);
        final String zendendePartijCode = "000789";
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(zendendePartijCode));
        Mockito.doThrow(OptimisticLockException.class).when(leveringsautorisatieService).controleerAutorisatie(Mockito.any());

        service.verwerkVerzoek(afnemerindicatieVerzoek);

    }

    private AfnemerindicatieVerzoek maakAfnemerIndicatieVerzoek(final SoortDienst soortDienst) {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        afnemerindicatieVerzoek.setSoortDienst(soortDienst);
        afnemerindicatieVerzoek.getParameters().setLeveringsAutorisatieId(String.valueOf(LEVERINGS_AUTORISATIE_ID));

        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setPartijCode(String.valueOf(PARTIJ_CODE));
        afnemerindicatie.setBsn("123411111");
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);

        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(PARTIJ_CODE));
        return afnemerindicatieVerzoek;
    }

    private Autorisatiebundel maakLeveringsInformatie(final AfnemerindicatieVerzoek afnemerindicatieVerzoek) {
        final PartijRol partijRol = new PartijRol(PARTIJ, Rol.AFNEMER);
        final SoortDienst soortDienst = afnemerindicatieVerzoek.getSoortDienst();
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
