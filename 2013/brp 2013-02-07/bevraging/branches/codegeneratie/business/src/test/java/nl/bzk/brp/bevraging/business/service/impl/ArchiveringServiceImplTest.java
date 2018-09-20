/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.Richting;

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
    private BerichtRepository      repositoryMock;

    private DomeinObjectFactory    domeinObjectFactory = new PersistentDomeinObjectFactory();

    private ArchiveringServiceImpl service;

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveerInkomend() {
        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        service.archiveer("Test", Richting.INGAAND);

        Mockito.verify(repositoryMock).save(argument.capture());
        Assert.assertEquals("Test", argument.getValue().getData());
    }

    /**
     * Test voor het bijwerken van een uitgaand bericht.
     */
    @Test
    public void testArchiveerUitgaand() {
        Bericht ingaand = domeinObjectFactory.createBericht();
        ingaand.setRichting(Richting.INGAAND);
        Bericht uitgaand = domeinObjectFactory.createBericht();
        uitgaand.setRichting(Richting.UITGAAND);
        Mockito.when(repositoryMock.findOne(12L)).thenReturn(uitgaand);
        Mockito.when(repositoryMock.findOne(13L)).thenReturn(ingaand);
        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);

        service.werkDataBij(13L, 12L, "Dit is een Test");
        Mockito.verify(repositoryMock).save(argument.capture());
        Assert.assertEquals("Dit is een Test", argument.getValue().getData());
        Assert.assertEquals(ingaand, argument.getValue().getAntwoordOp());
    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new ArchiveringServiceImpl();
        ReflectionTestUtils.setField(service, "berichtRepository", repositoryMock);
        ReflectionTestUtils.setField(service, "domeinObjectFactory", domeinObjectFactory);
    }

}
