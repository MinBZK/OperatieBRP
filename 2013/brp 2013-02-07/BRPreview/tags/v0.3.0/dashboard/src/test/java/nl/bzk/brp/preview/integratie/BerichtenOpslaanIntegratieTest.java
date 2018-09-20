/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.integratie;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Bijhoudingsverzoek;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Geslachtsnaamcomponent;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;


public class BerichtenOpslaanIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private RestTemplate               restTemplate;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        URI uri = new URI("http://localhost:8181/dashboard/service/berichten/opslaan");
        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setBijhoudingsverzoek(new Bijhoudingsverzoek(new Gemeente("Rijswijk"), "CentricBZM"));

        Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList("Jan"));
        persoon.setGeslachtsnaamcomponenten(Arrays.asList(new Geslachtsnaamcomponent("Vis")));
        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Assen"));
        nieuwAdres.setStraat("Herestraat");
        nieuwAdres.setHuisnummer("1");
        nieuwAdres.setDatumAanvangAdreshouding(20120621);
        persoon.setAdres(nieuwAdres);
        request.setPersoon(persoon);

        Adres oudAdres = new Adres();
        oudAdres.setGemeente(new Gemeente("Delft"));
        request.setOudAdres(oudAdres);

        // test
        ResponseEntity<VerhuisBerichtRequest> response =
            restTemplate.postForEntity(uri, request, VerhuisBerichtRequest.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where bericht like '%Delft%'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

    }

}
