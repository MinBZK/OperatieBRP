/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.StatusHistorie;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.DoelBinding;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link ContextVerrijkingStap} class.
 */
public class ContextVerrijkingStapTest {

    @Mock
    private BerichtVerzoek<BerichtAntwoord> berichtVerzoek;
    @Mock
    private BerichtContext                  berichtContext;
    @Mock
    private BerichtAntwoord                 berichtAntwoord;
    @Mock
    private PartijRepository                partijRepository;
    @Mock
    private AbonnementRepository            abonnementRepository;
    private ContextVerrijkingStap           stap;
    private Partij                          partij;
    private Abonnement                      abonnement;

    /**
     * Test de verrijking van een bestaande partij en abonnement.
     */
    @Test
    public void testVerrijking() {
        ArgumentCaptor<Partij> argumentPartij = ArgumentCaptor.forClass(Partij.class);
        ArgumentCaptor<Abonnement> argumentAbonnement = ArgumentCaptor.forClass(Abonnement.class);
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findOne(4L)).thenReturn(abonnement);

        assertTrue(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));

        Mockito.verify(berichtAntwoord, Mockito.never()).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
        Mockito.verify(berichtContext).setPartij(argumentPartij.capture());
        Mockito.verify(berichtContext).setAbonnement(argumentAbonnement.capture());
        assertSame(partij, argumentPartij.getValue());
        assertSame(abonnement, argumentAbonnement.getValue());
    }

    /**
     * Test de verrijking van een NIET bestaande partij.
     */
    @Test
    public void testVerrijkingNietBestaandePartij() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(null);
        Mockito.when(abonnementRepository.findOne(4L)).thenReturn(abonnement);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtContext, Mockito.never()).setPartij(Matchers.any(Partij.class));
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test de verrijking van een NIET bestaand abonnement.
     */
    @Test
    public void testVerrijkingNietBestaandAbonnement() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findById(4L)).thenReturn(new ArrayList<Abonnement>());

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtContext, Mockito.never()).setAbonnement(Matchers.any(Abonnement.class));
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test de verrijking van een abonnement wat niet de status 'A' actueel heeft. Maar 'M' Materieel voorkomend
     * maar niet actueel.
     */
    @Test
    public void testVerrijkingNietActueelAbonnement() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findOne(4L)).thenReturn(abonnement);

        ReflectionTestUtils.setField(abonnement, "statusHistorie", StatusHistorie.M);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test verrijking van een abonnement wat niet behoort tot de partij.
     */
    @Test
    public void testVerrijkingAbonnementBehoortNietTotPartij() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findOne(4L)).thenReturn(abonnement);

        Mockito.when(berichtContext.getPartijId()).thenReturn(988L);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtAntwoord, Mockito.times(2)).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Methode voor het initialiseren van de mocks en de stap.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        partij = new Partij(SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(partij, "id", 3L);
        DoelBinding doelBinding =
            new DoelBinding(new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "Test", partij),
                    partij);
        abonnement = new Abonnement(doelBinding, SoortAbonnement.LEVERING);
        ReflectionTestUtils.setField(abonnement, "statusHistorie", StatusHistorie.A);

        Mockito.when(berichtContext.getPartijId()).thenReturn(3L);
        Mockito.when(berichtContext.getAbonnementId()).thenReturn(4L);

        stap = new ContextVerrijkingStap();
        ReflectionTestUtils.setField(stap, "partijRepository", partijRepository);
        ReflectionTestUtils.setField(stap, "abonnementRepository", abonnementRepository);
    }

}
