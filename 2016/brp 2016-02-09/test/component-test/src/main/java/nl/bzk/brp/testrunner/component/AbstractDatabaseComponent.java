/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import nl.bzk.brp.testrunner.component.services.datatoegang.DatabaseService;
import nl.bzk.brp.testrunner.component.services.datatoegang.DatabaseVerzoek;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import nl.bzk.brp.testrunner.component.services.dsl.DslVerzoekFactory;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

/**
 *
 */
public abstract class AbstractDatabaseComponent extends AbstractDockerComponent implements BrpDatabase {

//    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ApplicationContext applicationContext;

    public AbstractDatabaseComponent(final Omgeving omgeving) {
        super(omgeving);
    }

    @Override
    public void leegDatabase() {
        geefDatabaseService().voerUitMetTransactie(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                jdbcTemplate.update("truncate autaut.certificaat cascade");
                jdbcTemplate.update("truncate kern.pers cascade");
                jdbcTemplate.update("truncate autaut.abonnement cascade");
                jdbcTemplate.update("truncate kern.relatie cascade");
                jdbcTemplate.update("truncate kern.admhnd cascade");
//                jdbcTemplate.update("delete from kern.actie");
            }
        });
    }

    @Override
    public void verwijderAllePersonen() {

        geefDatabaseService().voerUitMetTransactie(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                jdbcTemplate.update("truncate kern.betr cascade;");
                jdbcTemplate.update("truncate kern.relatie cascade;");
                jdbcTemplate.update("truncate kern.onderzoek cascade;");
                jdbcTemplate.update("truncate kern.pers cascade;");

                jdbcTemplate.update("delete from verconv.lo3melding;");
                jdbcTemplate.update("delete from verconv.lo3voorkomen;");
                jdbcTemplate.update("delete from verconv.lo3ber;");
                jdbcTemplate.update("delete from ist.autorisatietabel;");
                jdbcTemplate.update("delete from ist.stapelrelatie;");
                jdbcTemplate.update("delete from ist.stapelvoorkomen;");
                jdbcTemplate.update("delete from ist.stapel;");
            }
        });

    }

    @Override
    public void sqlScript(final Resource sqlResource) {
        geefDatabaseService().voerUitMetTransactie(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                JdbcTestUtils.executeSqlScript(jdbcTemplate, sqlResource, false);
            }
        });
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.DB_POORT_5432);
        return internePoorten;
    }


    @Override
    public void voerUit(final DatabaseVerzoek verzoek) {
        geefDatabaseService().voerUit(verzoek);
    }

    @Override
    public void voerUitMetTransactie(final DatabaseVerzoek verzoek) {
        geefDatabaseService().voerUitMetTransactie(verzoek);
    }

    @Override
    protected List<String> geefOverigeRunCommandos() {
        return Arrays.asList("--cpu-shares", "128");
    }

    /**
     * @return de service om te lezen en schrijven van deze database/
     */
    private DatabaseService geefDatabaseService() {
        return luieContext().getBean(DatabaseService.class);
    }


    @Override
    public DslVerzoekFactory geefDslVerzoekFactory() {
        return luieContext().getBean(DslVerzoekFactory.class);
    }

    private ApplicationContext luieContext() {
        if (applicationContext == null) {
            final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
            final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
            final Properties properties = new Properties();
            properties.put("jdbc.driverClassName", "org.postgresql.Driver");
            properties.put("jdbc.username", "brp");
            properties.put("jdbc.password", "brp");
            properties.put("jdbc.url", String.format("jdbc:postgresql://%s:%d/brp", getOmgeving().geefOmgevingHost(), getPoortMap().get(Poorten.DB_POORT_5432)));
            propConfig.setProperties(properties);

            applicationContext.addBeanFactoryPostProcessor(propConfig);
            applicationContext.setConfigLocation("classpath:/database/brp.xml");
            applicationContext.refresh();

            this.applicationContext = applicationContext;

        }

        return applicationContext;
    }
}
