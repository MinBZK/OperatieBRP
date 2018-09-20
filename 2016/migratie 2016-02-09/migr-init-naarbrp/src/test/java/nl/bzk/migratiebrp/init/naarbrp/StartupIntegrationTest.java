/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import java.text.ParseException;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;

@Ignore("Query werkt niet op HSQL")
public class StartupIntegrationTest extends AbstractIntegrationTest {

    @Ignore("Query werkt niet op HSQL")
    @Test
    public void startup() {
        Main.main(new String[] {});
    }

    @Before
    public void setup() throws ParseException {
        // Setup
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(gbavDataSource);

        jdbcTemplate.execute("delete from initvul.initvullingresult");
        jdbcTemplate.execute("delete from initvul.initvullingresult_aut");
        jdbcTemplate.execute("delete from initvul.initvullingresult_afnind_stapel");
        jdbcTemplate.execute("delete from initvul.initvullingresult_afnind_regel");
        jdbcTemplate.execute("delete from initvul.initvullingresult_afnind");

        jdbcTemplate.execute("delete from lo3_pl_afnemer_ind");
        jdbcTemplate.execute("delete from lo3_autorisatie");
        jdbcTemplate.execute("delete from lo3_pl_persoon");
        jdbcTemplate.execute("delete from lo3_pl");
        jdbcTemplate.execute("delete from lo3_bericht");
        jdbcTemplate.execute("delete from activiteit");

        insertGbavPersoon(
            toDate("20120604"),
            "00000000Lg0100000000000000000123123123400000000000028001148011001012312312340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E8110004051881200079-X000185100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876AA7210001I851000819700101861000819700102",
            null);

        insertGbavAbonnement("\"1\",\"900050\",\"Autorisatietabelregel met spontaan, selectie, adhoc en adresgeörienteerd\",\"20000101\",\"20210101\",\"\",\"0\",\"0\",\"Postbus\",\"90050\",\"\",\"\",\"9050AC\",\"0518\",\"01.01.20\",\"KNV 07.67.10\",\"01.01.20#01.02.20#01.03.10\",\"0\",\"N\",\"01.01.20#01.02.20#01.03.10\",\"KNV 07.67.10\",\"1\",\"1\",\"20000306\",\"00\",\"A\",\"01.03.10#01.03.20#01.03.30#\",\"\",\"1\",\"\",\"1\",\"N\",\"01.04.10\",\"KV 08.09.10\",\"N\"");

        insertAfnemersIndicatie(1231231234L, 900050, 19940101);
    }

    @Ignore("Query werkt niet op HSQL")
    @Test
    public void testPersonen() throws JMSException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(gbavDataSource);
        final int aantalVoorLaden = jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult", Integer.class);
        Assert.assertEquals(0, aantalVoorLaden);

        // Execute LAAD_PERS
        Main.main(new String[] {"-laad_pers" });

        // Verify
        final int aantalTeVerzendenNaLaden =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult where conversie_resultaat='TE_VERZENDEN'", Integer.class);
        Assert.assertEquals(1, aantalTeVerzendenNaLaden);

        // Execute SYNC_PERS
        Main.main(new String[] {"-sync_pers" });

        // Verify
        final int aantalTeVerzendenNaSync =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult where conversie_resultaat='TE_VERZENDEN'", Integer.class);
        Assert.assertEquals(0, aantalTeVerzendenNaSync);
        final int aantalVerzondenNaSync =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult where conversie_resultaat='VERZONDEN'", Integer.class);
        Assert.assertEquals(1, aantalVerzondenNaSync);

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(1000);
        final TextMessage message = (TextMessage) jmsTemplate.receive("init.vulling.naarbrp");
        Assert.assertEquals(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<synchroniseerNaarBrpVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                    + "<lo3BerichtAsTeletexString>0028001148011001012312312340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E8110004051881200079-X000185100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876AA7210001I851000819700101861000819700102</lo3BerichtAsTeletexString>"
                    + "<stuurgegevens><opnemenAlsNieuwePl>J</opnemenAlsNieuwePl></stuurgegevens></synchroniseerNaarBrpVerzoek>",
            message.getText());
    }

    @Ignore("Query werkt niet op HSQL")
    @Test
    public void testAbonnementen() throws JMSException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(gbavDataSource);
        final int aantalVoorLaden = jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_aut", Integer.class);
        Assert.assertEquals(0, aantalVoorLaden);

        // Execute LAAD_PERS
        Main.main(new String[] {"-laad_aut" });

        // Verify
        final int aantalTeVerzendenNaLaden =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_aut where conversie_resultaat='TE_VERZENDEN'", Integer.class);
        Assert.assertEquals(1, aantalTeVerzendenNaLaden);

        // Execute SYNC_PERS
        Main.main(new String[] {"-sync_aut" });

        // Verify
        final int aantalTeVerzendenNaSync =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_aut where conversie_resultaat='TE_VERZENDEN'", Integer.class);
        Assert.assertEquals(0, aantalTeVerzendenNaSync);
        final int aantalVerzondenNaSync =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_aut where conversie_resultaat='VERZONDEN'", Integer.class);
        Assert.assertEquals(1, aantalVerzondenNaSync);

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(1000);
        final TextMessage message = (TextMessage) jmsTemplate.receive("init.vulling.naarbrp");
        Assert.assertEquals("bericht", message.getText());
    }

    @Ignore("Query werkt niet op HSQL")
    @Test
    public void testAfnemersindicaties() throws JMSException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(gbavDataSource);
        final int aantalVoorLaden = jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_afnind", Integer.class);
        Assert.assertEquals(0, aantalVoorLaden);

        // Execute LAAD_PERS
        Main.main(new String[] {"-laad_afn" });

        // Verify
        final int aantalTeVerzendenNaLaden =
                jdbcTemplate.queryForObject(
                    "select count(*) from initvul.initvullingresult_afnind where conversie_resultaat='TE_VERZENDEN'",
                    Integer.class);
        Assert.assertEquals(1, aantalTeVerzendenNaLaden);

        // Execute SYNC_PERS
        Main.main(new String[] {"-sync_afn" });

        // Verify
        final int aantalTeVerzendenNaSync =
                jdbcTemplate.queryForObject(
                    "select count(*) from initvul.initvullingresult_afnind where conversie_resultaat='TE_VERZENDEN'",
                    Integer.class);
        Assert.assertEquals(0, aantalTeVerzendenNaSync);
        final int aantalVerzondenNaSync =
                jdbcTemplate.queryForObject("select count(*) from initvul.initvullingresult_afnind where conversie_resultaat='VERZONDEN'", Integer.class);
        Assert.assertEquals(1, aantalVerzondenNaSync);

        final int aantalStapelsNaSync =
                jdbcTemplate.queryForObject(
                    "select count(*) from initvul.initvullingresult_afnind_stapel where conversie_resultaat='TE_VERWERKEN'",
                    Integer.class);
        Assert.assertEquals(1, aantalStapelsNaSync);

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(1000);
        final TextMessage message = (TextMessage) jmsTemplate.receive("init.vulling.naarbrp");
        Assert.assertEquals(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<afnemersindicaties xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                    + "<aNummer>1231231234</aNummer>"
                    + "<afnemersindicatie><stapelNummer>0</stapelNummer><volgNummer>0</volgNummer><afnemerCode>900050</afnemerCode><geldigheidStartDatum>19940101</geldigheidStartDatum></afnemersindicatie>"
                    + "</afnemersindicaties>",
            message.getText());
    }
}
