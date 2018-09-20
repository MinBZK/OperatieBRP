/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.integratie;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.GeboorteBerichtRequest;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Geslachtsnaamcomponent;
import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.Plaats;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import nl.bzk.brp.preview.model.Verwerking;
import nl.bzk.brp.preview.model.VerwerkingStatus;
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
    public void testOpslaanVerhuizingPrevalidateMet1Melding() throws Exception {
        URI uri = new URI("http://localhost:8181/dashboard/service/berichten/opslaan/verhuizing");
        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        BerichtKenmerken kenmerken = new BerichtKenmerken(new Gemeente("Rijswijk"), "CentricBZM");
        kenmerken.setPrevalidatie(true);
        request.setKenmerken(kenmerken);

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.WAARSCHUWING,
                "Er zijn al mensen ingeschreven op het nieuwe adres.")));

        Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList("Hendrika"));
        persoon.setGeslachtsnaamcomponenten(Arrays.<Geslachtsnaamcomponent> asList(new Geslachtsnaamcomponent("de",
                "Roos")));
        request.setPersoon(persoon);

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        // test
        ResponseEntity<VerhuisBerichtRequest> response =
            restTemplate.postForEntity(uri, request, VerhuisBerichtRequest.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Rijswijk'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

    }

    @Test
    public void testOpslaanGeboorte() throws Exception {
        URI uri = new URI("http://localhost:8181/dashboard/service/berichten/opslaan/geboorte");
        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Katwijk"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon nieuwgeborene = new Persoon("Johan", "Vlag");
        nieuwgeborene.setBsn(198352888L);
        nieuwgeborene.setDatumGeboorte(20120620);
        nieuwgeborene.setGemeenteGeboorte(new Gemeente("Katwijk"));
        request.setAdresNieuwgeborene(new Adres(new Gemeente("Amsterdam")));
        request.setNieuwgeborene(nieuwgeborene);
        request.setOuder1(new Persoon("Cees", "Vlag"));
        request.setOuder2(new Persoon("Paula", "Prins"));

        // test
        ResponseEntity<GeboorteBerichtRequest> response =
                restTemplate.postForEntity(uri, request, GeboorteBerichtRequest.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Katwijk'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

    }

}
