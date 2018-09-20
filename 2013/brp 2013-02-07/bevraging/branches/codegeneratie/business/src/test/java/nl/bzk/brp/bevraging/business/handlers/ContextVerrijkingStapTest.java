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
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;
import nl.bzk.brp.domein.lev.persistent.PersistentAbonnement;

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
    private DomeinObjectFactory             factory = new PersistentDomeinObjectFactory();

    /**
     * Test de verrijking van een bestaande partij en abonnement.
     */
    @Test
    public void testVerrijking() {
        ArgumentCaptor<PersistentPartij> argumentPartij = ArgumentCaptor.forClass(PersistentPartij.class);
        ArgumentCaptor<PersistentAbonnement> argumentAbonnement = ArgumentCaptor.forClass(PersistentAbonnement.class);
        Mockito.when(partijRepository.findOne(3)).thenReturn((PersistentPartij) partij);
        Mockito.when(abonnementRepository.findOne(4)).thenReturn((PersistentAbonnement) abonnement);

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
        Mockito.when(partijRepository.findOne(3)).thenReturn(null);
        Mockito.when(abonnementRepository.findOne(4)).thenReturn((PersistentAbonnement) abonnement);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtContext, Mockito.never()).setPartij(Matchers.any(Partij.class));
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test de verrijking van een NIET bestaand abonnement.
     */
    @Test
    public void testVerrijkingNietBestaandAbonnement() {
        Mockito.when(partijRepository.findOne(3)).thenReturn((PersistentPartij) partij);
        Mockito.when(abonnementRepository.findById(4)).thenReturn(new ArrayList<Abonnement>());

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
        Mockito.when(partijRepository.findOne(3)).thenReturn((PersistentPartij) partij);
        Mockito.when(abonnementRepository.findOne(4)).thenReturn((PersistentAbonnement) abonnement);

        ReflectionTestUtils.setField(abonnement, "abonnementStatusHis", "M");

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtAntwoord).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test verrijking van een abonnement wat niet behoort tot de partij.
     */
    @Test
    public void testVerrijkingAbonnementBehoortNietTotPartij() {
        Mockito.when(partijRepository.findOne(3)).thenReturn((PersistentPartij) partij);
        Mockito.when(abonnementRepository.findOne(4)).thenReturn((PersistentAbonnement) abonnement);

        Mockito.when(berichtContext.getPartijId()).thenReturn(988);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtVerzoek, berichtContext, berichtAntwoord));
        Mockito.verify(berichtAntwoord, Mockito.times(2)).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Methode voor het initialiseren van de mocks en de stap.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        partij = factory.createPartij();
        partij.setSoort(SoortPartij.GEMEENTE);
        partij.setID(3);
        Autorisatiebesluit autorisatiebesluit = factory.createAutorisatiebesluit();
        autorisatiebesluit.setSoort(SoortAutorisatiebesluit.LEVERINGSAUTORISATIE);
        autorisatiebesluit.setBesluittekst("Test");
        autorisatiebesluit.setAutoriseerder(partij);
        Doelbinding doelBinding = factory.createDoelbinding();
        doelBinding.setLeveringsautorisatiebesluit(autorisatiebesluit);
        doelBinding.setGeautoriseerde(partij);
        abonnement = factory.createAbonnement();
        abonnement.setDoelbinding(doelBinding);
        abonnement.setSoortAbonnement(SoortAbonnement.LEVERING);
        ReflectionTestUtils.setField(abonnement, "abonnementStatusHis", "A");

        Mockito.when(berichtContext.getPartijId()).thenReturn(3);
        Mockito.when(berichtContext.getAbonnementId()).thenReturn(4);

        stap = new ContextVerrijkingStap();
        ReflectionTestUtils.setField(stap, "partijRepository", partijRepository);
        ReflectionTestUtils.setField(stap, "abonnementRepository", abonnementRepository);
    }

}
