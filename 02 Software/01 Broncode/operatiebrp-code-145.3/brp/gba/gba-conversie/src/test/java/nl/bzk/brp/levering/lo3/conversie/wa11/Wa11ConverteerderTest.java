/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.wa11;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;
import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.brp.levering.lo3.tabel.AdellijkeTitelPredikaatConversietabel;
import nl.bzk.brp.levering.lo3.tabel.GemeenteConversietabel;
import nl.bzk.brp.levering.lo3.tabel.LandConversietabel;
import nl.bzk.brp.levering.lo3.tabel.RNIDeelnemerConversietabel;
import nl.bzk.brp.levering.lo3.tabel.VoorvoegselConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Wa11ConverteerderTest {

    private final ConversieCache cache = new ConversieCache();
    private final PersoonSamengesteldeNaamMapper samengesteldeNaamMapper = new PersoonSamengesteldeNaamMapper();
    private final PersoonGeboorteMapper geboorteMapper = new PersoonGeboorteMapper();
    private final BrpOnderzoekLo3 onderzoekConverteerder = new BrpOnderzoekLo3();

    @Mock
    private ConversietabelFactory conversietabelFactory;

    private Wa11Converteerder subject;

    @Before
    public void setup() {
        Mockito.when(conversietabelFactory.createAdellijkeTitelPredikaatConversietabel())
                .thenReturn(new AdellijkeTitelPredikaatConversietabel(Collections.emptyList()));
        Mockito.when(conversietabelFactory.createVoorvoegselScheidingstekenConversietabel())
                .thenReturn(new VoorvoegselConversietabel(Collections.emptyList()));
        Mockito.when(conversietabelFactory.createGemeenteConversietabel()).thenReturn(new GemeenteConversietabel(Collections.emptyList()));
        Mockito.when(conversietabelFactory.createLandConversietabel()).thenReturn(new LandConversietabel(Collections.emptyList()));
        Mockito.when(conversietabelFactory.createRNIDeelnemerConversietabel()).thenReturn(new RNIDeelnemerConversietabel(Collections.emptyList()));

        final BrpAttribuutConverteerder brpAttribuutConverteerder = new BrpAttribuutConverteerder(conversietabelFactory);
        subject =
                new Wa11Converteerder(
                        samengesteldeNaamMapper,
                        geboorteMapper,
                        onderzoekConverteerder,
                        conversietabelFactory,
                        brpAttribuutConverteerder);
    }

    @Test
    public void test() {

        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", null,
                        SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", 0)
                ).build();
        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(ah);

        final MetaObject persoon =
                MetaObjectUtil.maakIngeschrevene(
                        Collections.emptyList(),
                        Arrays.asList(
                                b -> MetaObjectUtil.voegPersoonSamengesteldeNaamGroepToe(
                                        b,
                                        null,
                                        "geslachtsnaam",
                                        false,
                                        true,
                                        null,
                                        " ",
                                        "Voornaam1 Voornaam2",
                                        null),
                                b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, 20130101, "0518", "6030"))).build();

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoonslijst, null, handeling, null, cache);

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                Lo3ElementEnum.ELEMENT_0310,
                "20130101",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam1 Voornaam2",
                Lo3ElementEnum.ELEMENT_0240,
                "geslachtsnaam");
    }

    @Test
    public void testMetOnderzoek() {

        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", null,
                        SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", 0)
                ).build();
        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(ah);

        final MetaObject persoon =
                MetaObjectUtil.maakIngeschrevene(
                        Collections.emptyList(),
                        Arrays.asList(
                                b -> MetaObjectUtil.voegPersoonSamengesteldeNaamGroepToe(
                                        b,
                                        null,
                                        "geslachtsnaam",
                                        false,
                                        true,
                                        null,
                                        " ",
                                        "Voornaam1 Voornaam2",
                                        null),
                                b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, 20130101, "0518", "6030"))).build();

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoon);
        MetaObjectUtil.maakPersoonOnderzoek(persoonAdder, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, (short) 456, "Conversie GBA: 010210");

        final Persoonslijst persoonslijst = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoonslijst, null, handeling, null, cache);

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                Lo3ElementEnum.ELEMENT_0310,
                "20130101",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam1 Voornaam2",
                Lo3ElementEnum.ELEMENT_0240,
                "geslachtsnaam",
                Lo3ElementEnum.ELEMENT_8310,
                "010210",
                Lo3ElementEnum.ELEMENT_8320,
                "20141004");
    }

    @Test
    public void testMetHistorischOnderzoek() {
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID),
                        SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0,
                        DatumUtil.BRP_ZONE_ID), "000001", 0)
                ).build();
        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(ah);

        // final MetaObject persoon =
        // MetaObjectUtil.maakPersoonOnderzoek(
        // MetaObjectUtil.maakPersoonOnderzoek(persoonAdder, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, (short) 456,
        // "Conversie GBA: 010210"),
        // Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
        // (short) 457,
        // "Conversie GBA: 510210");
        final MetaObject persoon =
                MetaObjectUtil.maakIngeschrevene(
                        Collections.emptyList(),
                        Arrays.asList(
                                b -> MetaObjectUtil.voegVervallenPersoonSamengesteldeNaamGroepToe(
                                        b,
                                        null,
                                        "geslachtsnaam",
                                        false,
                                        true,
                                        null,
                                        " ",
                                        "Voornaam1 Voornaam2",
                                        null),
                                b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, 20130101, "0518" , "6030"))).build();

        final MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoon);
        MetaObjectUtil.maakPersoonOnderzoek(persoonAdder, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, (short) 456, "Conversie GBA: 010210");
        MetaObjectUtil.maakPersoonOnderzoek(persoonAdder, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, (short) 457, "Conversie GBA: 510210");

        final Persoonslijst persoonslijst = new Persoonslijst(persoonAdder.build(), 0L);
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoonslijst, null, handeling, null, cache);

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_01,
                true,
                Lo3ElementEnum.ELEMENT_0320,
                "0518",
                Lo3ElementEnum.ELEMENT_0310,
                "20130101",
                Lo3ElementEnum.ELEMENT_0330,
                "6030",
                Lo3ElementEnum.ELEMENT_0210,
                "Voornaam1 Voornaam2",
                Lo3ElementEnum.ELEMENT_0240,
                "geslachtsnaam",
                Lo3ElementEnum.ELEMENT_8310,
                "010210",
                Lo3ElementEnum.ELEMENT_8320,
                "20141004");
    }
}
