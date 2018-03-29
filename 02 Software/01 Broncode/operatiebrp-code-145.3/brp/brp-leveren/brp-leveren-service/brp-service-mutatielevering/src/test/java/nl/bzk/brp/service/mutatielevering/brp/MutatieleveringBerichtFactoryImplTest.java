/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.mutatielevering.algemeen.SoortSynchronisatieBepaler;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
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
public class MutatieleveringBerichtFactoryImplTest {

    @InjectMocks
    private MutatieleveringBerichtFactoryImpl service;

    @Mock
    private PartijService partijService;
    @Mock
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Mock
    private SoortSynchronisatieBepaler soortSynchronisatieBepaler;

    private ZonedDateTime tijdstipRegistratie;

    @Before
    public void voorTest() {
        tijdstipRegistratie = DatumUtil.nuAlsZonedDateTime();
        BrpNu.set(tijdstipRegistratie);

        Mockito.when(soortSynchronisatieBepaler.bepaalSoortSynchronisatie(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(SoortSynchronisatie.MUTATIE_BERICHT);
    }

    @Test
    public void testGeenStelselBrp() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(Stelsel.GBA, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final MetaObject persoon = TestBuilders.LEEG_PERSOON;
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> teLeverenPersonenMap = new HashMap<>();
        teLeverenPersonenMap.put(persoonslijst, Populatie.BETREEDT);
        final Map<Long, Persoonslijst> bijgehoudenPersonenMap = new HashMap<>();
        bijgehoudenPersonenMap.put(persoon.getObjectsleutel(), persoonslijst);

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);

        final ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor = ArgumentCaptor.forClass(MaakBerichtParameters.class);

        service.apply(Lists.newArrayList(mutatielevering), new Mutatiehandeling(0L, bijgehoudenPersonenMap));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(maakBerichtParametersArgumentCaptor.capture());

        Assert.assertTrue(maakBerichtParametersArgumentCaptor.getValue().getAutorisatiebundels().isEmpty());
    }


    @Test
    public void testStelselBrp() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(Stelsel.BRP, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final MetaObject persoon = TestBuilders.LEEG_PERSOON;
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> teLeverenPersonenMap = new HashMap<>();
        teLeverenPersonenMap.put(persoonslijst, Populatie.BETREEDT);
        final Map<Long, Persoonslijst> bijgehoudenPersonenMap = new HashMap<>();
        bijgehoudenPersonenMap.put(persoon.getObjectsleutel(), persoonslijst);

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);

        final ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor = ArgumentCaptor.forClass(MaakBerichtParameters.class);

        service.apply(Lists.newArrayList(mutatielevering), new Mutatiehandeling(0L, bijgehoudenPersonenMap));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(maakBerichtParametersArgumentCaptor.capture());

        Assert.assertEquals(1, maakBerichtParametersArgumentCaptor.getValue().getAutorisatiebundels().size());
    }

    @Test
    public void testDatumAanvangMaterielePeriodeDienstMutlevOpBasisVanAfnemerindicatie() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(Stelsel.BRP, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        final Integer datumAanvangMaterielePeriode = 20101010;
        final Integer persoonId = 1;
        final MetaObject persoon = maakPersoonMetAfnemerindicatie(autorisatiebundel, datumAanvangMaterielePeriode, persoonId);

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> teLeverenPersonenMap = new HashMap<>();
        teLeverenPersonenMap.put(persoonslijst, Populatie.BETREEDT);
        final Map<Long, Persoonslijst> bijgehoudenPersonenMap = new HashMap<>();
        bijgehoudenPersonenMap.put(persoon.getObjectsleutel(), persoonslijst);

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);

        final ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor = ArgumentCaptor.forClass(MaakBerichtParameters.class);

        service.apply(Lists.newArrayList(mutatielevering), new Mutatiehandeling(0L, bijgehoudenPersonenMap));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(maakBerichtParametersArgumentCaptor.capture());

        final MaakBerichtParameters maakBerichtParameters = maakBerichtParametersArgumentCaptor.getValue();
        Assert.assertEquals(1, maakBerichtParameters.getAutorisatiebundels().size());

