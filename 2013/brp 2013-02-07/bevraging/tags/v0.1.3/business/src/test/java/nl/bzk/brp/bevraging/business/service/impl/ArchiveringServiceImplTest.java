/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import nl.bzk.brp.bevraging.domein.ber.Bericht;
import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link ArchiveringServiceImpl} class.
 */
public class ArchiveringServiceImplTest {

    @Mock
    private BerichtRepository repositoryMock;

    private ArchiveringServiceImpl service;

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveerInkomend() {
        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        service.archiveer("Test");

        Mockito.verify(repositoryMock).save(argument.capture());
        Assert.assertEquals("Test", argument.getValue().getData());
    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new ArchiveringServiceImpl();
        ReflectionTestUtils.setField(service, "berichtRepository", repositoryMock);
    }

}
