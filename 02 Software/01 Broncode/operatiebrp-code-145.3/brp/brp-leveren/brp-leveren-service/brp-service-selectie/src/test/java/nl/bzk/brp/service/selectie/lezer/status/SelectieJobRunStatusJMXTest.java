/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.lezer.job.SelectieJobRunStatusJMX;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieJobRunStatusJMXTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieJobRunStatusJMXTest {

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;
    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private SelectieJobRunStatusJMX selectieJobRunStatusJMX;

    @Before
    public void voorTest() {
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(new SelectieJobRunStatus());
    }

    @Test
    public void test_moestStoppen() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setMoetStoppen(true);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(status);

        assertTrue(selectieJobRunStatusJMX.getMoestStoppen());
    }

    @Test
    public void test_isError() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setError(true);

        assertTrue(selectieJobRunStatusJMX.isError());
    }

    @Test
    public void test_getSelectieRunId() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setSelectieRunId(111);

        assertEquals(111, selectieJobRunStatusJMX.getSelectieRunId().intValue());
    }

    @Test
    public void testVoortgangVerwerker0() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        final double voortgangPerc = selectieJobRunStatusJMX.voortgang();
        assertEquals(0, voortgangPerc, 0);
    }


    @Test
    public void testVoortgangVerwerker() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);

        //zonder schrijftaken
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        assertEquals(100, selectieJobRunStatusJMX.voortgang(), 0);

        //met 0% schrijftaken afgerond
        status.incrementEnGetSchrijfTaken(100);
        assertEquals(70, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testVoortgangVerwerkerVoorDeHelftKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 50).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        assertEquals(35, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testVoortgangSchrijverKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        status.incrementEnGetSchrijfTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetSchrijfKlaarTaken());

        //zonder resultaatschrijf taken
        assertEquals(100, selectieJobRunStatusJMX.voortgang(), 0);

        //met 0% resultaatschrijf taken afgerond
        status.getSelectieResultaatSchrijfTaakIncrement(100);
        assertEquals(80, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testVoortgangSchrijverAfnemerindicatieKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        status.incrementEnGetSchrijfTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetSchrijfKlaarTaken());
        status.addEnGetAantalPlaatsAfnemerindicatieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementAfnemerindicatieTaakVerwerkt(1));

        //zonder resultaatschrijf taken
        assertEquals(100, selectieJobRunStatusJMX.voortgang(), 0);

        //met 0% resultaatschrijf taken afgerond
        status.getSelectieResultaatSchrijfTaakIncrement(100);
        assertEquals(80, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testVoortgangResultaatKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        status.incrementEnGetSchrijfTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetSchrijfKlaarTaken());
        status.getSelectieResultaatSchrijfTaakIncrement(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetSelectieResultaatSchrijfTakenIncrement());

        assertEquals(100, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testVoortgangResultaatVoorDeHelftKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        status.incrementEnGetSchrijfTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetSchrijfKlaarTaken());
        status.getSelectieResultaatSchrijfTaakIncrement(100);
        IntStream.range(0, 50).forEach(value -> status.incrementEnGetSelectieResultaatSchrijfTakenIncrement());

        assertEquals(90, selectieJobRunStatusJMX.voortgang(), 1);
    }

    @Test
    public void testVerwerkSnelheidSelectieTaken() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(calendar.getTime());
        IntStream.range(0, 10).forEach(value -> status.incrementEnGetVerwerkTaken());

        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidSelectieTaken();

        assertEquals(2, verwerksnelheid);
        assertEquals(10, status.getSelectieTaakCount());
    }

    @Test
    public void testVerwerkSnelheidSelectieTaken_startDatumIsNull() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(null);

        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidSelectieTaken();

        assertEquals(0, verwerksnelheid);
    }

    @Test
    public void testVerwerkSnelheidBlobs() {
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(5);
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(calendar.getTime());
        IntStream.range(0, 10).forEach(value -> status.incrementEnGetVerwerkTaken());

        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidBlobs();

        assertEquals(10, verwerksnelheid);
        assertEquals(10, status.getSelectieTaakCount());
    }

    @Test
    public void testVerwerkSnelheidBlobs_startDatumIsNull() {
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(5);
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(null);
        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidBlobs();

        assertEquals(0, verwerksnelheid);
    }


    @Test
    public void testVerwerkSnelheidSchrijfTaken() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(calendar.getTime());
        IntStream.range(0, 10).forEach(value -> status.incrementEnGetSchrijfTaken(10));

        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidSelectieSchrijfTaken();

        assertEquals(20, verwerksnelheid);
    }

    @Test
    public void testVerwerkSnelheidSchrijfTaken_startDatumIsNull() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(null);

        final long verwerksnelheid = selectieJobRunStatusJMX.verwerkSnelheidSelectieSchrijfTaken();

        assertEquals(0, verwerksnelheid);
    }


    @Test
    public void testTijdTeGaan() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -10);

        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(calendar.getTime());
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 25).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());

        final double doorloop = selectieJobRunStatusJMX.tijdTeGaan();

        assertEquals(47, (int) doorloop);
    }


    @Test
    public void test_tijdTeGaan_startDatumisNull() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        final Date startDatum =new Date();
        status.setStartDatum(startDatum);

        selectieJobRunStatusJMX.tijdTeGaan();
    }

    @Test
    public void test_tijdTeGaan_eindDatumisNull() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        final Date eindDatum =new Date();
        status.setEindeDatum(eindDatum);

        selectieJobRunStatusJMX.tijdTeGaan();
    }

    @Test
    public void testVoortgangSchrijverVoorDeHelftKlaar() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 100).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());
        status.incrementEnGetSchrijfTaken(100);
        IntStream.range(0, 50).forEach(value -> status.incrementEnGetSchrijfKlaarTaken());
        assertEquals(75, selectieJobRunStatusJMX.voortgang(), 0);
    }

    @Test
    public void testDoorloopTijd() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -10);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(calendar.getTime());
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 25).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());

        final double doorloop = selectieJobRunStatusJMX.doorloopTijd();

        assertEquals(57, (int) doorloop);
    }

    @Test
    public void testDoorloopTijd_startDatumIsNull() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -10);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        status.setStartDatum(null);
        status.setTotaalAantalSelectieTaken(100);
        IntStream.range(0, 25).forEach(value -> status.incrementEnGetVerwerkKlaarTaken());

        final double doorloop = selectieJobRunStatusJMX.doorloopTijd();

        assertEquals(0, (int) doorloop);
    }
}
