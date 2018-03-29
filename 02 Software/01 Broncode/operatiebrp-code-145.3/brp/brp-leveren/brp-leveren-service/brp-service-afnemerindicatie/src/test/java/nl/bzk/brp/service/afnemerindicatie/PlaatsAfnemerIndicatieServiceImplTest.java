/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.VerzoekAsynchroonBerichtQueueService;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen.PlaatsAfnemerindicatie;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaatsAfnemerIndicatieServiceImplTest {

    @InjectMocks
    private PlaatsAfnemerIndicatieServiceImpl service;

    @Mock
    private VerzoekAsynchroonBerichtQueueService zetBerichtOpQueueService;
    @Mock
    private PlaatsAfnemerindicatie plaatsAfnemerindicatieStap;
    @Mock
    private OnderhoudAfnemerindicatiePersoonBerichtFactory onderhoudAfnemerindicatieBerichtFactory;
    @Mock
    private DatumService datumService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        BrpNu.set();
    }

    @Test
    public void testHappyflow() throws StapException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setDatumEindeVolgen("2001-01-01");
        afnemerindicatie.setDatumAanvangMaterielePeriode("2001-01-01");
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode("123");

        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metTijdstipVerzending(nu)
                .eindeStuurgegevens()
                .build();
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null,
                Collections.singletonList(new BijgehoudenPersoon.Builder(null, null).build()));
        Mockito.when(onderhoudAfnemerindicatieBerichtFactory.maakBericht(any(), any(), any(), any(),
                eq(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE), any())).thenReturn(bericht);
        Mockito.when(datumService.parseDate(afnemerindicatie.getDatumEindeVolgen())).thenReturn(LocalDate.of(2001, 1, 1));

        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(afnemerindicatieVerzoek);
        verzoekResultaat.setAutorisatiebundel(autorisatiebundel);
        verzoekResultaat.setPersoonslijst(persoonslijst);
        service.plaatsAfnemerindicatie(verzoekResultaat);

        Mockito.verify(plaatsAfnemerindicatieStap).voerStapUit(any());
        Mockito.verify(zetBerichtOpQueueService).plaatsQueueberichtVoorVerzoek(eq(bericht), eq(autorisatiebundel), eq(20010101));
    }

    @Test
    public void testHappyflowLegeDatumsAfnemerindicatie() throws StapException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setDatumEindeVolgen(null);
        afnemerindicatie.setDatumAanvangMaterielePeriode(null);
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode("123");

        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .eindeStuurgegevens()
                .build();
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null,
                Collections.singletonList(new BijgehoudenPersoon.Builder(null, null).build()));

        Mockito.when(onderhoudAfnemerindicatieBerichtFactory.maakBericht(any(), any(), any(), any(),
                eq(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE), any())).thenReturn(bericht);
        Mockito.when(datumService.parseDate(afnemerindicatie.getDatumEindeVolgen())).thenReturn(LocalDate.now());

        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(afnemerindicatieVerzoek);
        verzoekResultaat.setAutorisatiebundel(autorisatiebundel);
        verzoekResultaat.setPersoonslijst(persoonslijst);
        service.plaatsAfnemerindicatie(verzoekResultaat);

        Mockito.verify(plaatsAfnemerindicatieStap).voerStapUit(any());
        Mockito.verify(zetBerichtOpQueueService).plaatsQueueberichtVoorVerzoek(eq(bericht), eq(autorisatiebundel), eq(null));
    }

    @Test
    public void testGeenberichtBijGeenPersonen() throws StapException {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setDatumEindeVolgen("2001-01-01");
        afnemerindicatie.setDatumAanvangMaterielePeriode("2001-01-01");
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode("123");

        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(BasisBerichtGegevens.builder()
                        .metStuurgegevens()
                        .metTijdstipVerzending(nu)
                        .eindeStuurgegevens()
                        .build(), null, null);

        Mockito.when(onderhoudAfnemerindicatieBerichtFactory.maakBericht(any(), any(), any(), any(),
                eq(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE), any())).thenReturn(bericht);
        Mockito.when(datumService.parseDate(afnemerindicatie.getDatumEindeVolgen())).thenReturn(LocalDate.of(2001, 1, 1));

        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(afnemerindicatieVerzoek);
        verzoekResultaat.setAutorisatiebundel(autorisatiebundel);
        verzoekResultaat.setPersoonslijst(persoonslijst);

        service.plaatsAfnemerindicatie(verzoekResultaat);

        Mockito.verifyZeroInteractions(zetBerichtOpQueueService);
    }
}
