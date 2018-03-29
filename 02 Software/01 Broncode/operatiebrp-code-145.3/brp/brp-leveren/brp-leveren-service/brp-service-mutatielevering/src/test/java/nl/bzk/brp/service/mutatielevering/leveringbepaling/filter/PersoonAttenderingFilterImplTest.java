/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PersoonAttenderingFilterImplTest {

    private static final String ATTENDERINGS_CRITERIUM_WAAR = "WAAR";
    private static final String WAAR = "WAAR";

    @InjectMocks
    private PersoonAttenderingFilterImpl filter;

    @Mock
    private ExpressieService expressieService;

    private Persoonslijst persoon;

    @Before
    public void voorTest() {
        persoon = TestBuilders.maakPersoonMetHandelingen(1);
    }

    @Test
    public final void testMagLeverenDoorgaanTrueAndereDienst() throws ExpressieException {
        Assert.assertTrue(filter.magLeveren(persoon, null, maakLevAutAndereDienst()));
    }

    @Test
    public final void testNietLeverenIndienGeenAttCriteriumVoorDienstAttendering() throws ExpressieException {
        assertFalse(filter.magLeveren(persoon, null, maakLevAutAttenderingPlaatsingGeenAttCriterium()));
    }

    @Test
    public final void testNietLeverenIndienGeenAttCriteriumVoorDienstAttenderingMetPlaatsing() throws ExpressieException {
        assertFalse(filter.magLeveren(persoon, null, maakLevAutAttenderingPlaats()));
    }

    @Test
    public final void testNietLeverenIndienGVoorDienstAttenderingMetPlaatsingGeenEerdereIndicatieGeplaatst() throws ExpressieException {
        when(expressieService.geefAttenderingsCriterium(any(Autorisatiebundel.class))).thenReturn(ExpressieParser.parse(WAAR));
        assertFalse(filter.magLeveren(persoon, null, maakLevAutAttenderingPlaats()));
    }

    @Test
    public final void testNietLeverenIndienGVoorDienstAttenderingMetPlaatsingMetGeldendeAfnemerIndicatie() throws ExpressieException {
        when(expressieService.geefAttenderingsCriterium(any(Autorisatiebundel.class))).thenReturn(ExpressieParser.parse(WAAR));
        Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingenEnAfnemerindicatie(1);
        assertFalse(filter.magLeveren(persoonslijst, null, maakLevAutAttenderingPlaats()));
    }


    @Test
    public final void testMagLeverenDoorgaanFalseNietsGewijzigd() throws ExpressieException {
        when(expressieService.bepaalPersoonGewijzigd(eq(persoon.beeldVan().vorigeHandeling()), eq(persoon), any(Expressie.class)))
                .thenReturn(false);
        when(expressieService.geefAttenderingsCriterium(any(Autorisatiebundel.class))).thenReturn(ExpressieParser.parse(WAAR));

        assertFalse(filter.magLeveren(persoon, null, maakLevAutAttendering()));
    }


    @Test
    public final void testMagLeverenDoorgaanTrue() throws ExpressieException {
        when(expressieService.bepaalPersoonGewijzigd(eq(persoon.beeldVan().vorigeHandeling()), eq(persoon), any(Expressie.class)))
                .thenReturn(true);
        when(expressieService.geefAttenderingsCriterium(any(Autorisatiebundel.class))).thenReturn(ExpressieParser.parse(WAAR));

        final boolean resultaat = filter
                .magLeveren(persoon, null, maakLevAutAttendering());

        Assert.assertTrue(resultaat);
    }

    private Autorisatiebundel maakLevAutAttendering() {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Autorisatiebundel liMetAttendering = maakAutorisatiebundel(soortDienst);
        return new Autorisatiebundel(liMetAttendering.getToegangLeveringsautorisatie(),
                AutAutUtil.zoekDienst(liMetAttendering.getLeveringsautorisatie(), soortDienst));
    }

    private Autorisatiebundel maakLevAutAndereDienst() {
        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING;
        final Autorisatiebundel liMetAttendering = maakAutorisatiebundel(soortDienst);
        return new Autorisatiebundel(liMetAttendering.getToegangLeveringsautorisatie(),
                AutAutUtil.zoekDienst(liMetAttendering.getLeveringsautorisatie(), soortDienst));
    }

    private Autorisatiebundel maakLevAutAttenderingPlaats() {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Autorisatiebundel liMetAttendering = maakAutorisatiebundel(soortDienst);
        final Dienst dienst = AutAutUtil.zoekDienst(liMetAttendering.getLeveringsautorisatie(), soortDienst);
        dienst.setEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING);
        dienst.setAttenderingscriterium(ATTENDERINGS_CRITERIUM_WAAR);
        return new Autorisatiebundel(liMetAttendering.getToegangLeveringsautorisatie(),
                dienst);
    }

    private MetaObject maakPersoonMetaObject() {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .build();
    }


    private MetaObject maakPersoonMetaObjectMetGeldigeAfnemerIndicatie() {
        return MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                .metObject(TestBuilders.maakAfnemerindicatie(1, "000001"))
                .build();
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(soortDienst);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }

    private Autorisatiebundel maakLevAutAttenderingPlaatsingGeenAttCriterium() {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Autorisatiebundel liMetAttendering = maakAutorisatiebundel(soortDienst);
        final Dienst dienst = AutAutUtil.zoekDienst(liMetAttendering.getLeveringsautorisatie(), soortDienst);
        dienst.setEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING);
        return new Autorisatiebundel(liMetAttendering.getToegangLeveringsautorisatie(),
                dienst);
    }

}
