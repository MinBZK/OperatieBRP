/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.levering.lo3.conversie.persoon.PersoonConverteerder;
import nl.bzk.brp.levering.lo3.conversie.wa11.Wa11Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.filter.MutatieBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Ng01BerichtFilter;
import nl.bzk.brp.levering.lo3.filter.ResyncBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Wa11BerichtFilter;
import nl.bzk.brp.levering.lo3.format.Ag01Formatter;
import nl.bzk.brp.levering.lo3.format.Ag11Formatter;
import nl.bzk.brp.levering.lo3.format.Ag21Formatter;
import nl.bzk.brp.levering.lo3.format.Ag31Formatter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.brp.levering.lo3.format.Gv01Formatter;
import nl.bzk.brp.levering.lo3.format.Gv02Formatter;
import nl.bzk.brp.levering.lo3.format.Ng01Formatter;
import nl.bzk.brp.levering.lo3.format.Sv01Formatter;
import nl.bzk.brp.levering.lo3.format.Sv11Formatter;
import nl.bzk.brp.levering.lo3.format.Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;
import nl.bzk.brp.levering.lo3.format.Wa11Formatter;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtFactoryImplTest {
    @Mock
    private IstTabelRepository istTabelRepository;

    private final PersoonConverteerder persoonConverteerder = new PersoonConverteerder(null, null);
    private final MutatieConverteerder mutatieConverteerder = new MutatieConverteerder();
    private final Wa11Converteerder wa11Converteerder =
            new Wa11Converteerder(
                    new PersoonSamengesteldeNaamMapper(),
                    new PersoonGeboorteMapper(),
                    new BrpOnderzoekLo3(),
                    null, null);

    private final VulBerichtFilter vulFilter = new VulBerichtFilter();
    private final ResyncBerichtFilter resyncFilter = new ResyncBerichtFilter(vulFilter);
    private final MutatieBerichtFilter mutatieFilter = new MutatieBerichtFilter();
    private final Ng01BerichtFilter ng01Filter = new Ng01BerichtFilter(vulFilter);
    private final Wa11BerichtFilter wa11Filter = new Wa11BerichtFilter(vulFilter);

    private final Ag01Formatter ag01Formatter = new Ag01Formatter();
    private final Ag11Formatter ag11Formatter = new Ag11Formatter();
    private final Ag21Formatter ag21Formatter = new Ag21Formatter();
    private final Ag31Formatter ag31Formatter = new Ag31Formatter();
    private final Gv01Formatter gv01Formatter = new Gv01Formatter();
    private final Gv02Formatter gv02Formatter = new Gv02Formatter();
    private final Ng01Formatter ng01Formatter = new Ng01Formatter();
    private final Sv01Formatter sv01Formatter = new Sv01Formatter();
    private final Sv11Formatter sv11Formatter = new Sv11Formatter();
    private final Wa11Formatter wa11Formatter = new Wa11Formatter();
    private final Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter wa11DubbeleInschrijvingMetVerschillendAnummerFormatter =
            new Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter();

    @InjectMocks
    private BerichtFactoryImpl subject;

    private Persoonslijst persoonslijst;

    @Before
    public void injectDependencies() {
        ReflectionTestUtils.setField(subject, "persoonConverteerder", persoonConverteerder);
        ReflectionTestUtils.setField(subject, "mutatieConverteerder", mutatieConverteerder);
        ReflectionTestUtils.setField(subject, "wa11Converteerder", wa11Converteerder);
        ReflectionTestUtils.setField(subject, "vulFilter", vulFilter);
        ReflectionTestUtils.setField(subject, "resyncFilter", resyncFilter);
        ReflectionTestUtils.setField(subject, "mutatieFilter", mutatieFilter);
        ReflectionTestUtils.setField(subject, "ng01Filter", ng01Filter);
        ReflectionTestUtils.setField(subject, "wa11Filter", wa11Filter);
        ReflectionTestUtils.setField(subject, "ag01Formatter", ag01Formatter);
        ReflectionTestUtils.setField(subject, "ag11Formatter", ag11Formatter);
        ReflectionTestUtils.setField(subject, "ag21Formatter", ag21Formatter);
        ReflectionTestUtils.setField(subject, "ag31Formatter", ag31Formatter);
        ReflectionTestUtils.setField(subject, "gv01Formatter", gv01Formatter);
        ReflectionTestUtils.setField(subject, "gv02Formatter", gv02Formatter);
        ReflectionTestUtils.setField(subject, "ng01Formatter", ng01Formatter);
        ReflectionTestUtils.setField(subject, "sv01Formatter", sv01Formatter);
        ReflectionTestUtils.setField(subject, "sv11Formatter", sv11Formatter);
        ReflectionTestUtils.setField(subject, "wa11Formatter", wa11Formatter);
        ReflectionTestUtils.setField(
                subject,
                "wa11DubbeleInschrijvingMetVerschillendAnummerFormatter",
                wa11DubbeleInschrijvingMetVerschillendAnummerFormatter);
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst, final EffectAfnemerindicaties effectAfnemerindicaties) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setEffectAfnemerindicaties(effectAfnemerindicaties);
        dienst.setId(1);

        return new Autorisatiebundel(tla, dienst);
    }

    @Before
    public void setupPersoon() {
        final MetaObject persoon =
                MetaObject.maakBuilder().metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(234534).build();
        persoonslijst = new Persoonslijst(persoon, 0L);
    }

    private Map<Persoonslijst, Populatie> maakPopulatieMap() {

        final Map<Persoonslijst, Populatie> resultaat = new HashMap<>();
        resultaat.put(persoonslijst, Populatie.BINNEN);
        return resultaat;
    }

    private Map<Persoonslijst, Populatie> maakPopulatieMap(final Persoonslijst persoonslijst) {
        final Map<Persoonslijst, Populatie> resultaat = new HashMap<>();
        resultaat.put(persoonslijst, Populatie.BINNEN);
        return resultaat;
    }

    private Map<Persoonslijst, Populatie> maakPopulatieMapBuiten() {

        final Map<Persoonslijst, Populatie> resultaat = new HashMap<>();
        resultaat.put(persoonslijst, Populatie.BUITEN);
        return resultaat;
    }

    private AdministratieveHandeling maakAdministratieveHandeling(final SoortAdministratieveHandeling soort) {
        return TestVerantwoording.maakAdministratieveHandeling(soort);
    }

    @Test
    public void testLeeg() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.ZOEK_PERSOON, null);
        final AdministratieveHandeling administratieveHandeling =
                TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.AANVANG_ONDERZOEK);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(0, berichten.size());
    }

    @Test
    public void testAg01() {
        final Bericht bericht = subject.maakAg01Bericht(persoonslijst);
        assertBerichtImpl(bericht, SoortBericht.AG01, persoonConverteerder, vulFilter, ag01Formatter, persoonslijst, null, null);
    }

    @Test
    public void testAg11() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING);
        final AdministratieveHandeling
                administratieveHandeling =
                TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.AANVANG_ONDERZOEK);
        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG11, persoonConverteerder, vulFilter, ag11Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testAg21() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.ATTENDERING, null);
        final AdministratieveHandeling
                administratieveHandeling =
                TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.AANVANG_ONDERZOEK);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG21, persoonConverteerder, vulFilter, ag21Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testAg31Correctie() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling =
                TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.CORRECTIE_GESLACHTSAANDUIDING);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG31, persoonConverteerder, resyncFilter, ag31Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testAg31Overig() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling
                .GBA_BIJHOUDING_OVERIG);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG31, persoonConverteerder, resyncFilter, ag31Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testGv01() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.GBA_WIJZIGING_GESLACHTSNAAM);

        final List<Bericht>
                berichten =
                subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling,
                        new IdentificatienummerMutatie(persoonslijst.getMetaObject(), administratieveHandeling));

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(
                bericht,
                SoortBericht.GV01,
                mutatieConverteerder,
                mutatieFilter,
                gv01Formatter,
                persoonslijst,
                administratieveHandeling,
                null);
    }

    @Test
    public void testGv02() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling =
                maakAdministratieveHandeling(SoortAdministratieveHandeling.WIJZIGING_ADRES_INFRASTRUCTUREEL);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(
                bericht,
                SoortBericht.GV02,
                mutatieConverteerder,
                mutatieFilter,
                gv02Formatter,
                persoonslijst,
                administratieveHandeling,
                null);
    }

    @Test
    public void testNg01() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.GBA_AFVOEREN_PL);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.NG01, persoonConverteerder, ng01Filter, ng01Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testNg01BuitenPopulatieMap() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.GBA_AFVOEREN_PL);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, maakPopulatieMapBuiten(), administratieveHandeling, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.NG01, persoonConverteerder, ng01Filter, ng01Formatter, persoonslijst, administratieveHandeling, null);
    }

    @Test
    public void testSv01() {
        final Bericht bericht = subject.maakSv01Bericht(persoonslijst);
        assertBerichtImpl(bericht, SoortBericht.SV01, persoonConverteerder, vulFilter, sv01Formatter, persoonslijst, null, null);
    }

    @Test
    public void testSv11() {
        final Bericht bericht = subject.maakSv11Bericht();
        assertBerichtImpl(bericht, SoortBericht.SV11, persoonConverteerder, vulFilter, sv11Formatter, null, null, null);
    }

    @Test
    public void testANummerWijziging() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);

        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(12L, "000034", null, SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObjecten(Lists.newArrayList(
                        TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0, DatumUtil
                                .BRP_ZONE_ID), "000001", null),
                        TestVerantwoording.maakActieBuilder(2, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0,
                                DatumUtil.BRP_ZONE_ID), "000001", null)
                ))
                .build());

        final Actie actieInhoud = administratieveHandeling.getActie(1);
        final Actie actieVerval = administratieveHandeling.getActie(2);

        final MetaObject persoon =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metId(234534)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                        .metRecord()
                        .metId(11)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567890")
                        .eindeRecord()
                        .metRecord()
                        .metId(12)
                        .metActieInhoud(actieInhoud)
                        .metActieVerval(actieVerval)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567891")
                        .eindeRecord()
                        .eindeGroep()
                        .build();

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final IdentificatienummerMutatie identificatienummerMutatie = new IdentificatienummerMutatie(persoon, administratieveHandeling);

        final List<Bericht> berichten =
                subject.maakBerichten(leveringAutorisatie, maakPopulatieMap(persoonslijst), administratieveHandeling, identificatienummerMutatie);

        Assert.assertEquals(2, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(
                bericht,
                SoortBericht.WA11,
                wa11Converteerder,
                wa11Filter,
                wa11Formatter,
                persoonslijst,
                administratieveHandeling,
                identificatienummerMutatie);
    }

    @Test
    public void testDubbeleInschrijvingMetVerschillendANummer() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);

        final AdministratieveHandeling handelingInhoud = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000034", ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID), SoortAdministratieveHandeling
                        .GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", null)
                ).build());
        final Actie actieInhoud = handelingInhoud.getActies().iterator().next();

        final MetaObject persoon =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metId(234534)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                        .metRecord()
                        .metId(11)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567890")
                        .eindeRecord()
                        .eindeGroep()
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId()))
                        .metRecord()
                        .metId(12)
                        .metActieInhoud(actieInhoud)
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId()), "1234567890")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()),
                                "1234123412")
                        .eindeRecord()
                        .eindeGroep()
                        .build();

        persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoonslijst, Populatie.BINNEN);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, populatieMap, handelingInhoud, null);

        Assert.assertEquals(1, berichten.size());
        final Bericht bericht = berichten.get(0);
        assertBerichtImpl(
                bericht,
                SoortBericht.WA11,
                persoonConverteerder,
                wa11Filter,
                wa11DubbeleInschrijvingMetVerschillendAnummerFormatter,
                persoonslijst,
                handelingInhoud,
                null);
    }

    @Test
    public void testDubbeleInschrijvingMetZelfdeANummer() {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandeling handelingInhoud = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000034", ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID), SoortAdministratieveHandeling
                        .GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", null)
                ).build());
        final Actie actieInhoud = handelingInhoud.getActies().iterator().next();

        final MetaObject persoon =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                        .metId(234534)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                        .metRecord()
                        .metId(11)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), "1234567890")
                        .eindeRecord()
                        .eindeGroep()
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId()))
                        .metRecord()
                        .metId(12)
                        .metActieInhoud(actieInhoud)
                        .metDatumAanvangGeldigheid(19400101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId()), "1234567890")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()),
                                "1234567890")
                        .eindeRecord()
                        .eindeGroep()
                        .build();

        persoonslijst = new Persoonslijst(persoon, 0L);

        final Map<Persoonslijst, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoonslijst, Populatie.BINNEN);

        final List<Bericht> berichten = subject.maakBerichten(leveringAutorisatie, populatieMap, handelingInhoud, null);

        Assert.assertEquals(0, berichten.size());
    }

    private void assertBerichtImpl(
            final Bericht bericht,
            final SoortBericht expectedSoortBericht,
            final Converteerder expectedConverteerder,
            final Filter expectedFilter,
            final Formatter expectedFormatter,
            final Persoonslijst expectedPersoon,
            final AdministratieveHandeling expectedAdministratieveHandeling,
            final IdentificatienummerMutatie expectedIdentificatienummerMutatieResultaat) {
        Assert.assertTrue(bericht instanceof BerichtImpl);

        final Object actualSoortBericht = ReflectionTestUtils.getField(bericht, "soortBericht");
        final Object actualConverteerder = ReflectionTestUtils.getField(bericht, "converteerder");
        final Object actualFilter = ReflectionTestUtils.getField(bericht, "filter");
        final Object actuelFormatter = ReflectionTestUtils.getField(bericht, "formatter");
        final Object actualPersoon = ReflectionTestUtils.getField(bericht, "persoon");
        final Object actualAdministratieveHandeling = ReflectionTestUtils.getField(bericht, "administratieveHandeling");
        final Object actualIdentificatienummerMutatieResultaat = ReflectionTestUtils.getField(bericht, "identificatienummerMutatie");

        Assert.assertSame(expectedSoortBericht, actualSoortBericht);
        Assert.assertSame(expectedConverteerder, actualConverteerder);
        Assert.assertSame(expectedFilter, actualFilter);
        Assert.assertSame(expectedFormatter, actuelFormatter);
        Assert.assertSame(expectedPersoon, actualPersoon);
        if (expectedAdministratieveHandeling != null) {
            Assert.assertSame(expectedAdministratieveHandeling, actualAdministratieveHandeling);
        }
        if (expectedIdentificatienummerMutatieResultaat != null) {
            Assert.assertSame(expectedIdentificatienummerMutatieResultaat, actualIdentificatienummerMutatieResultaat);
        }
    }
}
