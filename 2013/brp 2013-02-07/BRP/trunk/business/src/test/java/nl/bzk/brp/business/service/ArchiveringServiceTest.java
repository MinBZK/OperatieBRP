/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Calendar;

import nl.bzk.brp.dataaccess.special.BerichtRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
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
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new ArchiveringServiceImpl();
        ReflectionTestUtils.setField(service, "berichtRepository", berichtRepositoryMock);
    }

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveerInkomend() {
        // argument voor de 2e save ==> uitgaandBericht
        ArgumentCaptor<BerichtModel> argument = ArgumentCaptor.forClass(BerichtModel.class);
        service.archiveer("Test");

        Mockito.verify(berichtRepositoryMock, Mockito.times(2)).save(argument.capture());
        BerichtModel uitgaandBericht = argument.getValue();
        Assert.assertEquals("<Wordt nader bepaald>", uitgaandBericht.getData().getWaarde());
        Assert.assertNull(uitgaandBericht.getDatumTijdOntvangst());
        Assert.assertNull(uitgaandBericht.getDatumTijdVerzenden());
        Assert.assertNotNull(uitgaandBericht.getAntwoordOp().getDatumTijdOntvangst());
        Assert.assertNull(uitgaandBericht.getAntwoordOp().getDatumTijdVerzenden());
    }

    // BOLIE: don't know yet how to implement archievering.

    /**
     * Test voor het bijwerken van een uitgaand bericht.
     */
    @Test
    public void testArchiveerUitgaand() {
        BerichtModel ingaand = maakInkomendBericht("Willekeurige text");
        BerichtModel uitgaand = maakUitgaandBericht("Nog meer text", ingaand);
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
        BerichtModel ingaand = maakInkomendBericht("tekst");
        Mockito.when(berichtRepositoryMock.findOne(12L)).thenReturn(null);
        Mockito.when(berichtRepositoryMock.findOne(13L)).thenReturn(ingaand);

        service.werkDataBij(12L, "Dit is een Test");
    }


    private BerichtModel maakInkomendBericht(final String ingaandBerichtdata) {
        return new BerichtModel(
                /*final SoortBericht soort, */
                null,
                /*new AdministratieveHandelingModel(), */
                null,
                /* Berichtdata */
                new Berichtdata(ingaandBerichtdata),
                /*final DatumTijd datumTijdOntvangst, */
                new DatumTijd(Calendar.getInstance().getTime()),
                /*final DatumTijd datumTijdVerzenden, */
                null,
                /*final BerichtModel antwoordOp, */
                null,
                /*final Richting richting */
                Richting.INGAAND);
    }

    private BerichtModel maakUitgaandBericht(final String uitgaandBerichtdata, final BerichtModel antwoordOp) {
        return new BerichtModel(
                    /*final SoortBericht soort, */
                    null,
                      /*final AdministratieveHandelingModel administratieveHandeling, */
                      null,
                    /*final Berichtdata data, */
                    new Berichtdata(uitgaandBerichtdata),
                    /*final DatumTijd datumTijdOntvangst, */
                    null,
                    /*final DatumTijd datumTijdVerzenden, */
                    null,
                    /*final BerichtModel antwoordOp, */
                    antwoordOp,
                    /*final Richting richting */
                    Richting.UITGAAND);
    }

    @Test
    public void testWerkIngaandBerichtInfoBij() {
        BerichtModel ingaand = maakInkomendBericht("Willekeurige text");
        Mockito.when(berichtRepositoryMock.findOne(13L)).thenReturn(ingaand);

        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setApplicatie(new Applicatienaam("foo"));
        stuurgegevens.setOrganisatie(new Organisatienaam("bar"));

        BerichtParametersGroepBericht params = new BerichtParametersGroepBericht();
        params.setVerwerkingswijze(Verwerkingswijze.P);

        service.werkIngaandBerichtInfoBij(13L, stuurgegevens, params, SoortBericht.DUMMY);

        Assert.assertEquals(Verwerkingswijze.P, ingaand.getParameters().getVerwerkingswijze());
        Assert.assertEquals(new Applicatienaam("foo"), ingaand.getStuurgegevens().getApplicatie());
        Assert.assertEquals(new Organisatienaam("bar"), ingaand.getStuurgegevens().getOrganisatie());
        Assert.assertEquals(SoortBericht.DUMMY, ingaand.getSoort());
    }
}
