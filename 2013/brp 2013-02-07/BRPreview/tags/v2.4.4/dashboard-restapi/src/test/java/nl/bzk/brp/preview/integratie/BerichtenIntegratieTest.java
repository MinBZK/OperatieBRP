/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.integratie;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


public class BerichtenIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testBerichtenOphalen() {

        String url = TEST_SERVER_CONTEXT + "/service/berichten";

        // test
        BerichtenResponse response = restTemplate.getForObject(url, BerichtenResponse.class);

        Assert.assertEquals(DashboardSettings.DEFAULT_BERICHTEN_VOLLEDIG, response.getAantalBerichtenVolledig());

        List<Bericht> berichtenLijst = response.getBerichten();
        Bericht[] berichten = berichtenLijst.toArray(new Bericht[berichtenLijst.size()]);
        Assert.assertEquals("Utrecht", berichten[0].getPartij());
        Assert.assertEquals("Utrecht heeft een verhuizing geprevalideerd.", berichten[0].getBericht());
        Assert.assertEquals("Johan (BSN 111222333) heeft per 1 mei 2012 als adres:"
                + " Lange Vijverberg 11 (Den Haag).", berichten[0].getBerichtDetails());
        Assert.assertEquals(1, berichten[0].getAantalMeldingen());
        Assert.assertEquals(2012, berichten[0].getVerzondenOp().get(Calendar.YEAR));
        Assert.assertEquals(Calendar.MAY, berichten[0].getVerzondenOp().get(Calendar.MONTH));
        Assert.assertEquals(15, berichten[0].getVerzondenOp().get(Calendar.DATE));
        Assert.assertEquals(10, berichten[0].getVerzondenOp().get(Calendar.HOUR));
        Assert.assertEquals(9, berichten[0].getVerzondenOp().get(Calendar.MINUTE));
        Assert.assertEquals(8, berichten[0].getVerzondenOp().get(Calendar.SECOND));
        Assert.assertEquals("CentricBZM", berichten[0].getBurgerZakenModule());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, berichten[0].getSoortBijhouding());
        Assert.assertTrue(berichten[0].isPrevalidatie());
        Assert.assertEquals("Amsterdam", berichten[1].getPartij());
        Assert.assertEquals("Rotterdam", berichten[2].getPartij());
    }

    @Test
    public void testBerichtenOphalenVoorBsn() {

        String url = TEST_SERVER_CONTEXT + "/service/berichten/bsn/1201";

        // test
        BerichtenResponse response = restTemplate.getForObject(url, BerichtenResponse.class);

        Assert.assertEquals(DashboardSettings.DEFAULT_BERICHTEN_VOLLEDIG, response.getAantalBerichtenVolledig());

        List<Bericht> berichten = response.getBerichten();
        Assert.assertEquals(2, berichten.size());
        Assert.assertEquals("Amsterdam", berichten.get(0).getPartij());
        Assert.assertEquals("Rotterdam", berichten.get(1).getPartij());
    }

    @Test
    public void shouldReturn1BerichtSinceLastRequest() {
        BerichtenResponse response =
                restTemplate.getForObject(TEST_SERVER_CONTEXT + "/service/berichten/1337069346000",
                        BerichtenResponse.class);

        Assert.assertEquals(1, response.getAantalBerichten());
    }

    @Test
    @Ignore
    public void shouldReturn5Berichten() {
        fail("Test dat wanneer het settings getal 5 maximaal in een response is er ook maar 5 terugkomen");
    }

    @Test
    public void shouldReturnNoMessages() {
        Calendar future = Calendar.getInstance();
        future.add(Calendar.HOUR_OF_DAY, 1); // Set the date one hour in the future
        long milliseconds = future.getTimeInMillis();
        BerichtenResponse response =
                restTemplate.getForObject(TEST_SERVER_CONTEXT +"/service/berichten/" + milliseconds,
                        BerichtenResponse.class);
        Assert.assertEquals(0, response.getAantalBerichten());
    }

}
