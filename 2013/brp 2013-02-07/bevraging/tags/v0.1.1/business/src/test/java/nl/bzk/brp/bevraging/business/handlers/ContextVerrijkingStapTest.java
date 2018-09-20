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
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
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
    private BrpBerichtCommand     berichtCommand;
    @Mock
    private BrpBerichtContext     berichtContext;
    @Mock
    private PartijRepository      partijRepository;
    @Mock
    private AbonnementRepository  abonnementRepository;
    private ContextVerrijkingStap stap;
    private Partij                partij;
    private List<Abonnement>      abonnementen;

    /**
     * Test de verrijking van een bestaande partij en abonnement.
     */
    @Test
    public void testVerrijking() {
        ArgumentCaptor<Partij> argumentPartij = ArgumentCaptor.forClass(Partij.class);
        ArgumentCaptor<Abonnement> argumentAbonnement = ArgumentCaptor.forClass(Abonnement.class);
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findByIdAndDoelBindingGeautoriseerdeId(4L, 3L)).thenReturn(abonnementen);

        assertTrue(stap.voerVerwerkingsStapUitVoorBericht(berichtCommand));

        Mockito.verify(berichtCommand, Mockito.never()).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
        Mockito.verify(berichtContext).setPartij(argumentPartij.capture());
        Mockito.verify(berichtContext).setAbonnement(argumentAbonnement.capture());
        assertSame(partij, argumentPartij.getValue());
        assertSame(abonnementen.get(0), argumentAbonnement.getValue());
    }

    /**
     * Test de verrijking van een NIET bestaande partij.
     */
    @Test
    public void testVerrijkingNietBestaandePartij() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(null);
        Mockito.when(abonnementRepository.findByIdAndDoelBindingGeautoriseerdeId(4L, 3L)).thenReturn(abonnementen);

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtCommand));
        Mockito.verify(berichtContext, Mockito.never()).setPartij(Matchers.any(Partij.class));
        Mockito.verify(berichtCommand).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Test de verrijking van een NIET bestaand abonnement.
     */
    @Test
    public void testVerrijkingNietBestaandAbonnement() {
        Mockito.when(partijRepository.findOne(3L)).thenReturn(partij);
        Mockito.when(abonnementRepository.findByIdAndDoelBindingGeautoriseerdeId(4L, 3L)).thenReturn(
                new ArrayList<Abonnement>());

        assertFalse(stap.voerVerwerkingsStapUitVoorBericht(berichtCommand));
        Mockito.verify(berichtContext, Mockito.never()).setAbonnement(Matchers.any(Abonnement.class));
        Mockito.verify(berichtCommand).voegFoutToe(Matchers.any(BerichtVerwerkingsFout.class));
    }

    /**
     * Methode voor het initialiseren van de mocks en de stap.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(berichtCommand.getContext()).thenReturn(berichtContext);

        partij = new Partij(SoortPartij.GEMEENTE);
        abonnementen =
            Arrays.asList(new Abonnement(new DoelBinding(new AutorisatieBesluit(
                    SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "Test", partij), partij), SoortAbonnement.LEVERING));

        Mockito.when(berichtContext.getPartijId()).thenReturn(3L);
        Mockito.when(berichtContext.getAbonnementId()).thenReturn(4L);

        stap = new ContextVerrijkingStap();
        ReflectionTestUtils.setField(stap, "partijRepository", partijRepository);
        ReflectionTestUtils.setField(stap, "abonnementRepository", abonnementRepository);
    }

}
