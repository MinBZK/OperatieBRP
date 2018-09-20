/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


/**
 * Unit test voor de {@link StandaardBerichtUitvoerderService} class.
 */
public class StandaardBerichtUitvoerderServiceTest {

    @Mock
    private BrpBerichtCommand                 berichtCommand;
    @Mock
    private BrpBerichtContext                 berichtContext;
    @Mock
    private BRPVerzoek                        verzoek;

    private StandaardBerichtUitvoerderService service;

    /**
     * Test dat er geen excepties optreden als er geen stappen zijn geconfigureerd.
     */
    @Test
    public void testBerichtUitVoerZonderStappen() {
        service.voerBerichtUit(berichtCommand);
    }

    /**
     * Test dat er een exceptie wordt gegooid indien het bericht {@code null} is.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLeegBericht() {
        service.setStappen(getStappen(true));
        service.voerBerichtUit(null);
    }

    /**
     * Test een incorrect geinitialiseerd bericht waarvan de context leeg is.
     */
    @Test
    public void testIncorrectGeinitialiseerdBerichtGeenContext() {
        service.setStappen(getStappen(true));

        Mockito.when(berichtCommand.getContext()).thenReturn(null);
        verifieerAfhandelingIncorrectGeinitialiseerdBericht();
    }

    /**
     * Test een incorrect geinitialiseerd bericht waarvan het verzoek leeg is.
     */
    @Test
    public void testIncorrectGeinitialiseerdBerichtGeenVerzoek() {
        service.setStappen(getStappen(true));

        Mockito.when(berichtCommand.getVerzoek()).thenReturn(null);
        verifieerAfhandelingIncorrectGeinitialiseerdBericht();
    }

    /**
     * Test een incorrect geinitialiseerd bericht waarvan de partij id is.
     */
    @Test
    public void testIncorrectGeinitialiseerdBerichtGeenPartijId() {
        service.setStappen(getStappen(true));

        Mockito.when(berichtContext.getPartijId()).thenReturn(null);
        verifieerAfhandelingIncorrectGeinitialiseerdBericht();
    }

    /**
     * Test een incorrect geinitialiseerd bericht waarvan de bericht id is.
     */
    @Test
    public void testIncorrectGeinitialiseerdBerichtGeenBerichtId() {
        service.setStappen(getStappen(true));

        Mockito.when(berichtContext.getBerichtId()).thenReturn(null);
        verifieerAfhandelingIncorrectGeinitialiseerdBericht();
    }

    /**
     *
     */
    private void verifieerAfhandelingIncorrectGeinitialiseerdBericht() {
        service.voerBerichtUit(berichtCommand);
        Mockito.verify(berichtCommand).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
        assertEquals(0, ((DummyBerichtVerwerkingsStap) service.getStappen().get(0)).voerUitAangeroepenAantal);
    }

