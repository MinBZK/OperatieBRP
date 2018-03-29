/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.mockito.Mockito.never;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * MaakGeefDetailsPersoonBerichtServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeefDetailsPersoonMaakBerichtServiceImplTest {

    private static final SoortDienst SOORT_DIENST = SoortDienst.ATTENDERING;
    private static final int LEV_AUT_ID = 1;
    private static final int DIENST_ID = 1;
    private static final int ZENDENDE_PARTIJ_ID = 1;
    private static final String ZENDENDE_PARTIJ_CODE = "000001";
    private static final int PERSOON_ID = 1;
    private final List<Melding> gevuldeMeldingLijst = Lists.newArrayList(new Melding(Regel.R1245));
    private GeefDetailsPersoonVerzoek bevragingVerzoek;
    private Autorisatiebundel autorisatiebundel;
    private Persoonslijst persoonslijst;
    @Mock
    private GeefDetailsPersoon.OphalenPersoonService ophalenPersoonService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Mock
    private GeefDetailsPersoonBerichtFactory geefDetailsPersoonBerichtFactory;
    @Mock
    private ConverteerBevragingElementenService converteerBevragingElementenService;
    @Mock
    private PartijService partijService;
    @Mock
    private PeilmomentValidatieService peilmomentValidatieService;
    @InjectMocks
    private GeefDetailsPersoonMaakBerichtServiceImpl
            maakGeefDetailsPersoonBerichtService =
            new GeefDetailsPersoonMaakBerichtServiceImpl(leveringsautorisatieService, partijService);

    @Before
    public void voorTest() {
        bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getParameters().setDienstIdentificatie(Integer.toString(DIENST_ID));
        bevragingVerzoek.setSoortDienst(SOORT_DIENST);
        bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(LEV_AUT_ID));
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode(Integer.toString(ZENDENDE_PARTIJ_ID));
        bevragingVerzoek.getParameters().setRolNaam(Rol.AFNEMER.getNaam());

        persoonslijst =
                new Persoonslijst(TestBuilders.maakLeegPersoon(PERSOON_ID).build(), 0L);
        final Partij partij = TestPartijBuilder.maakBuilder().metId(ZENDENDE_PARTIJ_ID).metCode(ZENDENDE_PARTIJ_CODE).build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setDatumIngang(DatumUtil.gisteren());
        partijRol.setDatumEinde(DatumUtil.morgen());
        partij.getPartijRolSet().add(partijRol);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, SOORT_DIENST);
        leveringsautorisatie.setId(LEV_AUT_ID);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setId(LEV_AUT_ID);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, SOORT_DIENST);
        dienst.setId(DIENST_ID);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumIngang(DatumUtil.morgen());
        autorisatiebundel = new Autorisatiebundel(tla, dienst);
    }

    @Test
    public void testHappyFlow() throws StapException, AutorisatieException {
        Mockito.when(ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel)).thenReturn(persoonslijst);
        Mockito.when(geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoonslijst,
                autorisatiebundel, bevragingVerzoek, Collections.emptySet())).thenReturn(null);
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);

        maakGeefDetailsPersoonBerichtService.voerStappenUit(bevragingVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verify(ophalenPersoonService).voerStapUit(bevragingVerzoek, autorisatiebundel);
        Mockito.verify(geefDetailsPersoonBerichtFactory).maakGeefDetailsPersoonBericht(persoonslijst,
                autorisatiebundel, bevragingVerzoek, Collections.emptySet());
    }

    @Test()
    public void testFoutBijControleAutorisatie() throws StapException, AutorisatieException {
        Mockito.doThrow(new AutorisatieException(gevuldeMeldingLijst.get(0)))
                .when(leveringsautorisatieService).controleerAutorisatie(Mockito.any());

        final BevragingResultaat resultaat = maakGeefDetailsPersoonBerichtService.voerStappenUit(bevragingVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verify(ophalenPersoonService, never()).voerStapUit(bevragingVerzoek, autorisatiebundel);
        Mockito.verify(geefDetailsPersoonBerichtFactory, never()).maakGeefDetailsPersoonBericht(persoonslijst,
                autorisatiebundel, bevragingVerzoek, Collections.emptySet());

        Assert.assertEquals(resultaat.getMeldingen().size(), 1);
        Assert.assertEquals(Regel.R2343, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public void testFoutBijRegelValidatie() throws StapException, AutorisatieException {
        bevragingVerzoek.getParameters().setPeilMomentMaterieelResultaat("2014-01-01");
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any())).thenReturn(autorisatiebundel);
        Mockito.doThrow(new StapMeldingException(new Melding(Regel.R2222))).when(peilmomentValidatieService).valideerFormeelEnMaterieel(
                Mockito.anyString(), Mockito.anyString());

        final BevragingResultaat resultaat = maakGeefDetailsPersoonBerichtService.voerStappenUit(bevragingVerzoek);

        Mockito.verify(leveringsautorisatieService).controleerAutorisatie(Mockito.any());
        Mockito.verify(peilmomentValidatieService).valideerFormeelEnMaterieel(Mockito.any(), Mockito.any());
        Mockito.verify(ophalenPersoonService, never()).voerStapUit(bevragingVerzoek, autorisatiebundel);
        Mockito.verify(geefDetailsPersoonBerichtFactory, never()).maakGeefDetailsPersoonBericht(persoonslijst,
                autorisatiebundel, bevragingVerzoek, Collections.emptySet());
        Assert.assertEquals(resultaat.getMeldingen().size(), 1);
        Assert.assertEquals(resultaat.getMeldingen().get(0).getRegel(), Regel.R2222);
    }
}
