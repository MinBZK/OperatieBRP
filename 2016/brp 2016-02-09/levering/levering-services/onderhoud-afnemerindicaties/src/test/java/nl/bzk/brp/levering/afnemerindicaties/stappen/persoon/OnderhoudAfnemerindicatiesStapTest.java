/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.intThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesMetRegelsService;
import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.internbericht.RegelMelding;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatiesStapTest extends AbstractStappenTest {

    private static final String ZENDENDE_SYSTEEM_NAAM = "TestSysteem";

    @Mock
    private AfnemerindicatiesMetRegelsService afnemerindicatiesService;

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @InjectMocks
    private OnderhoudAfnemerindicatiesStap stap = new OnderhoudAfnemerindicatiesStap();




    @Test
    public final void testVoerStapUit() throws BrpLockerExceptie {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        getBerichtContext().setLeveringinformatie(leveringinformatie);

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));

        assertThat(getResultaat().isFoutief(), is(false));

        verify(afnemerindicatiesService).plaatsAfnemerindicatie(eq(toegangLeveringsautorisatie),
            intThat(is(not(0))), intThat(is(not(0))), any(DatumEvtDeelsOnbekendAttribuut.class), any(DatumAttribuut.class));
    }

    @Test
    public final void testAfnemerindicatieServiceGooitFout() throws BrpLockerExceptie {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);


        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        getBerichtContext().setLeveringinformatie(leveringinformatie);

        when(afnemerindicatiesService.plaatsAfnemerindicatie(
                eq(toegangLeveringsautorisatie), intThat(is(not(0))), intThat(is(not(0))), any
                        (DatumEvtDeelsOnbekendAttribuut
                                .class),
                any(DatumAttribuut.class))).thenThrow(BrpLockerExceptie.class);

        // act
        final boolean stapResultaat =
            stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertFalse(stapResultaat);
        assertThat(getResultaat().isFoutief(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAfnemerindicatieServiceGeeftMeldingen() throws BrpLockerExceptie {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.zonderGeldigeDienst();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        getBerichtContext().setLeveringinformatie(leveringinformatie);

        final BewerkAfnemerindicatieResultaat bewerkAfnemerindicatieResultaat = new BewerkAfnemerindicatieResultaat();
        bewerkAfnemerindicatieResultaat.getMeldingen().add(new RegelMelding(new RegelAttribuut(Regel.BRAL0001),
            new SoortMeldingAttribuut(
                SoortMelding.FOUT), new MeldingtekstAttribuut(Regel.BRAL0001.getOmschrijving())));
        when(afnemerindicatiesService
            .verwijderAfnemerindicatie(eq(toegangLeveringsautorisatie), intThat(is(not
                    (0))),
                intThat(is(not(0))))).thenReturn(bewerkAfnemerindicatieResultaat);


        // act
        stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
    }


    @Test
    public final void testVoerStapUitGeenPersonen() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(123).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final RegistreerAfnemerindicatieBericht onderwerp = getOnderwerp();
        onderwerp.getAdministratieveHandeling().setActies(Collections.<ActieBericht>emptyList());

        // act
        final boolean stapResultaat =
            stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().isFoutief(), is(true));
    }

    @Test
    public final void testVoerStapUitGeenAfnemerindicatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(123).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);
        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final RegistreerAfnemerindicatieBericht onderwerp = getOnderwerp();
        final PersoonBericht persoon = (PersoonBericht) onderwerp.getAdministratieveHandeling().getActies().get(0).getRootObject();
        persoon.setAfnemerindicaties(new ArrayList<PersoonAfnemerindicatieBericht>());

        // act
        final boolean stapResultaat =
            stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertFalse(stapResultaat);
        assertThat(getResultaat().isFoutief(), is(true));
    }

    @Test
    public final void onderhoudEnkelOpEigenPartij() throws BrpLockerExceptie {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
                new DatumEvtDeelsOnbekendAttribuut(20130101));

        //LET OP, afwijkende partij, dit mag niet
        getOnderwerp().getAdministratieveHandeling().setPartijCode("45566");


        getBerichtContext().setLeveringinformatie(leveringinformatie);

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().getMeldingen().get(0).getRegel(), is(Regel.R2061));
    }

    @Test
    public final void onderhoudEnkelOpEigenPartij2() throws BrpLockerExceptie {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        maakBericht(12348945, leveringinformatie, 31223, ZENDENDE_SYSTEEM_NAAM,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
                new DatumEvtDeelsOnbekendAttribuut(20130101));

        //LET OP, afwijkende partij, dit mag niet
        ((PersoonBericht)getOnderwerp().getAdministratieveHandeling().getActies().get(0).getRootObject()).getAfnemerindicaties().get(0).setAfnemerCode("45566");

        getBerichtContext().setLeveringinformatie(leveringinformatie);

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().getMeldingen().get(0).getRegel(), is(Regel.R2061));
    }
}
