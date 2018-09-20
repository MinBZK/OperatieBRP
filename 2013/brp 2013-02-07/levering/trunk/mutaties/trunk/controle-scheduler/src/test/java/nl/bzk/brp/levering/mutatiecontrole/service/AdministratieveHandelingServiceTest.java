/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.levering.mutatiecontrole.AbstractRepositoryTestCase;
import nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.levering.mutatiecontrole.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.mutatiecontrole.service.impl.AdministratieveHandelingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdministratieveHandelingServiceTest extends AbstractRepositoryTestCase {

    @InjectMocks
    private final AdministratieveHandelingService administratieveHandelingService = new AdministratieveHandelingServiceImpl();

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    private final List<BigInteger> onverwerkteAdministratieveHandelingen = new ArrayList<BigInteger>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        onverwerkteAdministratieveHandelingen.add(new BigInteger("20"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("21"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("22"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("23"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("24"));

        when(administratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp()).thenReturn(
                onverwerkteAdministratieveHandelingen);
    }


    @DirtiesContext
    @Test
    public void testMethodePlaatsOnverwerkteAdministratieveHandelingenOpQueue() {
        administratieveHandelingService.plaatsOnverwerkteAdministratieveHandelingenOpQueue();

        verify(administratieveHandelingVerwerker).plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);
        verify(administratieveHandelingRepository).haalOnverwerkteAdministratieveHandelingenOp();
    }
}
