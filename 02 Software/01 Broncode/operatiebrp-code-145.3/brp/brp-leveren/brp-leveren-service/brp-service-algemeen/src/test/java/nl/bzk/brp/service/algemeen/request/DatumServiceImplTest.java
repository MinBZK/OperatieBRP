/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.request.DatumServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatumServiceImplTest {


    @InjectMocks
    private DatumServiceImpl datumService;

    @Test(expected = StapMeldingException.class)
    public void testDatumKanNietGeparsedWorden() throws StapMeldingException {
        datumService.parseDate("2001");
    }

    @Test
    public void testDatum() throws StapMeldingException {
        final LocalDate localDate = datumService.parseDate("2001-02-01");
        Assert.assertEquals(2001, localDate.getYear());
        Assert.assertEquals(2, localDate.getMonthValue());
        Assert.assertEquals(1, localDate.getDayOfMonth());
    }

    @Test(expected = StapMeldingException.class)
    public void testGeenSchrikkeljaar() throws StapMeldingException {
        final LocalDate localDate = datumService.parseDate("2015-02-29");
    }

    @Test(expected = StapMeldingException.class)
    public void testDatumtijdKanNietGeparsedWorden() throws StapMeldingException {
        datumService.parseDateTime("2001");
    }

    @Test
    public void testDatumtijd() throws StapMeldingException {
        final ZonedDateTime zonedDateTime = datumService.parseDateTime("2016-08-23T10:04:55.144Z");
        Assert.assertEquals(2016, zonedDateTime.getYear());
        Assert.assertEquals(8, zonedDateTime.getMonthValue());
        Assert.assertEquals(23, zonedDateTime.getDayOfMonth());
        Assert.assertEquals(10, zonedDateTime.getHour());
        Assert.assertEquals(4, zonedDateTime.getMinute());
        Assert.assertEquals(55, zonedDateTime.getSecond());
        Assert.assertEquals(144000000, zonedDateTime.getNano());
    }

    @Test(expected = StapMeldingException.class)
    public void testGeenSchrikkeljaar2() throws StapMeldingException {
        datumService.parseDateTime("2015-02-29T10:04:55.144");
    }
}
