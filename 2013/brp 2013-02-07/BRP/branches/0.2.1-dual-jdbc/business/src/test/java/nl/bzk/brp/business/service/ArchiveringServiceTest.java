/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.dataaccess.logging.BerichtRepository;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;
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
public class ArchiveringServiceTest {

    @Mock
    private BerichtRepository berichtRepositoryMock;

    private ArchiveringServiceImpl service;

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveerInkomend() {
        ArgumentCaptor<BerichtModel> argument = ArgumentCaptor.forClass(BerichtModel.class);
        service.archiveer("Test");

        Mockito.verify(berichtRepositoryMock, Mockito.times(2)).save(argument.capture());
        Assert.assertEquals("<Wordt nader bepaald>", argument.getValue().getData().getWaarde());
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
        BerichtModel ingaand = new BerichtModel(Richting.INGAAND, null);
        BerichtModel uitgaand = new BerichtModel(Richting.UITGAAND, null);
        Mockito.when(berichtRepositoryMock.findOne(12L)).thenReturn(uitgaand);
        Mockito.when(berichtRepositoryMock.findOne(13L)).thenReturn(ingaand);
        ArgumentCaptor<BerichtModel> argument = ArgumentCaptor.forClass(BerichtModel.class);

        service.werkDataBij(12L, "Dit is een Test");
        Mockito.verify(berichtRepositoryMock).save(argument.capture());
        Assert.assertEquals("Dit is een Test", argument.getValue().getData().getWaarde());
        Assert.assertNull(argument.getValue().getDatumTijdOntvangst());
        Assert.assertNotNull(argument.getValue().getDatumTijdVerzenden());
    }

    /**
     * Test voor het bijwerken van een uitgaand bericht waarbij het uitgaande bericht in de database niet bestaat.
     */
    @Test(expected = RuntimeException.class)
    public void testArchiveerUitgaandWaarUitgaandBerichtMist() {
        BerichtModel ingaand = new BerichtModel(Richting.INGAAND, null);
        Mockito.when(berichtRepositoryMock.findOne(12L)).thenReturn(null);
        Mockito.when(berichtRepositoryMock.findOne(13L)).thenReturn(ingaand);

        service.werkDataBij(12L, "Dit is een Test");
    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new ArchiveringServiceImpl();
        ReflectionTestUtils.setField(service, "berichtRepository", berichtRepositoryMock);
    }

}
