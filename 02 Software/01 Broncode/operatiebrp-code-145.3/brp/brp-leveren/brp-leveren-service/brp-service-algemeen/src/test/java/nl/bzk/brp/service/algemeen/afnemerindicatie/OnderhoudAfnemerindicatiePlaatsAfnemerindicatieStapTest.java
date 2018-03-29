/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
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
 * Unit test voor {@link OnderhoudAfnemerindicatiePlaatsAfnemerindicatieStap}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatiePlaatsAfnemerindicatieStapTest {

    private static final int DIENST_ID = 1;
    private static final ToegangLeveringsAutorisatie TOEGANG_LEVERINGSAUTORISATIE = maakToegangLeveringautorisatie();

    private static final Persoonslijst PERSOONSLIJST = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @InjectMocks
    private OnderhoudAfnemerindicatiePlaatsAfnemerindicatieStap stap;
    @Mock
    private PersoonAfnemerindicatieService persoonAfnemerindicatieService;
    @Mock
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsPlaatsing valideerRegelsPlaatsing;


    @Before
    public void voorTest() {
        BrpNu.set();
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

    @Test
    public void voerStapUit() throws Exception {
        final ZonedDateTime nu = BrpNu.get().getDatum();
        final PlaatsAfnemerindicatieParams params
                = new PlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, PERSOONSLIJST,
                DatumUtil.vandaag(), DatumUtil.vandaag(), nu, DIENST_ID);

        stap.voerStapUit(params);

        final AfnemerindicatieParameters value = new AfnemerindicatieParameters(PERSOONSLIJST.getId(), PERSOONSLIJST.getPersoonLockVersie(),
                PERSOONSLIJST.getAfnemerindicatieLockVersie());
        verify(persoonAfnemerindicatieService)
                .plaatsAfnemerindicatie(
                        refEq(value),
                        eq(TOEGANG_LEVERINGSAUTORISATIE.getGeautoriseerde().getPartij()), eq(TOEGANG_LEVERINGSAUTORISATIE.getLeveringsautorisatie().getId()),
                        eq(DIENST_ID), eq(DatumUtil.vandaag()), eq(DatumUtil.vandaag()), eq(nu));
        verify(persoonAfnemerindicatieService).updateAfnemerindicatieBlob(refEq(value));
    }

    @Test
    public void gooitExceptieMetRegel2130AlsDienstNietAanwezig() throws Exception {
        final ZonedDateTime nu = BrpNu.get().getDatum();
        final int fouteDienstId = 2;
        final PlaatsAfnemerindicatieParams params
                = new PlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, PERSOONSLIJST,
                DatumUtil.vandaag(), DatumUtil.vandaag(), nu, fouteDienstId);

        exception.expect(new ExceptionRegelMatcher(Regel.R2130));

        stap.voerStapUit(params);

        verifyNoMoreInteractions(persoonAfnemerindicatieService);
    }

    @Test
    public void gooitStapExceptieAlsBlobExceptie() throws Exception {
        final ZonedDateTime nu = BrpNu.get().getDatum();
        final int fouteDienstId = 2;
        final PlaatsAfnemerindicatieParams params
                = new PlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, PERSOONSLIJST,
                DatumUtil.vandaag(), DatumUtil.vandaag(), nu, fouteDienstId);

        exception.expect(StapException.class);
        Mockito.doThrow(new BlobException("", new IllegalArgumentException())).when(persoonAfnemerindicatieService).updateAfnemerindicatieBlob(Mockito.any());
        stap.voerStapUit(params);

        verifyNoMoreInteractions(persoonAfnemerindicatieService);
    }

    @Test
    public void gooitStapMeldingExceptieAlsValidatieFout() throws Exception {
        final ZonedDateTime nu = BrpNu.get().getDatum();
        final int fouteDienstId = 2;
        final PlaatsAfnemerindicatieParams params
                = new PlaatsAfnemerindicatieParams(TOEGANG_LEVERINGSAUTORISATIE, PERSOONSLIJST,
                DatumUtil.vandaag(), DatumUtil.vandaag(), nu, fouteDienstId);

        exception.expect(StapException.class);
        Mockito.doThrow(new StapMeldingException(new Melding(Regel.R1406))).when(valideerRegelsPlaatsing).valideer(Mockito.any());
        stap.voerStapUit(params);

        verifyNoMoreInteractions(persoonAfnemerindicatieService);
    }

}
