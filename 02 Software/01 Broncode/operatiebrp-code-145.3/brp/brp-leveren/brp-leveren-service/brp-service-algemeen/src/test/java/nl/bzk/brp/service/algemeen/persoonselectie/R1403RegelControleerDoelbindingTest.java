/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class R1403RegelControleerDoelbindingTest {

    private static final String POPULATIEBEPERKING_WAAR = "WAAR";

    @Mock
    private ExpressieService expressieService;

    @Test
    public final void testGetRegel() {
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(null, null, null);
        Assert.assertEquals(Regel.R1403, regel.getRegel());
    }

    @Test
    public final void testVoerRegelUitGeenMeldingen() throws ExpressieException {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        leveringsautorisatie.setPopulatiebeperking(POPULATIEBEPERKING_WAAR);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        mockExpressie(true);
        final Persoonslijst persoonslijst = maakHuidigeSituatie("000001", leveringsautorisatie.getId());
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(persoonslijst, expressieService, autorisatiebundel);

        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
    }

    @Test
    public final void testVoerRegelUitPersoonValtBuitenPopulatieCriterium() throws ExpressieException {

        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        leveringsautorisatie.setPopulatiebeperking("BOGUS");
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        mockExpressie(false);
        final Persoonslijst persoonslijst = maakHuidigeSituatie("000001", leveringsautorisatie.getId());
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(persoonslijst, expressieService, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertEquals(Regel.R1403, melding.getRegel());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }

    @Test
    public final void testVoerRegelUitGeenMeldingenLeveringsinformatieIsNull() throws ExpressieException {
        final Persoonslijst persoonslijst = maakHuidigeSituatie("000001", 1);
        mockExpressie(false);
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(persoonslijst, expressieService, null);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
        Mockito.verifyZeroInteractions(expressieService);
    }

    @Test
    public final void testVoerRegelUitGeenMeldingenDienstIsNull() throws ExpressieException {
        final Persoonslijst persoonslijst = maakHuidigeSituatie("000001", 1);

        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        leveringsautorisatie.setPopulatiebeperking("BOGUS");
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, null);

        mockExpressie(true);
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(persoonslijst, expressieService, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
        Mockito.verifyZeroInteractions(expressieService);
    }

    @Test
    public final void testVoerRegelUitGeenMeldingenAbonnementIsNull() throws ExpressieException {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        leveringsautorisatie.setPopulatiebeperking("BOGUS");
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoonslijst = maakHuidigeSituatie("000001", 1);
        mockExpressie(false);
        final R1403RegelControleerDoelbinding regel = new R1403RegelControleerDoelbinding(persoonslijst, expressieService, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
        Mockito.verifyZeroInteractions(expressieService);
    }

    private void mockExpressie(boolean evalResultaat) throws ExpressieException {
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(Mockito.mock(Expressie.class));
        Mockito.when(expressieService.evalueer(Mockito.any(Expressie.class), Mockito.any(Persoonslijst.class))).thenReturn(evalResultaat);
    }

    private Persoonslijst maakHuidigeSituatie(final String partijCode, final Integer leveringsautorisatieId) {
        //@formatter:off
        final MetaObject build = TestBuilders.maakIngeschrevenPersoon()
            .metObject(TestBuilders.maakAfnemerindicatie(leveringsautorisatieId, partijCode))
            .metId(1)
        .build();
        //@formatter:on
        return new Persoonslijst(build, 0L);
    }
}
