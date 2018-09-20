/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.integration;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedSetConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernateProxyConverter;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import nl.bzk.brp.bevraging.AbstractIntegrationTest;
import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.bevraging.dataaccess.PersoonsLijstJdbcService;
import nl.bzk.brp.bevraging.dataaccess.PersoonsLijstJpaService;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 */
public class IsMappingComparisonEqualTest extends AbstractIntegrationTest {
    Logger LOGGER = LoggerFactory.getLogger(IsMappingComparisonEqualTest.class);

    @Inject
    private PersoonsLijstJdbcService persoonsLijstJdbcService;

    @Inject
    private PersoonsLijstJpaService persoonsLijstJpaService;

    XStream xStream;

    @Before
    public void setup() {
        xStream = new XStream() {
            protected MapperWrapper wrapMapper(final MapperWrapper next) {
                return new HibernateMapper(next) {
                    @Override
                    public String serializedClass(final Class clazz) {
                        return clazz == null ? "NULL" : super
                                .serializedClass(clazz);
                    }
                };
            }

        };
        xStream.aliasPackage("", "nl.bzk.brp.model");
        xStream.aliasPackage("", "nl.bzk.copy.model");

        xStream.alias("Persoonslijst", PersoonsLijst.class);

        xStream.registerConverter(new HibernateProxyConverter());
        xStream.registerConverter(new HibernatePersistentCollectionConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedSetConverter(xStream.getMapper()));
    }

    @Test
    public void serviceShouldDeliverEqualListsForBSN_123456789() throws Exception {
        List<Integer> bsns = Arrays.asList(123456789);

        for (Integer bsn : bsns) {
            PersoonsLijst jpaLijst = persoonsLijstJpaService.getPersoonsLijstJpa(bsn);
            PersoonsLijst jdbcLijst = persoonsLijstJdbcService.getPersoonsLijst(bsn);

            String jpaXml = xStream.toXML(jpaLijst);
            String jdbcXml = xStream.toXML(jdbcLijst);

            DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(jpaXml, jdbcXml));

            assertThat(diff.getAllDifferences().size(), lessThan(15));
        }
    }

    @Test
    public void serviceShouldDeliverEqualListsForBSNMetHistorie_123456789() throws Exception {
        List<Integer> bsns = Arrays.asList(123456789);

        for (Integer bsn : bsns) {
            PersoonsLijst jpaLijst = persoonsLijstJpaService.getPersoonsLijstMetHistorieJpa(bsn);
            PersoonsLijst jdbcLijst = persoonsLijstJdbcService.getPersoonsLijstMetHistorie(bsn);

            String jpaXml = xStream.toXML(jpaLijst);
            String jdbcXml = xStream.toXML(jdbcLijst);

            DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(jpaXml, jdbcXml));

            assertThat(diff.getAllDifferences().size(), lessThan(15));
        }
    }

    @Test
    public void serviceShouldDeliverEqualListsForBSN_100000001() throws Exception {
        List<Integer> bsns = Arrays.asList(100000001);

        for (Integer bsn : bsns) {
            PersoonsLijst jpaLijst = persoonsLijstJpaService.getPersoonsLijstJpa(bsn);
            PersoonsLijst jdbcLijst = persoonsLijstJdbcService.getPersoonsLijst(bsn);

            String jpaXml = xStream.toXML(jpaLijst);
            String jdbcXml = xStream.toXML(jdbcLijst);

            LOGGER.info(jpaXml);
            LOGGER.info(jdbcXml);

            DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(jpaXml, jdbcXml));

            assertThat(diff.getAllDifferences().size(), lessThan(20));
        }
    }

    @Test
    public void serviceShouldDeliverWhenUsingJoinedJpaQuery() throws Exception {
        PersoonsLijst jpaTweakLijst = persoonsLijstJpaService.getPersoonsLijstJoinedJpa(100001001);
        String jpaTweakXml = xStream.toXML(jpaTweakLijst);
        LOGGER.info(jpaTweakXml);

        PersoonsLijst jpaLijst = persoonsLijstJpaService.getPersoonsLijstJpa(100001001);
        String jpaXml = xStream.toXML(jpaLijst);
        LOGGER.info(jpaXml);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(jpaXml, jpaTweakXml));
        LOGGER.info(diff.toString());
        LOGGER.info("Number of differences: " + diff.getAllDifferences().size());

        assertThat(diff.getAllDifferences().size(), lessThan(20));
    }

    @Test
    public void serviceShouldDeliverSimilarListsForBSN() throws Exception {
        List<Integer> bsns = Arrays.asList(100000001, 100000002, 135867277, 123456789, 234567891);

        for (Integer bsn : bsns) {
            PersoonsLijst jpaLijst = persoonsLijstJpaService.getPersoonsLijstJpa(bsn);
            PersoonsLijst jdbcLijst = persoonsLijstJdbcService.getPersoonsLijst(bsn);

            String jpaXml = xStream.toXML(jpaLijst);
            String jdbcXml = xStream.toXML(jdbcLijst);

//            LOGGER.info(jpaXml);
//            LOGGER.info(jdbcXml);

            DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(jpaXml, jdbcXml));

            assertThat(diff.similar(), is(false));
        }
    }
}
