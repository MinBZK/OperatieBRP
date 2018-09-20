/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.AbstractBerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
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
    private LeveringCommunicatieRepository leveringCommunicatieRepositoryMock;

    private ProtocolleringStap            protocolleringStap;

    /**
     * Test de standaard stap uitvoer voor de protocollering stap.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {
        Calendar now = Calendar.getInstance();

        boolean resultaat =
            protocolleringStap.voerVerwerkingsStapUitVoorBericht(getVerzoek(now, 3L, 5L), getContext(3L, 2L),
                    getAntwoord(3L, 5L));

        ArgumentCaptor<Levering> levering = ArgumentCaptor.forClass(Levering.class);

        Mockito.verify(abonnementRepositoryMock).findOne(4L);
        Mockito.verify(authenticatieMiddelRepositoryMock).findOne(5L);
        Mockito.verify(leveringRepositoryMock).save(levering.capture());
        Mockito.verify(leveringPersoonRepositoryMock, Mockito.times(2)).save(Matchers.isA(LeveringPersoon.class));

        assertTrue(resultaat);
        assertEquals(now, levering.getValue().getBeschouwing());
    }

    /**
     * Test de uitvoer van de protocollering stap waarin een exceptie optreedt.
     */
    @Test
    public void testExceptieInProtocollering() {
        Calendar now = Calendar.getInstance();
        Mockito.when(leveringRepositoryMock.save(Matchers.any(Levering.class))).thenThrow(new RuntimeException());

        BerichtAntwoord antwoord = getAntwoord(3L, 5L);
        assertEquals(0, antwoord.getFouten().size());

        boolean resultaat =
            protocolleringStap.voerVerwerkingsStapUitVoorBericht(getVerzoek(now, 3L, 5L), getContext(3L, 2L), antwoord);
        assertFalse(resultaat);
        assertEquals(1, antwoord.getFouten().size());
        assertEquals(BerichtVerwerkingsFoutCode.BRVE0008_01_PROTOCOLLERING_MISLUKT.getCode(),
                antwoord.getFouten().get(0).getCode());
    }

    /**
     * Genereert een {@link BerichtVerzoek} instantie die de opgegeven waardes retourneert indien gevraagd.
     *
     * @param beschouwingsMoment beschouwing moment zoals gevraagd door de partij/afnemer.
     * @param persoonIds ids van de personen die geretourneerd dienen te worden.
     * @return een nieuw (dummy) {@link BerichtVerzoek} instantie.
     */
    private <T extends BerichtAntwoord> BerichtVerzoek<T> getVerzoek(final Calendar beschouwingsMoment,
            final Long... persoonIds)
    {
        BerichtVerzoek<T> verzoek = new BerichtVerzoek<T>() {

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
                return null;
            }

            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }

            @Override
            public Class<T> getAntwoordClass() {
                return null;
            }

        };

        return verzoek;
    }

    /**
     * Genereert een {@link BerichtContext} instantie die de opgegeven waardes retourneert indien gevraagd.
     *
     * @param berichtId het id van het bericht.
     * @param partijId het id van de partij.
     * @return een nieuw (dummy) {@link BerichtContext} instantie.
     */
    private BerichtContext getContext(final Long berichtId, final Long partijId) {
        BerichtContext context = new BerichtContext();
        context.setPartijId(partijId);
        context.setIngaandBerichtId(berichtId);
        context.setAbonnementId(4L);
        context.setAuthenticatieMiddelId(5L);
        return context;
    }

    /**
     * Genereert een {@link BerichtAntwoord} instantie die de opgegeven waardes retourneert indien gevraagd.
     *
     * @param persoonIds ids van de personen die geretourneerd dienen te worden.
     * @return een nieuw (dummy) {@link BerichtAntwoord} instantie.
     */
    private BerichtAntwoord getAntwoord(final Long... persoonIds) {
        BerichtAntwoord antwoord = new AbstractBerichtAntwoord() {

            @Override
            public Collection<Persoon> getPersonen() {
                Set<Persoon> personen = new HashSet<Persoon>();
                for (Long id : persoonIds) {
                    nl.bzk.brp.bevraging.domein.Persoon pers = new nl.bzk.brp.bevraging.domein.Persoon(SoortPersoon.INGESCHREVENE);
                    ReflectionTestUtils.setField(pers, "id", id);
                    personen.add(pers);
                }
                return personen;
            }

            @Override
            public void wisContent() {

            }

        };

        antwoord.setLeveringId(2L);
        return antwoord;
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
        ReflectionTestUtils.setField(protocolleringStap, "leveringCommunicatieRepository", leveringCommunicatieRepositoryMock);
    }

}
