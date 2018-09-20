/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.ActieDAO;
import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonDAO;
import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonsNationaliteitDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Bron;
import nl.bzk.brp.poc.berichtenverwerker.model.Nation;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;
import nl.bzk.brp.poc.berichtenverwerker.service.PersoonEnNationaliteitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@class PersoonEnNationaliteitServiceImpl} class en de in deze class
 * geimplementeerde functies.
 */
public class PersoonEnNationaliteitServiceImplTest {

    @Mock
    private PersoonDAO                    persoonDAO;
    @Mock
    private PersoonsNationaliteitDAO      persoonsNationaliteitDAO;
    @Mock
    private ActieDAO                      actieDAO;

    private PersoonEnNationaliteitService service;

    /**
     * Initializeert de test instances door de Mocks die in deze unit test class worden gebruikt te initialiseren
     * en de service die getest wordt op zijn beurt te initialiseren met deze mocks.
     */
    @Before
    public final void initTests() {
        MockitoAnnotations.initMocks(this);

        service = new PersoonEnNationaliteitServiceImpl();
        ReflectionTestUtils.setField(service, "actieDAO", actieDAO);
        ReflectionTestUtils.setField(service, "persoonDAO", persoonDAO);
        ReflectionTestUtils.setField(service, "persoonsNationaliteitDAO", persoonsNationaliteitDAO);
    }

    /**
     * Unit test voor de {@link PersoonEnNationaliteitServiceImpl#verlenenNationaliteit(Actie, Set, BigDecimal, int)}
     * methode.
     */
    @Test
    public final void testToevoegenNationaliteit() {
        // Initialisatie
        Actie actie = new Actie();
        Set<Bron> bronnen = new HashSet<Bron>();
        actie.setBrons(bronnen);

        BigDecimal bsn = new BigDecimal("1234567");
        int nationId = 2;

        Pers persoon = new Pers();
        persoon.setId(1L);
        persoon.setBsn(bsn);

        // Mock configuratie
        when(persoonDAO.vindPersoonOpBasisVanBsn(bsn)).thenReturn(persoon);

        // Service Aanroep
        service.toevoegenNationaliteit(actie, bronnen, bsn, nationId, new BigDecimal("111111"));

        // Verificatie
        ArgumentCaptor<Persnation> persoonsNationaliteit = ArgumentCaptor.forClass(Persnation.class);
        verify(actieDAO).voegToeActie(actie);
        verify(persoonsNationaliteitDAO).voegToePersoonsNationaliteit(persoonsNationaliteit.capture());
        assertNotNull("Toevoegen van persoonsnationaliteit met null object", persoonsNationaliteit.getValue());
        assertEquals("Persoon met verkeerde BSN toegevoegd", bsn, persoonsNationaliteit.getValue().getPers().getBsn());
        assertEquals("Nationaliteit met verkeerd ID toegevoegd", nationId, persoonsNationaliteit.getValue().getNation()
                .getId());
    }

    /**
     * Unit test voor de {@link PersoonEnNationaliteitServiceImpl#opheffenNationaliteit(Actie, Set, long)} methode.
     */
    @Test
    public final void testOpheffenNationaliteit() {
        // Initialisatie
        Actie actie = new Actie();
        Set<Bron> bronnen = new HashSet<Bron>();
        actie.setBrons(bronnen);
        Pers persoon = new Pers();
        persoon.setId(2);
        Persnation persoonsNationaliteit = new Persnation(2, new Nation(1, null), persoon);

        // Mock configuratie
        when(persoonsNationaliteitDAO.vindPersoonsNationaliteitOpBasisVanId(2)).thenReturn(persoonsNationaliteit);

        // Service Aanroep
        service.opheffenNationaliteit(actie, bronnen, 2);

        // Verificatie
        verify(actieDAO).voegToeActie(actie);
        verify(persoonsNationaliteitDAO).verwijderPersoonsNationaliteit(actie, persoonsNationaliteit);
    }

    // @Test
    public final void testWijzigPersoonNationaliteit() {
        fail("Not yet implemented");
    }

}
