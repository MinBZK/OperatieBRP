/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:runtime-test-beans.xml", initializers = PortInitializer.class)
public class AfnemersIndicatiesRepositoryTest {

    @Inject
    private AfnemersIndicatieRepository afnemersindicatieRepository;

    @Inject
    private DataSource dataSource;

    @Test
    public void test() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvul.initvullingresult_afnind SET bericht_resultaat='TE_VERZENDEN'");

        final BerichtTeller<AfnemersindicatiesBericht> verwerker = new BerichtTeller<>();
        Assert.assertFalse(afnemersindicatieRepository.verwerkAfnemerindicaties(ConversieResultaat.TE_VERZENDEN, verwerker, 100));

        Assert.assertEquals("Verkeerd aantal berichten gevonden", 30, verwerker.aantalBerichten());
    }

    @Test
    public void testInhoud() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvul.initvullingresult_afnind SET bericht_resultaat='VERZONDEN'");
        jdbcTemplate.update("UPDATE initvul.initvullingresult_afnind SET bericht_resultaat='TE_VERZENDEN' where pl_id=42");

        final BerichtTeller<AfnemersindicatiesBericht> verwerker = new BerichtTeller<>();
        Assert.assertFalse(afnemersindicatieRepository.verwerkAfnemerindicaties(ConversieResultaat.TE_VERZENDEN, verwerker, 100));
        Assert.assertEquals("Verkeerd aantal berichten gevonden", 1, verwerker.aantalBerichten());
        final AfnemersindicatiesBericht bericht = verwerker.getBerichten().get(0);
        final String berichtXml = bericht.format();
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<afnemersindicaties xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                + "<aNummer>2467964321</aNummer>"
                + "<afnemersindicatie><stapelNummer>0</stapelNummer><volgNummer>0</volgNummer><afnemerCode>099971</afnemerCode><geldigheidStartDatum>20130101</geldigheidStartDatum></afnemersindicatie>"
                + "<afnemersindicatie><stapelNummer>0</stapelNummer><volgNummer>1</volgNummer><afnemerCode>099971</afnemerCode><geldigheidStartDatum>20090101</geldigheidStartDatum></afnemersindicatie>"
                + "<afnemersindicatie><stapelNummer>1</stapelNummer><volgNummer>0</volgNummer><geldigheidStartDatum>20100101</geldigheidStartDatum></afnemersindicatie>"
                + "<afnemersindicatie><stapelNummer>1</stapelNummer><volgNummer>1</volgNummer><afnemerCode>099971</afnemerCode><geldigheidStartDatum>20060101</geldigheidStartDatum></afnemersindicatie>"
                + "<afnemersindicatie><stapelNummer>2</stapelNummer><volgNummer>0</volgNummer><geldigheidStartDatum>20050101</geldigheidStartDatum></afnemersindicatie>"
                + "<afnemersindicatie><stapelNummer>2</stapelNummer><volgNummer>1</volgNummer><afnemerCode>099971</afnemerCode><geldigheidStartDatum>20000101</geldigheidStartDatum></afnemersindicatie>"
                + "</afnemersindicaties>", berichtXml);
    }
}
