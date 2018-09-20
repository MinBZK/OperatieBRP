/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import nl.bzk.brp.bevraging.business.vraag.Vraag;
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
 * Unit test voor de {@link OpvragenPersoonBusinessServiceImpl} class.
 */
public class OpvragenPersoonBusinessServiceImplTest {

    @Mock
    private PersoonRepository persoonRepositoryMock;

    /**
     * Unit test voor het opvragen van een persoon op basis van een BSN.
     */
    @Test
    public final void testOpvragenPersoonOpBasisVanBsn() {
        OpvragenPersoonBusinessServiceImpl service = new OpvragenPersoonBusinessServiceImpl();
        ReflectionTestUtils.setField(service, "persoonRepository", persoonRepositoryMock);

        Vraag<BigDecimal> vraag = new Vraag<BigDecimal>();

        vraag.setVraag(new BigDecimal("123456789"));
        assertNotNull(service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat());
        assertEquals("Test1", service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat().getGeslachtsnaam());

        vraag.setVraag(new BigDecimal("234567890"));
        assertNotNull(service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat());
        assertEquals("Test2", service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat().getGeslachtsnaam());

        vraag.setVraag(new BigDecimal("345678901"));
        assertNull(service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat());
    }

    /**
     * Unit test voor het opvragen van een persoon op basis van een leeg BSN.
     */
    @Test
    public final void testOpvragenPersoonOpBasisVanLeegBsn() {
        OpvragenPersoonBusinessServiceImpl service = new OpvragenPersoonBusinessServiceImpl();
        ReflectionTestUtils.setField(service, "persoonRepository", persoonRepositoryMock);

        Vraag<BigDecimal> vraag = new Vraag<BigDecimal>();

        assertNotNull(service.opvragenPersoonOpBasisVanBsn(vraag));
        assertNull(service.opvragenPersoonOpBasisVanBsn(vraag).getResultaat());
    }

    /**
     * Initializeert de mocks die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        Persoon pers1 = new Persoon(SoortPersoon.INGESCHREVENE);
        pers1.setBsn(123456789);
        pers1.setGeslachtsnaam("Test1");
        Persoon pers2 = new Persoon(SoortPersoon.INGESCHREVENE);
        pers2.setBsn(234567890);
        pers2.setGeslachtsnaam("Test2");

        Mockito.when(persoonRepositoryMock.zoekOpBSN(123456789)).thenReturn(pers1);
        Mockito.when(persoonRepositoryMock.zoekOpBSN(234567890)).thenReturn(pers2);
    }

}
