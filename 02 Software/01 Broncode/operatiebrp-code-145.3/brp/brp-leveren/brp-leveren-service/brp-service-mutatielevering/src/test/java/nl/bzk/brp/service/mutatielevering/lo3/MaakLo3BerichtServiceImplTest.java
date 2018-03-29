/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.lo3;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * MaakLo3BerichtServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakLo3BerichtServiceImplTest {

    private static final String UITGAAND_BERICHT = "uitgaand bericht";
    @InjectMocks
    private MaakLo3BerichtServiceImpl maakLo3BerichtService;
    @Mock
    private BerichtFactory berichtFactory;
    @Mock
    private Bericht bericht;
    @Mock
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    @Mock
    private PartijService partijService;

    @Before
    public void before() {
        BrpNu.set();
    }

    @Test
    public void maakBerichtenHappyFlow() throws StapException {
        final Mutatielevering mutatieLevering = maakMutatieLevering(Stelsel.GBA);
        final Persoonslijst persoonslijst = mutatieLevering.getPersonen().iterator().next();
        final AdministratieveHandeling administratieveHandeling = persoonslijst.getAdministratieveHandeling();
        new IdentificatienummerMutatie(
                persoonslijst.getMetaObject(),
                administratieveHandeling);
        Mockito.when(
                berichtFactory.maakBerichten(
                        Matchers.eq(mutatieLevering.getAutorisatiebundel()),
                        Matchers.eq(mutatieLevering.getTeLeverenPersonenMap()),
                        Matchers.eq(administratieveHandeling),
                        Matchers.any(IdentificatienummerMutatie.class)))
                .thenReturn(Lists.newArrayList(bericht));
        Mockito.when(bericht.filterRubrieken(any())).thenReturn(true);
        Mockito.when(bericht.getSoortSynchronisatie()).thenReturn(SoortSynchronisatie.VOLLEDIG_BERICHT);
        Mockito.when(bericht.getPersoonsgegevens()).thenReturn(Iterables.getOnlyElement(mutatieLevering.getPersonen()));
        Mockito.when(bericht.maakUitgaandBericht()).thenReturn(UITGAAND_BERICHT);
        final Partij brpPartij = TestPartijBuilder.maakBuilder().metId(20).metCode("000000").build();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(brpPartij);

        final List<Mutatiebericht>
                mutatieBerichten =
                maakLo3BerichtService.maakBerichten(Lists.newArrayList(mutatieLevering), new Mutatiehandeling(administratieveHandeling.getId(), new HashMap
                        <>()));

        Mockito.verify(berichtFactory).maakBerichten(
                Matchers.eq(mutatieLevering.getAutorisatiebundel()),
                Matchers.eq(mutatieLevering.getTeLeverenPersonenMap()),
                Matchers.eq(administratieveHandeling),
                Matchers.any(IdentificatienummerMutatie.class));
        Mockito.verify(lo3FilterRubriekRepository)
                .haalLo3FilterRubriekenVoorDienstbundel(mutatieLevering.getAutorisatiebundel().getDienst().getDienstbundel().getId());
        Assert.assertEquals(1, mutatieBerichten.size());
        Assert.assertThat(mutatieBerichten.get(0).getInhoudelijkBericht(), is(UITGAAND_BERICHT));
    }

    @Test
    public void maakBerichtenGeenGBAStelsel() throws StapException {
        final Mutatielevering mutatieLevering = maakMutatieLevering(Stelsel.BRP);
        final List<Mutatiebericht>
                mutatieBerichten =
                maakLo3BerichtService.maakBerichten(Lists.newArrayList(mutatieLevering), new Mutatiehandeling(0L, new HashMap<>()));
        Assert.assertEquals(0, mutatieBerichten.size());
    }

    @Test
    public void maakBerichtenGeenLo3FilterRubrieken() throws StapException {
        final Mutatielevering mutatieLevering = maakMutatieLevering(Stelsel.GBA);
        final Persoonslijst persoonslijst = mutatieLevering.getPersonen().iterator().next();
        final AdministratieveHandeling administratieveHandeling = persoonslijst.getAdministratieveHandeling();
        new IdentificatienummerMutatie(
                persoonslijst.getMetaObject(),
                administratieveHandeling);
        Mockito.when(
                berichtFactory.maakBerichten(
                        Matchers.eq(mutatieLevering.getAutorisatiebundel()),
                        Matchers.eq(mutatieLevering.getTeLeverenPersonenMap()),
                        Matchers.eq(administratieveHandeling),
                        Matchers.any(IdentificatienummerMutatie.class)))
                .thenReturn(Lists.newArrayList(bericht));

        final List<Mutatiebericht>
                mutatieBerichten =
                maakLo3BerichtService.maakBerichten(Lists.newArrayList(mutatieLevering), new Mutatiehandeling(administratieveHandeling.getId(), new HashMap
                        <>()));

        Mockito.verify(berichtFactory).maakBerichten(
                Matchers.eq(mutatieLevering.getAutorisatiebundel()),
                Matchers.eq(mutatieLevering.getTeLeverenPersonenMap()),
                Matchers.eq(administratieveHandeling),
                Matchers.any(IdentificatienummerMutatie.class));
        Mockito.verify(lo3FilterRubriekRepository)
                .haalLo3FilterRubriekenVoorDienstbundel(mutatieLevering.getAutorisatiebundel().getDienst().getDienstbundel().getId());
        Assert.assertEquals(0, mutatieBerichten.size());
    }

    private Mutatielevering maakMutatieLevering(final Stelsel stelsel) {

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final MetaObject.Builder builder = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", nu, SoortAdministratieveHandeling.ACTUALISERING_KIND);
        builder.metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, nu, "000123", 0));
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(builder.build());

        final MetaObject metaObject =
                MetaObject.maakBuilder()
                        .metObjectElement(Element.PERSOON)
                        .metId(45252L)
                        .metGroep()
                        .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS)
                        .metRecord()
                        .metId(234524)
                        .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, "152452324")
                        .metActieInhoud(Iterables.getOnlyElement(administratieveHandeling.getActies()))
                        .eindeRecord()
                        .eindeGroep()
                        .build();
        final Persoonslijst pg = new Persoonslijst(metaObject, 0L);
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        persoonsLijstMap.put(pg.getId(), pg);
        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandeling.getId(), persoonsLijstMap);

        // autorisatiebundel; stelsel=GBA
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000123").build();
        partij.setId((short) 1);

        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setStelsel(Stelsel.GBA);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setId(1);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, dienst);

        // te leveren personen
        final HashMap<Persoonslijst, Populatie> teLeverenPersonenMap = Maps.newHashMap();
        teLeverenPersonenMap.put(pg, Populatie.BINNEN);

        return new Mutatielevering(autorisatiebundel, teLeverenPersonenMap);
    }
}
