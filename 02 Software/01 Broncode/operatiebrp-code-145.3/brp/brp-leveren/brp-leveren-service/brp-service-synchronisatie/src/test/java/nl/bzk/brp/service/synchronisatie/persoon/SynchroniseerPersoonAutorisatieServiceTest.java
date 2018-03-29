/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.AutorisatieExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * R1347RegelTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchroniseerPersoonAutorisatieServiceTest {

    @InjectMocks
    private SynchroniseerPersoonAutorisatieServiceImpl synchroniseerPersoonAutorisatieService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        BrpNu.set();
    }

    @Test
    public final void testJuisteDienstOpLeveringsautorisatieMutatieLeveringOpDoelbinding() throws Exception {
        final Autorisatiebundel autorisatiebundel =
                maakAutorisatieBundel(SoortDienst.SYNCHRONISATIE_PERSOON, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any(AutorisatieParams.class))).thenReturn(autorisatiebundel);

        Assert.assertNotNull(synchroniseerPersoonAutorisatieService.controleerAutorisatie(maakVerzoek()));
    }

    @Test
    public final void testJuisteDienstOpLeveringsautorisatieMutatieLeveringOpAfnemerindicatie() throws Exception {
        final Autorisatiebundel autorisatiebundel =
                maakAutorisatieBundel(SoortDienst.SYNCHRONISATIE_PERSOON, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any(AutorisatieParams.class))).thenReturn(autorisatiebundel);

        Assert.assertNotNull(synchroniseerPersoonAutorisatieService.controleerAutorisatie(maakVerzoek()));
    }

    @Test
    public final void testGeenGeldigeDienstOpLeveringsautorisatie() throws Exception {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(SoortDienst.SYNCHRONISATIE_PERSOON);
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any(AutorisatieParams.class))).thenReturn(autorisatiebundel);

        thrown.expect(new AutorisatieExceptionRegelMatcher(Regel.R1347));

        synchroniseerPersoonAutorisatieService.controleerAutorisatie(maakVerzoek());

    }

    @Test
    public final void testVerkeerdeDienstOpLeveringsautorisatie() throws Exception {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(SoortDienst.GEEF_DETAILS_PERSOON);
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any(AutorisatieParams.class))).thenReturn(autorisatiebundel);

        thrown.expect(new AutorisatieExceptionRegelMatcher(Regel.R1347));

        synchroniseerPersoonAutorisatieService.controleerAutorisatie(maakVerzoek());
    }

    @SuppressWarnings({"unchecked"})
    @Test(expected = StapMeldingException.class)
    public final void testGeneriekeAutorisatieFout() throws Exception {
        Mockito.when(leveringsautorisatieService.controleerAutorisatie(Mockito.any(AutorisatieParams.class))).thenThrow(StapMeldingException.class);

        synchroniseerPersoonAutorisatieService.controleerAutorisatie(maakVerzoek());
    }

    private Autorisatiebundel maakAutorisatieBundel(SoortDienst... soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.SYNCHRONISATIE_PERSOON);
        return new Autorisatiebundel(tla, dienst);
    }

    private SynchronisatieVerzoek maakVerzoek() {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getStuurgegevens().setZendendePartijCode("1");
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        return verzoek;
    }
}
