/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.service.datatoegang.EntityManagerVerzoek;
import nl.bzk.brp.dockertest.service.datatoegang.TemplateVerzoek;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Abstractie tbv de databasecomponenten.
 */
abstract class AbstractDatabaseDocker extends AbstractDocker implements DatabaseDocker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private AbstractRefreshableConfigApplicationContext applicationContext;

    @Override
    public boolean isFunctioneelBeschikbaar() {
        try {
            template().readonly(jdbcTemplate -> jdbcTemplate.queryForList("SELECT 1+1"));
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public AbstractRefreshableConfigApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public TemplateVerzoek template() {
        return new TemplateVerzoek() {
            @Override
            public void readonly(final Consumer<JdbcTemplate> consumer) {
                consumer.accept(new JdbcTemplate(geefDatabaseService().getDataSource()));
            }

            @Override
            public Stream readonlyStream(final Function<JdbcTemplate, Collection> jdbcTemplateConsumer) {
                final JdbcTemplate template = new JdbcTemplate(geefDatabaseService().getDataSource());
                return jdbcTemplateConsumer.apply(template).stream();
            }

            @Override
            public void readwrite(final Consumer<JdbcTemplate> consumer) {
                final JdbcTemplate template = new JdbcTemplate(geefDatabaseService().getDataSource());
                geefDatabaseService().voerUitMetTransactie(() -> consumer.accept(template));
            }
        };
    }

    @Override
    public EntityManagerVerzoek entityManagerVerzoek() {
        return new EntityManagerVerzoek() {
            @Override
            public void voerUit(final Consumer<EntityManager> entityManagerConsumer) {
                entityManagerConsumer.accept(geefDatabaseService().getEntityManager());
            }

            @Override
            public <T> T voerUitEnReturn(final Function<EntityManager, T> function) {
                return function.apply(geefDatabaseService().getEntityManager());
            }

            @Override
            public void voerUitTransactioneel(final Consumer<EntityManager> entityManagerConsumer) {
                geefDatabaseService().voerUitMetTransactie(() -> {
                    final nl.bzk.brp.dockertest.service.datatoegang.DatabaseService databaseService = geefDatabaseService();
                    entityManagerConsumer.accept(databaseService.getEntityManager());
                });
            }
        };
    }

    @Override
    protected void afterStop() {
        try {
            super.afterStop();
        } finally {
            if (applicationContext != null) {
                applicationContext.close();
            }
        }
    }

    /**
     * Laad een databasedump in juiste database (container).
     * @param database database
     * @param bestand bestand met dump (dat gemount is in container)
     * @param logischeNaam logische naam van de container
     */
    public void laadDump(final String database, final String bestand, final DockerNaam logischeNaam) {
        if (getDockerInfo().logischeNaam() == logischeNaam) {
            final List<String> commandList = getOmgeving().getDockerCommandList("exec");
            commandList.add(getDockerInfo().logischeNaam() + "-" + getOmgeving().getNaam());
            commandList.addAll(Lists.newArrayList("pg_restore",
                    "--host", "localhost",
                    "--port", "5432",
                    "--username", "postgres",
                    "--dbname", database,
                    "--no-password",
                    "--verbose",
                    "--data-only",
                    "--disable-triggers",
                    String.format("%s/%s", Arrays.asList(getDockerInfo().mount()).get(0).containerDir(), bestand
                    )));
            try {
                ProcessHelper.DEFAULT.startProces(commandList).geefOutput();
            } catch(Exception e) {
                //catch all : warnings en/of niet fatale errors tijdens pg_restore stopt gehele proces
                LOGGER.info("Warnings/errors tijdens pg_restore, check database voor resultaten");
            }
        }
    }

    /**
     * @return de service om te lezen en schrijven van deze database/
     */
    private nl.bzk.brp.dockertest.service.datatoegang.DatabaseService geefDatabaseService() {
        return lazyClientContext().getBean(nl.bzk.brp.dockertest.service.datatoegang.DatabaseService.class);
    }

    private ApplicationContext lazyClientContext() {
        if (applicationContext == null) {
            final ClassPathXmlApplicationContext tempApplicationContext = new ClassPathXmlApplicationContext();
            final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
            final Properties properties = new Properties();

            //de delivery-datasource wordt hier gebruikt voor de client connectie
            //de master-prefix heeft geen verdere betekenis en wordt ook gebruikt
            //voor de archivering en protocollering database.
            properties.put("jdbc.master.driverClassName", "org.postgresql.Driver");
            properties.put("jdbc.master.username", "brp");
            properties.put("jdbc.master.password", "brp");
            properties.put("jdbc.master.url", String.format("jdbc:postgresql://%s:%d/brp",
                    getOmgeving().getDockerHostname(), getPoortMap().get(Poorten.DB_POORT_5432)));
            propConfig.setProperties(properties);

            LOGGER.info("Database Client properties: " + properties);
            tempApplicationContext.addBeanFactoryPostProcessor(propConfig);
            tempApplicationContext.setConfigLocation("classpath:/database/brp.xml");
            tempApplicationContext.refresh();
            applicationContext = tempApplicationContext;
        }
        return applicationContext;
    }
}
