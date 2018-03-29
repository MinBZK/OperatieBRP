/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link OnderhoudAfnemerindicatieValideerRegelsPlaatsing}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatieValideerRegelsPlaatsingTest {

    private static final int DIENST_ID = 1;
    private static final ToegangLeveringsAutorisatie TOEGANG_LEVERINGSAUTORISATIE = maakToegangLeveringautorisatie();
    private static final int DATUM_IN_TOEKOMST = DatumUtil.vanDatumNaarInteger(DatumUtil.vanDateTimeNaarDate(ZonedDateTime.now().plusYears(5)));
    private static final int DATUM_IN_VERLEDEN = DatumUtil.vanDatumNaarInteger(DatumUtil.vanDateTimeNaarDate(ZonedDateTime.now().minusYears(5)));

    @InjectMocks
    private OnderhoudAfnemerindicatieValideerRegelsPlaatsing service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        BrpNu.set();
    }

    @Test
    public void valideerZonderMeldingen() throws Exception {
        final GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams valideerPlaatsAfnemerindicatieParams =
                new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, maakPersoonslijst(),
                        DATUM_IN_VERLEDEN, DATUM_IN_TOEKOMST);
        service.valideer(valideerPlaatsAfnemerindicatieParams);
    }

    @Test
    public void testValidatiefout_datumAanvangMaterieelLigtInToekomst() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1405));

        final GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams valideerPlaatsAfnemerindicatieParams =
                new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, maakPersoonslijst(),
                        DATUM_IN_TOEKOMST, DATUM_IN_TOEKOMST);
        service.valideer(valideerPlaatsAfnemerindicatieParams);
    }

    @Test
    public void testValidatiefout_datumEindeVolgenLigtNietInToekomst() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1406));

        final GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams valideerPlaatsAfnemerindicatieParams =
                new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, maakPersoonslijst()
                        , DATUM_IN_VERLEDEN, DATUM_IN_VERLEDEN);
        service.valideer(valideerPlaatsAfnemerindicatieParams);
    }

    @Test
    public void testValidatiefout_BestaandeAfnemerIndicatie() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1402));

        final GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams valideerPlaatsAfnemerindicatieParams =
                new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(maakToegangLeveringautorisatie(),
                        maakPersoonslijstMetAfnemerIndicatie(), DATUM_IN_VERLEDEN, DATUM_IN_TOEKOMST);
        service.valideer(valideerPlaatsAfnemerindicatieParams);
    }

    private static ToegangLeveringsAutorisatie maakToegangLeveringautorisatie() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setId(DIENST_ID);
        return tla;
    }

    private Persoonslijst maakPersoonslijstMetAfnemerIndicatie() {
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metId(1)
                .metObject(TestBuilders.maakAfnemerindicatie(1, "000001"))
                .build();
        return new Persoonslijst(persoon, 0L);
    }

    private Persoonslijst maakPersoonslijst() {
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon().metId(1).build();
        return new Persoonslijst(persoon, 0L);
    }
}
