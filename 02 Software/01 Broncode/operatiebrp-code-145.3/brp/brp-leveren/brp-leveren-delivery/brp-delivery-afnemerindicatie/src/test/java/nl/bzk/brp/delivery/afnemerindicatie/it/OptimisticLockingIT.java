/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.it;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.delivery.afnemerindicatie.RegistreerAfnemerindicatieCallbackImpl;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.afnemerindicatie.Afnemerindicatie;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.cache.CacheController;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 * Integratietest om optimistic locking binnen Onderhoud Afnemerindicaties te testen.
 */
@ContextConfiguration(classes = OptimisticLockingIT.TestConfiguratie.class)
@Data(resources = {"classpath:/data/testdata.xml"})
public class OptimisticLockingIT extends AbstractDataAccessTest {

    private static final String OIN = "oin";

    @Inject
    private OnderhoudAfnemerindicatieService service;
    @Inject
    private DataSource masterDataSource;
    @Inject
    private CacheController cacheController;

    @Before
    public void setUp() throws Exception {
        BrpNu.set(DatumUtil.nuAlsZonedDateTimeInNederland());
        final Connection connection = masterDataSource.getConnection();
        final Statement statement = connection.createStatement();
        statement.execute(String.format("update kern.partij set oin='%s', datovergangnaarbrp=20170101 where code='199901'", OIN));
        connection.commit();
        connection.close();
        cacheController.herlaadCaches();
        System.setProperty("pasPersoonCacheAan", "false");
        System.setProperty("pasPersoonAan", "false");
    }

    @Test
    public void testNietsAangepast() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet("/data/testdata.xml"));

        doeVerzoek();
    }

    @Test(expected = OptimisticLockException.class)
    public void testPersoonCacheAangepast() throws Exception {
        System.setProperty("pasPersoonCacheAan", "true");

        doeVerzoek();
    }

    @Test(expected = OptimisticLockException.class)
    public void testPersoonZonderAfnemerindicatieAangepast() throws Exception {
        System.setProperty("pasPersoonAan", "true");

        doeVerzoek();
    }

    @Test(expected = OptimisticLockException.class)
    public void testPersoonMetAfnemerindicatieAangepast() throws Exception {
        System.setProperty("pasPersoonAan", "true");
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet("/data/testdata_afnemerindicatie.xml"));

        doeVerzoek();

    }

    private String doeVerzoek() {
        final AfnemerindicatieVerzoek verzoek = new AfnemerindicatieVerzoek();
        verzoek.setOin(new OIN(OIN, OIN));
        verzoek.setSoortDienst(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        verzoek.getStuurgegevens().setZendendePartijCode("199901");
        verzoek.getStuurgegevens().setTijdstipVerzending(ZonedDateTime.now());
        verzoek.getStuurgegevens().setZendendSysteem("TEST");
        verzoek.getParameters().setLeveringsAutorisatieId(String.valueOf(1));
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setPartijCode(String.valueOf(199901));
        afnemerindicatie.setBsn("755443322");
        verzoek.setAfnemerindicatie(afnemerindicatie);

        final RegistreerAfnemerindicatieCallbackImpl callback = new RegistreerAfnemerindicatieCallbackImpl();
        service.onderhoudAfnemerindicatie(verzoek, callback);

        return callback.getResultaat();
    }

    private static IDataSet getDataSet(final String location) throws Exception {
        InputStream inputStream = OptimisticLockingIT.class.getResourceAsStream(location);
        return new FlatXmlDataSetBuilder().build(inputStream);
    }

    @Import(DataAccessTestConfiguratie.class)
    @ImportResource({
            "classpath:config/test-interceptor-override.xml",
            "classpath:levering-kern-algemeen.xml",
            "classpath:afnemerindicatie-beans.xml",
            "classpath:delivery-algemeen-beans.xml",
            "classpath:maakbericht-service-beans.xml"
    })
    public static class TestConfiguratie {

        @Bean
        ArchiefService maakArchiefService() {
            return Mockito.mock(ArchiefService.class);
        }

        @Bean
        PlaatsAfnemerBerichtService maakPlaatsAfnemerBerichtService() {
            return Mockito.mock(PlaatsAfnemerBerichtService.class);
        }

        @Bean(name = "jmxdomein")
        public String maakJMXDomein() {
            return "jmx";
        }
    }


    private IDatabaseConnection getConnection() throws Exception {
        final Connection jdbcConnection = masterDataSource.getConnection();
        final IDatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);
        final DatabaseConfig config = databaseConnection.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, Boolean.TRUE);
        return databaseConnection;
    }
}