        Assert.assertEquals(datumAanvangMaterielePeriode,
                maakBerichtParameters.getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst).getDatumAanvangmaterielePeriode());
    }

    @Test
    public void testDatumAanvangMaterielePeriodeGeenDienstMutlevOpBasisVanAfnemerindicatie() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(Stelsel.BRP, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final Integer datumAanvangMaterielePeriode = 20101010;
        final Integer persoonId = 1;
        final MetaObject persoon = maakPersoonMetAfnemerindicatie(autorisatiebundel, datumAanvangMaterielePeriode, persoonId);

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> teLeverenPersonenMap = new HashMap<>();
        teLeverenPersonenMap.put(persoonslijst, Populatie.BETREEDT);
        final Map<Long, Persoonslijst> bijgehoudenPersonenMap = new HashMap<>();
        bijgehoudenPersonenMap.put(persoon.getObjectsleutel(), persoonslijst);

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);

        final ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor = ArgumentCaptor.forClass(MaakBerichtParameters.class);

        service.apply(Lists.newArrayList(mutatielevering), new Mutatiehandeling(0L, bijgehoudenPersonenMap));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(maakBerichtParametersArgumentCaptor.capture());

        final MaakBerichtParameters maakBerichtParameters = maakBerichtParametersArgumentCaptor.getValue();
        Assert.assertEquals(1, maakBerichtParameters.getAutorisatiebundels().size());

        Assert.assertNull(maakBerichtParameters.getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst).getDatumAanvangmaterielePeriode());
    }

    private MetaObject maakPersoonMetAfnemerindicatie(Autorisatiebundel autorisatiebundel, Integer datumAanvangMaterielePeriode, Integer persoonId) {
        //@formatter:off
        return TestBuilders.maakLeegPersoon(persoonId)
        .metObject()
            .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId()))
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId()))
                .metRecord()
                .metId(8)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId()), autorisatiebundel.getLeveringsautorisatie().getId())
                    .metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId()), autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getCode())
                    .metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId()), persoonId)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId()))
                    .metRecord()
                    .metId(8)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), tijdstipRegistratie)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId()), datumAanvangMaterielePeriode)
                    .eindeRecord()
            .eindeGroep()
        .eindeObject().build();
        //@formatter:on
    }

    @Test
    public void testMaakBerichtParameters() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(Stelsel.BRP, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final MetaObject persoon1 = TestBuilders.maakLeegPersoon(1).build();
        final MetaObject persoon2 = TestBuilders.maakLeegPersoon(2).build();
        final Persoonslijst persoonslijst1 = new Persoonslijst(persoon1, 0L);
        final Persoonslijst persoonslijst2 = new Persoonslijst(persoon2, 0L);

        final Map<Persoonslijst, Populatie> teLeverenPersonenMap = new HashMap<>();
        teLeverenPersonenMap.put(persoonslijst1, Populatie.BETREEDT);
        final Map<Long, Persoonslijst> bijgehoudenPersonenMap = new HashMap<>();
        bijgehoudenPersonenMap.put(persoonslijst1.getId(), persoonslijst1);
        bijgehoudenPersonenMap.put(persoonslijst2.getId(), persoonslijst2);

        final Long administratieveHandelingId = 11L;

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);

        final ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor = ArgumentCaptor.forClass(MaakBerichtParameters.class);

        service.apply(Lists.newArrayList(mutatielevering), new Mutatiehandeling(administratieveHandelingId, bijgehoudenPersonenMap));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(maakBerichtParametersArgumentCaptor.capture());

        final MaakBerichtParameters maakBerichtParameters = maakBerichtParametersArgumentCaptor.getValue();
        Assert.assertEquals(1, maakBerichtParameters.getAutorisatiebundels().size());

        Assert.assertEquals(administratieveHandelingId, maakBerichtParameters.getAdministratieveHandeling());

        Assert.assertEquals(2, maakBerichtParameters.getBijgehoudenPersonen().size());

        final Map<Persoonslijst, MaakBerichtPersoonInformatie>
                maakBerichtPersoonInformatieMap =
                maakBerichtParameters.getMaakBerichtPersoonMap().get(autorisatiebundel);

        Assert.assertNotNull(maakBerichtPersoonInformatieMap);

        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie1 = maakBerichtPersoonInformatieMap.get(persoonslijst1);

        Assert.assertNotNull(maakBerichtPersoonInformatie1);

        Assert.assertNotNull(maakBerichtPersoonInformatie1.getSoortSynchronisatie());

        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie2 = maakBerichtPersoonInformatieMap.get(persoonslijst2);

        Assert.assertNull(maakBerichtPersoonInformatie2);
    }


    private Autorisatiebundel maakAutorisatieBundel(final Stelsel stelsel, final SoortDienst soortDienst) {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        leveringsautorisatie.setStelsel(stelsel);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
