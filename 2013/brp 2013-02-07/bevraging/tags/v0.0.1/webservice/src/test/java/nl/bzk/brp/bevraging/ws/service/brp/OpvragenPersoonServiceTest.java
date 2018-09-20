/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nl.bzk.brp.bevraging.business.OpvragenPersoonBusinessService;
import nl.bzk.brp.bevraging.business.antwoord.Antwoord;
import nl.bzk.brp.bevraging.business.vraag.Vraag;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.ws.service.BerichtIdGenerator;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link OpvragenPersoonService} class.
 */
public class OpvragenPersoonServiceTest {

    @Mock
    private BerichtIdGenerator             berichtIdGeneratorMock;
    @Mock
    private Log                            loggerMock;
    private OpvragenPersoonBusinessService businessServiceMock;
    private OpvragenPersoonService         service;

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon op basis van een <b>leeg</b> BSN.
     */
    @Test
    public final void testOpvragenPersoonMetLeegBSN() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(null);

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);

        assertNotNull(antwoord);
        assertTrue(antwoord.getPersoon().isEmpty());
        assertEquals(0, antwoord.getAantalPersonen());
    }

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon op basis van een <b>bestaand</b> BSN.
     */
    @Test
    public final void testOpvragenPersoonMetBestaandBSN() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(new BigDecimal(123456789));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);

        assertNotNull(antwoord);
        assertFalse(antwoord.getPersoon().isEmpty());
        assertEquals(1, antwoord.getAantalPersonen());
        assertEquals("Test1", antwoord.getPersoon().get(0).getGeslachtsNaam());

        // Ook testen met een andere geldig BSN
        vraag.setBsn(new BigDecimal(987654321));

        antwoord = service.opvragenPersoon(vraag);

        assertNotNull(antwoord);
        assertFalse(antwoord.getPersoon().isEmpty());
        assertEquals(1, antwoord.getAantalPersonen());
        assertEquals("Test3", antwoord.getPersoon().get(0).getGeslachtsNaam());
    }

    /**
     * Initializeert de mocks en de service die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(berichtIdGeneratorMock.volgendeId()).thenReturn(2L);
        businessServiceMock = new OpvragenPersoonBusinessServiceMock();

        service = new OpvragenPersoonService();
        ReflectionTestUtils.setField(service, "opvragenPersoonService", businessServiceMock);
        ReflectionTestUtils.setField(service, "berichtIdGenerator", berichtIdGeneratorMock);
        ReflectionTestUtils.setField(service, "log", loggerMock);
    }

    /**
     * Interne class welke een werkende mock is voor de {@link OpvragenPersoonBusinessService} interface. Deze mock
     * retourneert een standaard persoon op basis van de gezocht bsn.
     */
    private final class OpvragenPersoonBusinessServiceMock implements OpvragenPersoonBusinessService {

        private final Map<BigDecimal, Persoon> personen;
        /**
         * Standaard constructor die een aantal personen creeert, die op basis van bsn kunnen worden geretourneerd.
         */
        private OpvragenPersoonBusinessServiceMock() {
            personen = new HashMap<BigDecimal, Persoon>();

            Persoon persoon = null;

            persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setBsn(new Integer(123456789));
            persoon.setGeslachtsnaam("Test1");
            personen.put(new BigDecimal(persoon.getBsn()), persoon);

            persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setBsn(new Integer(121212121));
            persoon.setGeslachtsnaam("Test2");
            personen.put(new BigDecimal(persoon.getBsn()), persoon);

            persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setBsn(new Integer(987654321));
            persoon.setGeslachtsnaam("Test3");
            personen.put(new BigDecimal(persoon.getBsn()), persoon);
        }

        @Override
        public Antwoord<Persoon> opvragenPersoonOpBasisVanBsn(final Vraag<BigDecimal> vraag) {
            Antwoord<Persoon> antwoord = new Antwoord<Persoon>();

            for (Entry<BigDecimal, Persoon> persoonEntry : personen.entrySet()) {
                if (persoonEntry.getKey().equals(vraag.getVraag())) {
                    antwoord.setResultaat(persoonEntry.getValue());
                    break;
                }
            }
            return antwoord;
        }

    }

}
