/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel1;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

/**

 --voor vullen
 truncate autaut.abonnement cascade;
 truncate kern.pers cascade;
 truncate kern.relatie cascade;
 delete from kern.admhnd;
 delete from kern.actie;

 */
final class Belastingprofiel1Generator {

    private static final int ITERATIES = 2;

    public static void main(final String[] args) throws InterruptedException {

        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");


        final JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) applicationContext.getBean("lezenSchrijvenDataSource"));
        final Resource dsl = new DefaultResourceLoader().getResource("abonnement.sql");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, dsl, false);

        final Belastingprofiel1Service dataset1Generator = applicationContext.getBean(Belastingprofiel1Service.class);
        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < ITERATIES; i++) {
            final int persId = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        SpringBeanProvider.setContext(applicationContext);
                        dataset1Generator.genereerPersoon(persId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();

        while (!executorService.awaitTermination(2 , TimeUnit.SECONDS)) {
            //nog ff wachten
        }

        jdbcTemplate.update("update kern.admhnd set tslev = null where id in (select admhnd from kern.pers)");
        jdbcTemplate.update("insert into autaut.persafnemerindicatie (pers, afnemer, abonnement) \n"
            + "select \n"
            + "p.id, tab.partij, 0\n"
            + "from \n"
            + "autaut.toegangabonnement tab,\n"
            + "kern.pers p");

        jdbcTemplate.update("insert into autaut.his_persafnemerindicatie (persafnemerindicatie, tsreg) "
            + "select id,  '2014-10-10 10:10:10.00000'  from autaut.persafnemerindicatie");
    }
}
