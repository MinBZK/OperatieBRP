/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.persoon.PersoonConverteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpBepalenMaterieleHistorie;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpSorterenLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerBrpNaarLo3ServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Representeert een lo3 adhoc webservice antwoord.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AdhocWebserviceAntwoordIT {

    @Inject
    private PersoonslijstMapper persoonslijstMapper;

    private IstTabelRepository istTabelRepository;

    private Converteerder converteerder;

    private Filter filter;

    private AdhocWebserviceAntwoord subject;

    @Before
    public void setup() {
        filter = new VulBerichtFilter();
        ConversietabelFactory conversieTabelFactory = new ConversietabelFactoryImpl();
        ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service = new ConverteerBrpNaarLo3ServiceImpl(new BrpBepalenMaterieleHistorie(),
                new BrpConverteerder(new BrpAttribuutConverteerder(conversieTabelFactory)), new BrpOnderzoekLo3(), new BrpSorterenLo3());
        converteerder = new PersoonConverteerder(converteerBrpNaarLo3Service, persoonslijstMapper);
        istTabelRepository = mock(IstTabelRepository.class);
        when(istTabelRepository.leesIstStapels(anyLong())).thenReturn(Collections.emptyList());
    }

    @Test
    public void achternaamRubriek() {
        subject = new AdhocWebserviceAntwoord(istTabelRepository, converteerder, filter, Collections.singletonList(persoonslijst(null)));
        List<List<Lo3CategorieWaarde>> result = subject.rubrieken(Collections.singletonList("01.02.40"));
        assertEquals(1, result.size());
        assertEquals("achternaam", result.get(0).get(0).getElement(Lo3ElementEnum.ELEMENT_0240));
    }

    @Test
    public void legeRubrieken() {
        subject = new AdhocWebserviceAntwoord(istTabelRepository, converteerder, filter, Collections.singletonList(persoonslijst(null)));
        List<List<Lo3CategorieWaarde>> result = subject.rubrieken(Collections.emptyList());
        assertEquals(1, result.size());
        assertEquals(true, result.get(0).isEmpty());
    }

    @Test
    public void meeverstrekkenOpschortReden() {
        subject =
                new AdhocWebserviceAntwoord(istTabelRepository, converteerder, filter,
                        Collections.singletonList(persoonslijst(BrpNadereBijhoudingsaardCode.EMIGRATIE)));
        List<List<Lo3CategorieWaarde>> result = subject.rubrieken(Collections.emptyList());
        assertEquals(1, result.size());
        assertFalse(result.get(0).isEmpty());
        assertEquals(Lo3CategorieEnum.CATEGORIE_07, result.get(0).get(0).getCategorie());
        assertNotNull(result.get(0).get(0).getElement(Lo3ElementEnum.ELEMENT_6710));
        assertEquals(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.getCode(), result.get(0).get(0).getElement(Lo3ElementEnum.ELEMENT_6720));
    }

    private Persoonslijst persoonslijst(final BrpNadereBijhoudingsaardCode opschortreden) {
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", ZonedDateTime.now(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, ZonedDateTime.now(), "000123", 0)).build());

        MetaObject.Builder builder = TestBuilders.maakIngeschrevenPersoon();
        //@formatter:off
        if(opschortreden == null){
        builder
                .metGroep()
                .metGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId())
                .metRecord()
                .metId(0)
                .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), "achternaam")
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
        } else {
        builder
                .metGroep()
                .metGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId())
                .metRecord()
                .metId(0)
                .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), "achternaam")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                .metRecord()
                .metId(0)
                .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()), opschortreden.getWaarde())
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
                }
        //@formatter:on

        return new Persoonslijst(builder.build(), 1L);
    }

    @Configuration
    @ComponentScan("nl.bzk.brp.levering.lo3.mapper")
    public static class SpringConfig {
        @Bean
        public VerConvRepository verConvRepository() {
            return mock(VerConvRepository.class);
        }
    }
}
