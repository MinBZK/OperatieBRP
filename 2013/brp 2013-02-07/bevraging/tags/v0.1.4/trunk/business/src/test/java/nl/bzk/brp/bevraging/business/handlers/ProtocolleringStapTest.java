/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.ber.LeveringBericht;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringBerichtRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link ProtocolleringStap} class.
 */
public class ProtocolleringStapTest {

    @Mock
    private LeveringRepository            leveringRepositoryMock;

    @Mock
    private AbonnementRepository          abonnementRepositoryMock;

    @Mock
    private AuthenticatieMiddelRepository authenticatieMiddelRepositoryMock;

    @Mock
    private LeveringPersoonRepository     leveringPersoonRepositoryMock;

    @Mock
    private LeveringBerichtRepository     leveringBerichtRepositoryMock;

    private ProtocolleringStap            protocolleringStap;

    /**
     * Test de standaar stap uitvoer voor de protocollering stap.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {
        Calendar now = Calendar.getInstance();

        boolean resultaat =
            protocolleringStap.voerVerwerkingsStapUitVoorBericht(getBrpBerichtCommandMock(3L, 2L, now, 3L, 5L));

        ArgumentCaptor<Levering> levering = ArgumentCaptor.forClass(Levering.class);

        Mockito.verify(abonnementRepositoryMock).findOne(4L);
        Mockito.verify(authenticatieMiddelRepositoryMock).findOne(5L);
        Mockito.verify(leveringRepositoryMock).save(levering.capture());
        Mockito.verify(leveringPersoonRepositoryMock, Mockito.times(2)).save(Matchers.isA(LeveringPersoon.class));
        Mockito.verify(leveringBerichtRepositoryMock).save(Matchers.isA(LeveringBericht.class));

        assertTrue(resultaat);
        assertEquals(now, levering.getValue().getBeschouwing());
    }

    /**
     * Genereert een {@link BrpBerichtCommand} instantie die de opgegeven waardes retourneert indien gevraagd.
     *
     * @param berichtId het id van het bericht; afkomstig uit de context.
     * @param partijId het id van de partij; afkomstig uit de context.
     * @param beschouwingsMoment beschouwing moment zoals gevraagd door de partij/afnemer.
     * @param persoonIds ids van de personen die geretourneerd dienen te worden.
     * @return een nieuw (dummy) {@link BrpBerichtCommand} instantie.
     */
    private BrpBerichtCommand getBrpBerichtCommandMock(final Long berichtId, final Long partijId,
            final Calendar beschouwingsMoment, final Long... persoonIds)
    {
        BrpBerichtCommand commandMock = Mockito.mock(BrpBerichtCommand.class);

        BrpBerichtContext context = new BrpBerichtContext();
        context.setPartijId(partijId);
        context.setBerichtId(berichtId);
        context.setAbonnementId(4L);
        context.setAuthenticatieMiddelId(5L);

        // Verzoek instantie die altijd het beschouwingsmoment retourneert.
        BRPVerzoek verzoek = new BRPVerzoek() {

            @Override
            public Calendar getBeschouwing() {
                return beschouwingsMoment;
            }

            @Override
            public Collection<Long> getReadBSNLocks() {
                return Arrays.asList(persoonIds);
            }

            @Override
            public Collection<Long> getWriteBSNLocks() {
                // TODO Auto-generated method stub
                return null;
            }

        };

        // Antwoord instantie die een collectie van personen retourneert op basis van opgegevens ids.
        BRPAntwoord antwoord = new BRPAntwoord() {

            @Override
            public Collection<Persoon> getPersonen() {
                Set<Persoon> personen = new HashSet<Persoon>();
                for (Long id : persoonIds) {
                    Persoon pers = new Persoon(SoortPersoon.INGESCHREVENE);
                    ReflectionTestUtils.setField(pers, "id", id);
                    personen.add(pers);
                }
                return personen;
            }

        };

        Mockito.when(commandMock.getContext()).thenReturn(context);
        Mockito.when(commandMock.getVerzoek()).thenReturn(verzoek);
        Mockito.when(commandMock.getAntwoord()).thenReturn(antwoord);
        return commandMock;
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnStap() {
        MockitoAnnotations.initMocks(this);

        protocolleringStap = new ProtocolleringStap();
        ReflectionTestUtils.setField(protocolleringStap, "leveringRepository", leveringRepositoryMock);
        ReflectionTestUtils.setField(protocolleringStap, "authenticatieMiddelRepository",
                authenticatieMiddelRepositoryMock);
        ReflectionTestUtils.setField(protocolleringStap, "abonnementRepository", abonnementRepositoryMock);
        ReflectionTestUtils.setField(protocolleringStap, "leveringPersoonRepository", leveringPersoonRepositoryMock);
        ReflectionTestUtils.setField(protocolleringStap, "leveringBerichtRepository", leveringBerichtRepositoryMock);
    }

}
