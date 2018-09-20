/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.LockStap;
import nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.PubliceerAdministratieveHandelingStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BerichtVerrijkingsStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BerichtVerwerkingStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingBerichtVerplichteObjectenValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingGegevensValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingObjectSleutelVerificatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenOpHaalStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenOpslagStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenSerialisatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingTransactieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.GedeblokkeerdeMeldingenOverschotControleStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.GedeblokkeerdeMeldingenValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.MaakNotificatieBerichtStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.NaBerichtRegelStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.VoorBerichtRegelStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.ZetNotificatieBerichtOpQueueStap;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.webservice.business.service.BerichtResultaatFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Unit test voor de bericht verwerker.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsBerichtVerwerkerTest {

    @InjectMocks
    private final BijhoudingsBerichtVerwerker berichtVerwerker = new BijhoudingsBerichtVerwerker();

    @Mock
    private BijhoudingBerichtVerplichteObjectenValidatieStap bijhoudingBerichtVerplichteObjectenValidatieStap;
    @Mock
    private PubliceerAdministratieveHandelingStap        publiceerAdministratieveHandelingStap;
    @Mock
    private VoorBerichtRegelStap                         voorBerichtRegelStap;
    @Mock
    private GedeblokkeerdeMeldingenValidatieStap         gedeblokkeerdeMeldingenValidatieStap;
    @Mock
    private BerichtVerrijkingsStap                       berichtVerrijkingsStap;
    @Mock
    private BijhoudingGegevensValidatieStap              bijhoudingGegevensValidatieStap;
    @Mock
    private BijhoudingObjectSleutelVerificatieStap       bijhoudingObjectSleutelVerificatieStap;
    @Mock
    private LockStap                                     lockStap;
    @Mock
    private BijhoudingTransactieStap                     bijhoudingTransactieStap;
    @Mock
    private BijhoudingRootObjectenOpHaalStap             bijhoudingRootObjectenOpHaalStap;
    @Mock
    private BerichtVerwerkingStap                        berichtVerwerkingStap;
    @Mock
    private MaakNotificatieBerichtStap                   maakNotificatieBerichtStap;
    @Mock
    private ZetNotificatieBerichtOpQueueStap             zetNotificatieBerichtOpQueueStap;
    @Mock
    private NaBerichtRegelStap                           naBerichtRegelStap;
    @Mock
    private GedeblokkeerdeMeldingenOverschotControleStap gedeblokkeerdeMeldingenOverschotControleStap;
    @Mock
    private BijhoudingRootObjectenOpslagStap             bijhoudingRootObjectenOpslagStap;
    @Mock
    private BijhoudingRootObjectenSerialisatieStap       bijhoudingRootObjectenSerialisatieStap;
    @Mock
    private DeblokkeerService                            deblokkeerService;


    @Mock
    private BerichtResultaatFactory berichtResultaatFactory;

    private BijhoudingBerichtContext berichtContext = bouwBerichtContext();

    @Before
    public void setUp() {
        when(deblokkeerService.deblokkeerResultaatMeldingen(any(BerichtBericht.class), any(BijhoudingBerichtContext.class), any(Resultaat.class)))
            .thenReturn(Resultaat.LEEG);
        when(bijhoudingBerichtVerplichteObjectenValidatieStap.voerStapUit(any(BijhoudingsBericht.class))).thenReturn(Resultaat.LEEG);
        when(voorBerichtRegelStap.voerStapUit(any(BijhoudingsBericht.class))).thenReturn(Resultaat.LEEG);
        when(gedeblokkeerdeMeldingenValidatieStap.voerStapUit(any(BijhoudingsBericht.class))).thenReturn(Resultaat.LEEG);
        when(berichtVerrijkingsStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(bijhoudingGegevensValidatieStap.voerStapUit(any(BijhoudingsBericht.class))).thenReturn(Resultaat.LEEG);
        when(bijhoudingObjectSleutelVerificatieStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class)))
            .thenReturn(Resultaat.LEEG);
        when(lockStap.vergrendel(any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(bijhoudingRootObjectenOpHaalStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(berichtVerwerkingStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(maakNotificatieBerichtStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(zetNotificatieBerichtOpQueueStap.voerStapUit(any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(naBerichtRegelStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(gedeblokkeerdeMeldingenOverschotControleStap.voerStapUit(any(BijhoudingsBericht.class))).thenReturn(Resultaat.LEEG);
        when(bijhoudingRootObjectenOpslagStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class))).thenReturn(Resultaat.LEEG);
        when(bijhoudingRootObjectenSerialisatieStap.voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class)))
            .thenReturn(Resultaat.LEEG);
        when(bijhoudingTransactieStap.stopTransactie(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class), anyBoolean())).thenReturn
            (Resultaat.LEEG);
    }

    @Test
    public void testVerwerkBerichtMoetStappenInJuisteVolgordeAanroepen() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        InOrder inOrder = inOrder(bijhoudingBerichtVerplichteObjectenValidatieStap, voorBerichtRegelStap, gedeblokkeerdeMeldingenValidatieStap,
            berichtVerrijkingsStap, bijhoudingGegevensValidatieStap, bijhoudingObjectSleutelVerificatieStap, lockStap, bijhoudingTransactieStap,
            bijhoudingRootObjectenOpHaalStap, berichtVerwerkingStap, maakNotificatieBerichtStap, zetNotificatieBerichtOpQueueStap, naBerichtRegelStap,
            gedeblokkeerdeMeldingenOverschotControleStap, bijhoudingRootObjectenOpslagStap, bijhoudingRootObjectenSerialisatieStap,
            publiceerAdministratieveHandelingStap);
        inOrder.verify(bijhoudingBerichtVerplichteObjectenValidatieStap)
            .voerStapUit(any(BijhoudingsBericht.class));
        inOrder.verify(voorBerichtRegelStap)
            .voerStapUit(any(BijhoudingsBericht.class));
        inOrder.verify(gedeblokkeerdeMeldingenValidatieStap)
            .voerStapUit(any(BijhoudingsBericht.class));
        inOrder.verify(berichtVerrijkingsStap).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(bijhoudingGegevensValidatieStap).voerStapUit(any(BijhoudingsBericht.class));
        inOrder.verify(bijhoudingObjectSleutelVerificatieStap).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(lockStap).vergrendel(any(BijhoudingBerichtContext.class));
        inOrder.verify(bijhoudingTransactieStap).startTransactie(any(BijhoudingBerichtContext.class));
        inOrder.verify(bijhoudingRootObjectenOpHaalStap)
            .voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(berichtVerwerkingStap)
            .voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(maakNotificatieBerichtStap)
            .voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(zetNotificatieBerichtOpQueueStap).voerStapUit(any(BijhoudingBerichtContext.class));
        inOrder.verify(naBerichtRegelStap).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(gedeblokkeerdeMeldingenOverschotControleStap).voerStapUit(any(BijhoudingsBericht.class));
        inOrder.verify(bijhoudingRootObjectenOpslagStap).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        inOrder.verify(bijhoudingRootObjectenSerialisatieStap).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));

        // voormalige nabewerkingstappen
        inOrder.verify(bijhoudingTransactieStap).stopTransactie(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class), anyBoolean());
        inOrder.verify(lockStap).ontgrendel();
        inOrder.verify(publiceerAdministratieveHandelingStap).voerUit(any(BijhoudingBerichtContext.class));
        inOrder.verifyNoMoreInteractions();

        // er zijn geen meldingen toegevoegd
        assertEquals(0, bijhoudingResultaat.getMeldingen().size());

    }

    @Test
    public void testVerwerkBerichtIndienExceptionBijLockStapVergrendel() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        // gegeven dat vergrendelen een exception oplevert
        doThrow(new NullPointerException("fout bij lock")).when(lockStap).vergrendel(any(BijhoudingBerichtContext.class));

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // er is een melding toegevoegd
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        // ga niet de transactiestap in
        verify(bijhoudingTransactieStap, never()).startTransactie(any(BijhoudingBerichtContext.class));
        // maar probeer wel te ontgrendelen
        verify(lockStap).ontgrendel();
    }

    @Test
    public void testVerwerkBerichtIndienExceptionBijLockStapOntgrendel() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        // gegeven dat ontgrendelen een exception oplevert
        doThrow(new NullPointerException("fout bij unlock")).when(lockStap).ontgrendel();

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // er is een melding toegevoegd
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        // maar probeer wel te ontgrendelen
        verify(publiceerAdministratieveHandelingStap).voerUit(any(BijhoudingBerichtContext.class));
    }

    @Test
    public void testVerwerkBerichtIndienExceptionBijTransactieStapStart() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        doThrow(new NullPointerException("fout bij starten transactie")).when(bijhoudingTransactieStap).startTransactie(any(BijhoudingBerichtContext
            .class));

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // er is een melding toegevoegd
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        verify(bijhoudingRootObjectenOpHaalStap, never()).voerStapUit(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));
        verify(bijhoudingTransactieStap).stopTransactie(any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class), anyBoolean());
        verify(lockStap).ontgrendel();
    }

    @Test
    public void testVerwerkBerichtIndienExceptionBijTransactieStapStopTransactie() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        doThrow(new NullPointerException("fout bij stoppen transactie")).when(bijhoudingTransactieStap).stopTransactie(
            any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class), anyBoolean());

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // er is een melding toegevoegd
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        verify(lockStap).ontgrendel();
    }

    @Test
    public void testVerwerkBerichtIndienErrorVoorTransactie() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        doThrow(new Error("error voor transactie")).when(voorBerichtRegelStap).voerStapUit(
            any(BijhoudingsBericht.class));

        try {
            berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);
            fail("moet hier niet komen");
        } catch (Error error) {
            // transactie niet gestart
            verify(bijhoudingTransactieStap, never()).startTransactie(any(BijhoudingBerichtContext.class));
        }
    }

    @Test
    public void testVerwerkBerichtIndienExceptionVoorTransactie() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        doThrow(new RuntimeException("exception voor transactie")).when(voorBerichtRegelStap).voerStapUit(
            any(BijhoudingsBericht.class));

        BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);
        // melding toegevoegd
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        // transactie niet gestart
        verify(bijhoudingTransactieStap, never()).startTransactie(any(BijhoudingBerichtContext.class));
    }

    @Test
    public void testVerwerkBerichtIndienErrorBinnenTransactie() {
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());

        doThrow(new Error("error binnen transactie")).when(berichtVerwerkingStap).voerStapUit(
            any(BijhoudingsBericht.class), any(BijhoudingBerichtContext.class));

        try {
            berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);
        } catch (Error error) {

        }
        verify(bijhoudingTransactieStap).stopTransactie(bijhoudingsBericht, berichtContext, true);
    }

    @Test
    public void testVerwerkBerichtIndienVerwerkingStoppendeFout() {

        // GIVEN
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());
        // dit is de eerste stap die wordt uitgevoerd; het resultaat gaat meteen naar 'verifieerVerwerkingStoppendeFouten'
        when(bijhoudingBerichtVerplichteObjectenValidatieStap.voerStapUit(any(BijhoudingsBericht.class)))
            .thenReturn(Resultaat.LEEG.voegToe(ResultaatMelding.builder().withReferentieID("abc").build()));

        // WHEN
        final BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // THEN
        // de tweede stap mag niet worden uitgevoerd
        verify(voorBerichtRegelStap, never()).voerStapUit(any(BijhoudingsBericht.class));
        // de fout moet ook in het bijhoudingResultaat komen
        assertTrue(bijhoudingResultaat.bevatStoppendeFouten());
        assertTrue(bijhoudingResultaat.bevatVerwerkingStoppendeFouten());
        assertFalse(bijhoudingResultaat.isSuccesvol());
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        assertEquals("abc", bijhoudingResultaat.getMeldingen().get(0).getReferentieID());
    }

    @Test
    public void testVerwerkBerichtIndienVerwerkingStoppendeFoutInTransactioneleStap() {
        // GIVEN
        final BijhoudingsBericht bijhoudingsBericht = maakNieuwStandaardBericht(null, maakStandaardActie());
        final Resultaat resultaat = Resultaat.LEEG.voegToe(ResultaatMelding.builder().withMeldingTekst("hupseflups").build());
        when(bijhoudingRootObjectenOpHaalStap.voerStapUit(bijhoudingsBericht, berichtContext)).thenReturn(resultaat);
        when(deblokkeerService.deblokkeerResultaatMeldingen(bijhoudingsBericht, berichtContext, resultaat))
            .thenThrow(new BijhoudingsBerichtVerwerker.VerwerkingStoppendeFoutException());

        // WHEN
        final BijhoudingResultaat bijhoudingResultaat = berichtVerwerker.verwerkBericht(bijhoudingsBericht, berichtContext);

        // THEN
        // de eerdere melding uit de stap moet in het resultaat zitten
        assertEquals(1, bijhoudingResultaat.getMeldingen().size());
        assertEquals("hupseflups", bijhoudingResultaat.getMeldingen().get(0).getMeldingTekst());
    }

    private ActieBericht maakStandaardActie() {
        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setRootObject(new PersoonBericht());
        return actie;

    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BijhoudingsBericht maakNieuwStandaardBericht(final List<GedeblokkeerdeMeldingBericht> overrules,
        final ActieBericht... acties)
    {
        final BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        final AdministratieveHandelingBericht admhnd = new HandelingErkenningNaGeboorteBericht();
        admhnd.setGedeblokkeerdeMeldingen(new ArrayList<AdministratieveHandelingGedeblokkeerdeMeldingBericht>());
        bericht.getStandaard().setAdministratieveHandeling(admhnd);

        if (overrules != null) {
            for (final GedeblokkeerdeMeldingBericht ovm : overrules) {
                final AdministratieveHandelingGedeblokkeerdeMeldingBericht adgmb =
                    new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
                adgmb.setGedeblokkeerdeMelding(ovm);
                bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen().add(adgmb);
            }
        }

        if (acties != null) {
            bericht.getStandaard().setAdministratieveHandeling(admhnd);
            bericht.getAdministratieveHandeling().setActies(Arrays.asList(acties));
        }

        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        return bericht;
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.stappen.BerichtContext} instantie, met ingevulde in-  en uitgaande bericht ids, een
     * authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    private BijhoudingBerichtContext bouwBerichtContext() {
        final BerichtenIds ids = new BerichtenIds(2L, 3L);
        return new BijhoudingBerichtContext(ids, Mockito.mock(Partij.class), "ref", null);
    }

}
