/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link OpvragenPersoonCommand} class.
 */
public class OpvragenPersoonCommandTest {

    @Mock
    private PersoonRepository           persoonRepositoryMock;
    private PersoonZoekCriteriaAntwoord antwoordMock;

    /**
     * Test de constructor en of de velden correct zijn geinitialiseerd.
     */
    @Test
    public final void testConstructorVeldInitialisatie() {
        BerichtContext context = getBrpBerichtContext();
        PersoonZoekCriteria criteria = new PersoonZoekCriteria();
        criteria.setBsn(5L);

        OpvragenPersoonCommand command = new OpvragenPersoonCommand(criteria, context);
        assertEquals(5, command.getVerzoek().getBsn().intValue());
        assertEquals(context, command.getContext());
    }

    /**
     * Test de "happy-flow" van de {@link OpvragenPersoonCommand#voerUit(PersoonZoekCriteriaAntwoord)} methode.
     */
    @Test
    public final void testStandaardVoerUit() {
        voerCommandUitMetBSN(123456789L);
        assertEquals("Test1", antwoordMock.getPersonen().iterator().next().getGeslachtsnaam());

        voerCommandUitMetBSN(234567890L);
        assertEquals("Test2", antwoordMock.getPersonen().iterator().next().getGeslachtsnaam());

        voerCommandUitMetBSN(345678901L);
        assertEquals(0, antwoordMock.getPersonen().size());

        voerCommandUitMetBSN(434567890L);
        assertEquals(2, antwoordMock.getPersonen().size());
    }

    /**
     * Unit test voor het opvragen van een persoon op basis van een leeg BSN.
     */
    @Test
    public final void testOpvragenPersoonOpBasisVanLeegBsn() {
        PersoonZoekCriteria criteria = new PersoonZoekCriteria();
        OpvragenPersoonCommand command = new OpvragenPersoonCommand(criteria, getBrpBerichtContext());
        ReflectionTestUtils.setField(command, "persoonRepository", persoonRepositoryMock);
        command.voerUit(antwoordMock);
        assertTrue(antwoordMock.getPersonen().size() == 0);
        assertEquals(0, antwoordMock.getFouten().size());
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        final long bsnPers1 = 123456789L;
        final long bsnPers2 = 234567890L;
        final long bsnPers3a = 434567890L;

        Persoon pers1 = new Persoon(SoortPersoon.INGESCHREVENE);
        pers1.setBurgerservicenummer(bsnPers1);
        pers1.setGeslachtsnaam("Test1");

        Persoon pers2 = new Persoon(SoortPersoon.INGESCHREVENE);
        pers2.setBurgerservicenummer(bsnPers2);
        pers2.setGeslachtsnaam("Test2");

        Persoon pers3a = new Persoon(SoortPersoon.INGESCHREVENE);
        pers3a.setBurgerservicenummer(bsnPers3a);
        pers3a.setGeslachtsnaam("Test3a");

        Persoon pers3b = new Persoon(SoortPersoon.INGESCHREVENE);
        pers3b.setBurgerservicenummer(bsnPers3a);
        pers3b.setGeslachtsnaam("Test3b");

        Mockito.when(persoonRepositoryMock.findByBurgerservicenummer(bsnPers1)).thenReturn(Arrays.asList(pers1));
        Mockito.when(persoonRepositoryMock.findByBurgerservicenummer(bsnPers2)).thenReturn(Arrays.asList(pers2));
        Mockito.when(persoonRepositoryMock.findByBurgerservicenummer(bsnPers3a)).thenReturn(
                Arrays.asList(pers3a, pers3b));

        antwoordMock = new PersoonZoekCriteriaAntwoord();
    }

    /**
     * Voert het OpvragenPersoon command uit met een bepaald bsn.
     *
     * @param bsn Het BSN waarmee het command wordt uitgevoerd.
     */
    public void voerCommandUitMetBSN(final long bsn) {
        final PersoonZoekCriteria criteria = new PersoonZoekCriteria();
        criteria.setBsn(bsn);
        OpvragenPersoonCommand command = new OpvragenPersoonCommand(criteria, getBrpBerichtContext());
        ReflectionTestUtils.setField(command, "persoonRepository", persoonRepositoryMock);
        command.voerUit(antwoordMock);
    }

    /**
     * Retourneert een standaard bericht context voor testen.
     *
     * @return een standaard bericht context.
     */
    private BerichtContext getBrpBerichtContext() {
        return new BerichtContext();
    }
}
