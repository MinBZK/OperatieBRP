/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.expressie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpressieServiceImplTest {

    private static final String WAAR = "WAAR";

    private static final String BSN = "172814259";
    private static Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime().minusYears(1));


    //@formatter:off
    private static final Persoonslijst PERSOONSLIJST = new Persoonslijst(
            MetaObject.maakBuilder()
                .metId(1)
                .metObjectElement(Element.PERSOON.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metDatumAanvangGeldigheid(18 * 1000 * 10 * 10)
                        .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(),  BSN)
                    .eindeRecord()
                .eindeGroep()
                .build(),
            0L);
        //@formatter:on

    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    @InjectMocks
    private ExpressieServiceImpl expressieService;


    @Test
    public void testBepaalPersoonGewijzigd() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING);
        final Expressie attCriterium = expressieService.geefAttenderingsCriterium(autorisatiebundel);
        assertTrue(expressieService.bepaalPersoonGewijzigd(PERSOONSLIJST, PERSOONSLIJST, attCriterium));
    }

    @Test
    public void testEvalueerExpressie() throws Exception {
        final Expressie expressie = ExpressieParser.parse(String.format("BSN = \"%s\"", BSN));

        final Boolean isWaar = expressieService.evalueer(expressie, PERSOONSLIJST);

        assertThat(isWaar, is(true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEvalueerExpressieMetExceptie() {
        Expressie expressie = Mockito.mock(Expressie.class);
        Mockito.when(expressie.evalueer(Mockito.any())).thenThrow(ExpressieException.class);
        final Boolean isWaar = expressieService
                .evalueer(expressie, PERSOONSLIJST);

        assertThat(isWaar, is(false));
    }


    @Test
    public void testMetLijstWaar() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "987654321");
        final Expressie populatieBeperking = ExpressieParser.parse("SELECTIE_LIJST()");
        SelectieLijst selectieLijst = new SelectieLijst(1, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Sets.newHashSet("123456789"));
        final boolean resultaat = expressieService.evalueerMetSelectieDatumEnSelectielijst(populatieBeperking, persoonslijst, 20101010, selectieLijst);
        Assert.assertTrue(resultaat);
    }

    @Test
    public void testMetLijstOnwaar() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "987654321");
        final Expressie populatieBeperking = ExpressieParser.parse("SELECTIE_LIJST()");
        SelectieLijst selectieLijst = new SelectieLijst(1, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Sets.newHashSet("123456780"));
        final boolean resultaat = expressieService.evalueerMetSelectieDatumEnSelectielijst(populatieBeperking, persoonslijst, 20101010, selectieLijst);
        Assert.assertFalse(resultaat);
    }

    @Test
    public void testGeefPopulatiebeperking() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Expressie resultaat = expressieService.geefPopulatiebeperking(autorisatiebundel);
        Assert.assertEquals(WAAR, resultaat.toString());
    }

    @Test
    public void testAttenderingscriterium() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING);
        final Expressie resultaat = expressieService.geefAttenderingsCriterium(autorisatiebundel);
        Assert.assertEquals(WAAR, resultaat.toString());
    }


    private Autorisatiebundel maakAutorisatiebundel(SoortDienst soortDienst) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setPopulatiebeperking("dummy");
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setNaderePopulatiebeperking("dummy");

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.getDienstbundel().setNaderePopulatiebeperking("x");
        dienst.setAttenderingscriterium("dummy");

        Mockito.when(leveringsAutorisatieCache.geefPopulatiebeperking(tla, dienst)).thenReturn(BooleanLiteral.WAAR);
        Mockito.when(leveringsAutorisatieCache.geefAttenderingExpressie(dienst)).thenReturn(BooleanLiteral.WAAR);

        return new Autorisatiebundel(tla, dienst);
    }
}
