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

    private final DomeinObjectFactory    domeinObjectFactory = new PersistentDomeinObjectFactory();

    private ArchiveringServiceImpl service;

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveerInkomend() {
        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        service.archiveer("Test");

        Mockito.verify(repositoryMock, Mockito.times(2)).save(argument.capture());
        Assert.assertEquals("<Wordt nader bepaald>", argument.getValue().getData());
        Assert.assertNull(argument.getValue().getDatumTijdOntvangst());
        Assert.assertNull(argument.getValue().getDatumTijdVerzenden());
        Assert.assertNotNull(argument.getValue().getAntwoordOp().getDatumTijdOntvangst());
        Assert.assertNull(argument.getValue().getAntwoordOp().getDatumTijdVerzenden());
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

        service.werkDataBij(12L, "Dit is een Test");
        Mockito.verify(repositoryMock).save(argument.capture());
        Assert.assertEquals("Dit is een Test", argument.getValue().getData());
        Assert.assertNull(argument.getValue().getDatumTijdOntvangst());
        Assert.assertNotNull(argument.getValue().getDatumTijdVerzenden());
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
