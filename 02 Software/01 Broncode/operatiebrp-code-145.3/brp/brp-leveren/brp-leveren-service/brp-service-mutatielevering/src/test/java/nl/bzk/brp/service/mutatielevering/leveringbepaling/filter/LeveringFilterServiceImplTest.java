/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeveringFilterServiceImplTest {

    @InjectMocks
    private LeveringFilterServiceImpl service;

    @Mock
    private Leveringfilter persoonPopulatieFilter;
    @Mock
    private Leveringfilter persoonAfnemerindicatieFilter;
    @Mock
    private Leveringfilter persoonAttenderingFilter;
    @Mock
    private Leveringfilter verstrekkingsbeperkingfilter;

    @Before
    public void before() throws ExpressieException {
        service.postConstruct();
        Mockito.when(persoonPopulatieFilter.magLeveren(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(persoonAfnemerindicatieFilter.magLeveren(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(persoonAttenderingFilter.magLeveren(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(verstrekkingsbeperkingfilter.magLeveren(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    public void testNietLeverenObvPopulatiefilter() throws ExpressieException {
        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        mogelijkTeLeverenPersonen.put(persoonA, Populatie.BUITEN);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, Populatie.BUITEN,
                autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, Populatie.BUITEN, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter,
                Mockito.never()).magLeveren(Mockito.anyObject(), Mockito.any(), Mockito.anyObject());
    }

    @Test
    public void testNietLeverenObvBinnenPopulatiefilter() throws ExpressieException {

        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        mogelijkTeLeverenPersonen.put(persoonA, Populatie.BINNEN);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, Populatie.BINNEN,
                autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, Populatie.BINNEN, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter,
                Mockito.never()).magLeveren(Mockito.anyObject(),Mockito.anyObject(), Mockito.anyObject());
    }

    @Test
    public void testNietLeverenObvFoutieveDienst() throws ExpressieException {

        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundelDienstGeefDetailsPersoon();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        mogelijkTeLeverenPersonen.put(persoonA, Populatie.BUITEN);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, Populatie.BUITEN,
                autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, Populatie.BUITEN, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter,
                Mockito.never()).magLeveren(Mockito.anyObject(), Mockito.anyObject(), Mockito.anyObject());
    }

    @Test
    public void testNietLeverenObvAfnemerindicatiefilter() throws ExpressieException {

        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        final Populatie populatie = Populatie.BUITEN;
        mogelijkTeLeverenPersonen.put(persoonA, populatie);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, populatie,
                autorisatiebundel)).thenReturn(true);

        Mockito.when(persoonAfnemerindicatieFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAttenderingFilter,
                Mockito.never()).magLeveren(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testNietLeverenObvAttenderingfilter() throws ExpressieException {

        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        final Populatie populatie = Populatie.BUITEN;
        mogelijkTeLeverenPersonen.put(persoonA, populatie);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, populatie,
                autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAfnemerindicatieFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAttenderingFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAttenderingFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(verstrekkingsbeperkingfilter,
                Mockito.never()).magLeveren(persoonA, populatie, autorisatiebundel);
    }

    @Test
    public void testNietLeverenObvVerstrekkingbeperkingfilter() throws ExpressieException {

        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        final Populatie populatie = Populatie.BUITEN;
        mogelijkTeLeverenPersonen.put(persoonA, populatie);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAfnemerindicatieFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAttenderingFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(verstrekkingsbeperkingfilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(false);

        final Set<Persoonslijst> set = service.bepaalTeLeverenPersonen(autorisatiebundel, mogelijkTeLeverenPersonen);
        Assert.assertFalse(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAttenderingFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(verstrekkingsbeperkingfilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
    }

    @Test
    public void testWelLeveren() throws ExpressieException {
        final Persoonslijst persoonA = TestBuilders.maakPersoonMetHandelingen(1);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final HashMap<Persoonslijst, Populatie> mogelijkTeLeverenPersonen = Maps.newHashMap();
        final Populatie populatie = Populatie.BUITEN;
        mogelijkTeLeverenPersonen.put(persoonA, populatie);

        Mockito.when(persoonPopulatieFilter.magLeveren(persoonA, populatie,
                autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAfnemerindicatieFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(persoonAttenderingFilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);
        Mockito.when(verstrekkingsbeperkingfilter.magLeveren(persoonA, populatie, autorisatiebundel)).thenReturn(true);

        final Set<Persoonslijst> set = service
                .bepaalTeLeverenPersonen(autorisatiebundel,
                        mogelijkTeLeverenPersonen);
        Assert.assertTrue(set.contains(persoonA));

        //verify
        Mockito.verify(persoonPopulatieFilter, Mockito.only())
                .magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAfnemerindicatieFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(persoonAttenderingFilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
        Mockito.verify(verstrekkingsbeperkingfilter, Mockito.only()).magLeveren(persoonA, populatie, autorisatiebundel);
    }

    private Autorisatiebundel maakAutorisatiebundel() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }

    private Autorisatiebundel maakAutorisatiebundelDienstGeefDetailsPersoon() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.GEEF_DETAILS_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