    /**
     * Test dat er geen excepties optreden als er een enkele succesvolle stap is geconfigureerd en dat de methodes
     * op de stap maar eenmalig zijn aangeroepen.
     */
    @Test
    public void testEnkeleSuccesvolleStap() {
        service.setStappen(getStappen(true));

        service.voerBerichtUit(berichtCommand);
        assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(0)).voerUitAangeroepenAantal);
        assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(0)).naVerwerkingAangeroepenAantal);
    }

    /**
     * Test dat er geen excepties optreden als er een enkele falende stap is geconfigureerd en dat de methodes
     * op de stap beide maar eenmalig zijn aangeroepen.
     */
    @Test
    public void testEnkeleFalendeStap() {
        service.setStappen(getStappen(false));

        service.voerBerichtUit(berichtCommand);
        assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(0)).voerUitAangeroepenAantal);
        assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(0)).naVerwerkingAangeroepenAantal);
    }

    /**
     * Test dat er geen excepties optreden als er meerdere succesvolle stappen zijn geconfigureerd en dat de methodes
     * op alle stappen maar eenmalig zijn aangeroepen.
     */
    @Test
    public void testMeerdereSuccesvolleStappen() {
        service.setStappen(getStappen(true, true, true));

        service.voerBerichtUit(berichtCommand);
        for (BerichtVerwerkingsStap stap : service.getStappen()) {
            assertEquals(1, ((DummyBerichtVerwerkingsStap) stap).voerUitAangeroepenAantal);
            assertEquals(1, ((DummyBerichtVerwerkingsStap) stap).naVerwerkingAangeroepenAantal);
        }
    }

    /**
     * Test dat er geen excepties optreden als er meerdere succesvolle, en een falende stap zijn geconfigureerd en dat
     * de methodes op alle stappen tot en met de falende stap maar eenmalig zijn aangeroepen, en de methodes op de
     * stappen na de falende stap niet zijn aangeroepen.
     */
    @Test
    public void testSuccesVolleEnFalendeStappen() {
        service.setStappen(getStappen(true, true, false, true));

        service.voerBerichtUit(berichtCommand);

        // Test de stappen tot en met de falende stap
        for (int i = 0; i < 3; i++) {
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).voerUitAangeroepenAantal);
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).naVerwerkingAangeroepenAantal);
        }
        // En test de stap na de falende stap dat die niet is aangeroepen
        assertEquals(0, ((DummyBerichtVerwerkingsStap) service.getStappen().get(3)).voerUitAangeroepenAantal);
        assertEquals(0, ((DummyBerichtVerwerkingsStap) service.getStappen().get(3)).naVerwerkingAangeroepenAantal);
    }

    /**
     * Test dat als er een exceptie in de uitvoer van een stap optreed, er een fout wordt toegevoegd aan het bericht
     * en dat de verwerking stopt (de opvolgende stappen dienen dus niet meer uitgevoerd te zijn).
     */
    @Test
    public void testExceptieInStappenUitvoer() {
        service.setStappen(getStappen(true, true, null, true, null, true));

        service.voerBerichtUit(berichtCommand);

        // Test dat alleen de eerste drie stappen zijn uitgevoerd en de overige drie niet
        for (int i = 0; i < 3; i++) {
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).voerUitAangeroepenAantal);
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).naVerwerkingAangeroepenAantal);
        }
        for (int i = 3; i < 6; i++) {
            assertEquals(0, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).voerUitAangeroepenAantal);
            assertEquals(0, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).naVerwerkingAangeroepenAantal);
        }
        Mockito.verify(berichtCommand).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test dat als er een exceptie in de naverwerking van een stap optreed, er een fout wordt toegevoegd aan het
     * bericht en dat de naverwerking van de overige stappen gewoon doorgaat (de naverwerking van de opvolgende stappen
     * dienen dus niet ook uitgevoerd te zijn).
     */
    @Test
    public void testExceptieInNaverwerkingStappen() {
        service.setStappen(getStappenMetExceptieInNaverwerking(false, false, true, false, true));

        service.voerBerichtUit(berichtCommand);

        // Test dat alle stappen zijn uitgevoerd en alle naverwerkingsstappen.
        for (int i = 0; i < 5; i++) {
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).voerUitAangeroepenAantal);
            assertEquals(1, ((DummyBerichtVerwerkingsStap) service.getStappen().get(i)).naVerwerkingAangeroepenAantal);
        }
        Mockito.verify(berichtCommand, Mockito.times(2)).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Methode voor het testen van de getter en setter van de stappen.
     */
    @Test
    public void testSetEnGetStappen() {
        assertNull(service.getStappen());

        service.setStappen(getStappen(true, true, false));
        assertNotNull(service.getStappen());
        assertEquals(3, service.getStappen().size());
    }

    /**
     * Initialiseert de mocks die worden gebruikt in deze test en instantieert een nieuwe service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(berichtCommand.getContext()).thenReturn(berichtContext);
        Mockito.when(berichtContext.getPartijId()).thenReturn(1L);
        Mockito.when(berichtContext.getBerichtId()).thenReturn(1L);
        Mockito.when(berichtCommand.getVerzoek()).thenReturn(verzoek);

        service = new StandaardBerichtUitvoerderService();
    }

    /**
     * Bouwt een lijst van stappen op met de opgegeven resultaten, waarbij een nullpointer
     * wordt omgezet in een stap die een exceptie gooit.
     *
     * @param verwerkingsResultaten de resultaten die de stappen (in volgorde) moeten retourneren.
     * @return een lijst van stappen.
     */
    private List<BerichtVerwerkingsStap> getStappen(final Boolean... verwerkingsResultaten) {
        List<BerichtVerwerkingsStap> stappen = new ArrayList<BerichtVerwerkingsStap>();
        for (Boolean verwerkingOk : verwerkingsResultaten) {
            stappen.add(new DummyBerichtVerwerkingsStap(verwerkingOk));
        }
        return stappen;
    }

    /**
     * Bouwt een lijst van stappen op, waarbij de stappen altijd succesvol zijn, alleen de naverwerking
     * mogelijk een exceptie gooit, afhankelijk van de opgegeven {@code exceptieInNaverwerking} indicaties.
     *
     * @param exceptieInNaverwerking indicatie of een stap een exceptie gooit in de naverwerking of niet.
     * @return een lijst van stappen.
     */
    private List<BerichtVerwerkingsStap> getStappenMetExceptieInNaverwerking(final Boolean... exceptieInNaverwerking) {
        List<BerichtVerwerkingsStap> stappen = new ArrayList<BerichtVerwerkingsStap>();
        for (Boolean exceptieNaverwerking : exceptieInNaverwerking) {
            stappen.add(new DummyBerichtVerwerkingsStap(true, exceptieNaverwerking));
        }
        return stappen;
    }

    /**
     * Dummy implementatie van de {@link BerichtVerwerkingsStap} interface die een standaard te configureren
     * resultaat retourneert bij uitvoer en tevens een teller bijhoudt hoevaak de verschillende methodes op
     * de stap worden aangeroepen.
     */
    private final class DummyBerichtVerwerkingsStap implements BerichtVerwerkingsStap {

        private final Boolean resultaat;
        private final Boolean exceptieInNaVerwerking;
        private int           voerUitAangeroepenAantal      = 0;
        private int           naVerwerkingAangeroepenAantal = 0;

        /**
         * Standaard constructor die het te retourneren resultaat zet.
         *
         * @param resultaat het resultaat dat geretourneerd wordt bij uitvoer van deze stap.
         */
        private DummyBerichtVerwerkingsStap(final Boolean resultaat) {
            this(resultaat, false);
        }

        /**
         * Standaard constructor die het te retourneren resultaat zet en of er een exceptie
         * dient op te treden in de naverwerking.
         *
         * @param resultaat het resultaat dat geretourneerd wordt bij uitvoer van deze stap.
         * @param exceptieInNaverwerking of er een exceptie optreedt in de naverwerking.
         */
        private DummyBerichtVerwerkingsStap(final Boolean resultaat, final Boolean exceptieInNaverwerking) {
            this.resultaat = resultaat;
            exceptieInNaVerwerking = exceptieInNaverwerking;
        }

        @Override
        public boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
            voerUitAangeroepenAantal++;
            if (resultaat == null) {
                throw new NullPointerException();
            }
            return resultaat;
        }

        @Override
        public void naVerwerkingsStapVoorBericht(final BrpBerichtCommand bericht) {
            naVerwerkingAangeroepenAantal++;
            if (exceptieInNaVerwerking) {
                throw new NullPointerException();
            }
        }

    }
}
