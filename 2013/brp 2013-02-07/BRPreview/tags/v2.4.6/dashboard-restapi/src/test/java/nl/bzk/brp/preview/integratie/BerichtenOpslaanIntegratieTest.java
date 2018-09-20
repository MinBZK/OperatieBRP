/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.integratie;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.AdrescorrectieBerichtRequest;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.GeboorteBerichtRequest;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.HuwelijkBerichtRequest;
import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.Plaats;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import nl.bzk.brp.preview.model.Verwerking;
import nl.bzk.brp.preview.model.VerwerkingStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;


public class BerichtenOpslaanIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private RestTemplate               restTemplate;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testOpslaanVerhuizingPrevalidateMet1Melding() throws Exception {
        URI uri = new URI(TEST_SERVER_CONTEXT + "/service/berichten/opslaan/verhuizing");
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

        request.setPersoon(new Persoon(366, "Hendrika", "de", "Roos"));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvang(20120620);
        request.setNieuwAdres(nieuwAdres);

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        // test
        restTemplate.put(uri, request);

        //Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Rijswijk'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

    }

    @Test
    public void testOpslaanGeboorte() throws Exception {
        URI uri = new URI(TEST_SERVER_CONTEXT + "/service/berichten/opslaan/geboorte");
        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Katwijk"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon nieuwgeborene = new Persoon(198352888, "Johan", "Vlag");
        nieuwgeborene.setDatumGeboorte(20120620);
        nieuwgeborene.setGemeenteGeboorte(new Gemeente("Katwijk"));
        request.setAdresNieuwgeborene(new Adres(new Gemeente("Amsterdam")));
        request.setNieuwgeborene(nieuwgeborene);
        request.setOuder1(new Persoon(31, "Cees", "Vlag"));
        request.setOuder2(new Persoon(32, "Paula", "Prins"));

        // test
        restTemplate.put(uri, request);

        // Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Katwijk'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

        String sqlBsn = "select count(0) from dashboard.berichten b, dashboard.bericht_bsn bsn where bsn.bericht = b.id and partij = 'Katwijk'";
        long countBsn = jdbcTemplate.queryForLong(sqlBsn, (Map<String, ?>) null);

        Assert.assertEquals(3, countBsn);

    }

    @Test
    public void testOpslaanHuwelijk() throws Exception {
        URI uri = new URI(TEST_SERVER_CONTEXT + "/service/berichten/opslaan/huwelijk");
        HuwelijkBerichtRequest request = new HuwelijkBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Harlingen"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setPlaats(new Plaats("Delft"));
        Persoon persoon1 = new Persoon(31, "Cees", "Vlag");
        persoon1.setGeslacht("Man");
        request.setPersoon1(persoon1);
        Persoon persoon2 = new Persoon(32, "Paula", "Prins");
        persoon2.setGeslacht("Vrouw");
        request.setPersoon2(persoon2);

        // test
        restTemplate.put(uri, request);

        //Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Harlingen'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

        String sqlBsn = "select count(0) from dashboard.berichten b, dashboard.bericht_bsn bsn where bsn.bericht = b.id and partij = 'Harlingen'";
        long countBsn = jdbcTemplate.queryForLong(sqlBsn, (Map<String, ?>) null);

        Assert.assertEquals(2, countBsn);

    }

    @Test
    public void testOpslaanAdrescorrectie() throws Exception {
        URI uri = new URI(TEST_SERVER_CONTEXT + "/service/berichten/opslaan/adrescorrectie");
        AdrescorrectieBerichtRequest request = new AdrescorrectieBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Makkum"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        List<Adres> adressen = new ArrayList<Adres>();
        Adres adres = new Adres();
        adres.setGemeente(new Gemeente("Groningen"));
        adres.setStraat("Akerkhof");
        adres.setHuisnummer("2");
        adres.setPlaats(new Plaats("Groningen"));
        adres.setDatumAanvang(20120620);
        adressen.add(adres);
        request.setAdressen(adressen);

        request.setPersoon(new Persoon(31, "Cees", "Vlag"));

        // test
        restTemplate.put(uri, request);

        //Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String sql = "select count(0) from dashboard.berichten where partij = 'Makkum'";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

        String sqlBsn = "select count(0) from dashboard.berichten b, dashboard.bericht_bsn bsn where bsn.bericht = b.id and partij = 'Makkum'";
        long countBsn = jdbcTemplate.queryForLong(sqlBsn, (Map<String, ?>) null);

        Assert.assertEquals(1, countBsn);

    }

}
