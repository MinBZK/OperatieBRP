/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.filter.LeveringFilterService;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling.PopulatieBepalingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MutatieleveringServiceImplTest {

    @InjectMocks
    private MutatieleveringServiceImpl service;

    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    @Mock
    private PopulatieBepalingService populatieBepalingService;

    @Mock
    private LeveringFilterService leveringFilterService;

    @Test
    public void test1AutorisatieEnPersonenNietWegfilteren() throws StapException, ExpressieException {

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(true);
        Mockito.when(leveringsAutorisatieCache.geefAutorisatieBundelsVoorMutatielevering()).thenReturn(Lists.newArrayList(
                autorisatiebundel
        ));

        final long PERSOON_A = 1L;
        final long PERSOON_B = 2L;
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        persoonsLijstMap.put(PERSOON_A, TestBuilders.maakBasisPersoon(PERSOON_A,
                administratieveHandeling.getActie(1)));
        persoonsLijstMap.put(PERSOON_B, TestBuilders.maakBasisPersoon(PERSOON_B,
                administratieveHandeling.getActie(1)));

        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandeling.getId(), persoonsLijstMap);

        final HashMap<Persoonslijst, Populatie> populatieMap = Maps.newHashMap();
        populatieMap.put(persoonsLijstMap.get(PERSOON_A).getNuNuBeeld(), Populatie.BINNEN);
        populatieMap.put(persoonsLijstMap.get(PERSOON_B).getNuNuBeeld(), Populatie.BUITEN);

        Mockito.when(populatieBepalingService.bepaalPersoonPopulatieCorrelatie(mutatiehandeling, autorisatiebundel)).thenReturn(
                populatieMap
        );
        //niet filteren
        Mockito.when(leveringFilterService.bepaalTeLeverenPersonen(autorisatiebundel, populatieMap)).thenReturn(
                Sets.newHashSet(populatieMap.keySet())
        );

        final List<Mutatielevering> mutatieleveringList = service.bepaalLeveringen(mutatiehandeling);

        //per autorisatie wordt 1 mutatielevering object aangemaakt
        Assert.assertEquals(1, mutatieleveringList.size());
        //voor de autorisatie worden 2 personen geleverd, want niets is gefilterd
        Assert.assertEquals(2, mutatieleveringList.get(0).getPersonen().size());

        //overige checks
        Assert.assertEquals(autorisatiebundel, mutatieleveringList.get(0).getAutorisatiebundel());
        Assert.assertEquals(Populatie.BINNEN, mutatieleveringList
                .get(0).getTeLeverenPersonenMap().get(persoonsLijstMap.get(PERSOON_A).getNuNuBeeld()));
        Assert.assertEquals(Populatie.BUITEN, mutatieleveringList
                .get(0).getTeLeverenPersonenMap().get(persoonsLijstMap.get(PERSOON_B).getNuNuBeeld()));
    }

    @Test
    public void test1AutorisatieEn1PersoonWegfilteren() throws StapException, ExpressieException {

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(true);
        Mockito.when(leveringsAutorisatieCache.geefAutorisatieBundelsVoorMutatielevering()).thenReturn(Lists.newArrayList(
                autorisatiebundel
        ));

        final long PERSOON_A = 1L;
        final long PERSOON_B = 2L;
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        persoonsLijstMap.put(PERSOON_A, TestBuilders.maakBasisPersoon(PERSOON_A,
                administratieveHandeling.getActie(1)));
        persoonsLijstMap.put(PERSOON_B, (TestBuilders.maakBasisPersoon(PERSOON_B,
                administratieveHandeling.getActie(1))));

        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandeling.getId(), persoonsLijstMap);

        final HashMap<Persoonslijst, Populatie> populatieMap = Maps.newHashMap();
        populatieMap.put(persoonsLijstMap.get(PERSOON_A).getNuNuBeeld(), Populatie.BINNEN);
        populatieMap.put(persoonsLijstMap.get(PERSOON_B).getNuNuBeeld(), Populatie.BUITEN);

        Mockito.when(populatieBepalingService.bepaalPersoonPopulatieCorrelatie(mutatiehandeling, autorisatiebundel)).thenReturn(
                populatieMap
        );
        //filter A
        Mockito.when(leveringFilterService.bepaalTeLeverenPersonen(autorisatiebundel, populatieMap)).thenReturn(
                Sets.newHashSet(persoonsLijstMap.get(PERSOON_B).getNuNuBeeld())
        );

        final List<Mutatielevering> mutatieleveringList = service.bepaalLeveringen(mutatiehandeling);

        //per autorisatie wordt 1 mutatielevering object aangemaakt
        Assert.assertEquals(1, mutatieleveringList.size());
        //alleen persoonB wordt geleverd
        Assert.assertEquals(1, mutatieleveringList.get(0).getPersonen().size());

        //overige checks
        Assert.assertEquals(autorisatiebundel, mutatieleveringList.get(0).getAutorisatiebundel());
        Assert.assertEquals(Populatie.BUITEN, mutatieleveringList
                .get(0).getTeLeverenPersonenMap().get(persoonsLijstMap.get(PERSOON_B).getNuNuBeeld()));
    }

    @Test
    public void test1AutorisatieEnAllePersonenWegfilteren() throws StapException, ExpressieException {

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(true);
        Mockito.when(leveringsAutorisatieCache.geefAutorisatieBundelsVoorMutatielevering()).thenReturn(Lists.newArrayList(
                autorisatiebundel
        ));

        final long PERSOON_A = 1L;
        final long PERSOON_B = 2L;
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        persoonsLijstMap.put(PERSOON_A, TestBuilders.maakBasisPersoon(PERSOON_A,
                administratieveHandeling.getActie(1)));
        persoonsLijstMap.put(PERSOON_B, TestBuilders.maakBasisPersoon(PERSOON_B,
                administratieveHandeling.getActie(1)));

        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandeling.getId(), persoonsLijstMap);

        final HashMap<Persoonslijst, Populatie> populatieMap = Maps.newHashMap();
        populatieMap.put(persoonsLijstMap.get(PERSOON_A), Populatie.BINNEN);
        populatieMap.put(persoonsLijstMap.get(PERSOON_B), Populatie.BUITEN);

        Mockito.when(populatieBepalingService.bepaalPersoonPopulatieCorrelatie(mutatiehandeling, autorisatiebundel)).thenReturn(
                populatieMap
        );
        //filter A
        Mockito.when(leveringFilterService.bepaalTeLeverenPersonen(autorisatiebundel, populatieMap)).thenReturn(
                Sets.newHashSet()
        );

        final List<Mutatielevering> mutatieleveringList = service.bepaalLeveringen(mutatiehandeling);

        //per autorisatie wordt 1 mutatielevering object aangemaakt
        Assert.assertTrue(mutatieleveringList.isEmpty());
    }

    @Test
    public void testExpressieFout() throws StapException, ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(true);
        Mockito.when(leveringsAutorisatieCache.geefAutorisatieBundelsVoorMutatielevering()).thenReturn(Lists.newArrayList(
                autorisatiebundel
        ));

        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandeling.getId(), persoonsLijstMap);
        final HashMap<Persoonslijst, Populatie> populatieMap = Maps.newHashMap();
        Mockito.when(populatieBepalingService.bepaalPersoonPopulatieCorrelatie(mutatiehandeling, autorisatiebundel)).thenReturn(
                populatieMap
        );

        Mockito.doThrow(ExpressieException.class).when(leveringFilterService).bepaalTeLeverenPersonen(autorisatiebundel, populatieMap);

        final List<Mutatielevering> mutatieleveringList = service.bepaalLeveringen(mutatiehandeling);
        Assert.assertTrue(mutatieleveringList.isEmpty());
    }

    private Autorisatiebundel maakAutorisatiebundel(boolean afleverpuntGevuld) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        leveringsautorisatie.setStelsel(Stelsel.BRP);
        if (afleverpuntGevuld) {
            tla.setAfleverpunt("nietleeg");
        }
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
