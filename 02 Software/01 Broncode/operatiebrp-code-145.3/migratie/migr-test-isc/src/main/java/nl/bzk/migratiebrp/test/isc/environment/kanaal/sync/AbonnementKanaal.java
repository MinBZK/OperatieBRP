/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.repositories.StamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAutorisatieService;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Initiele vulling abonnement kanaal.
 */
public final class AbonnementKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public AbonnementKanaal() {
        super(new Worker(),
                new Configuration(
                        "classpath:beans-sync.xml",
                        "classpath:configuratie.xml",
                        "classpath:infra-db-brp.xml",
                        "classpath:infra-jta.xml",
                        "classpath:infra-db-sync.xml",
                        "classpath:infra-em-sync.xml",
                        "classpath:synchronisatie-dal-cache.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private AutorisatieReader autorisatieReader;

        @Inject
        private BrpAutorisatieService autorisatieService;

        @Inject
        @Named("syncDalDataSource")
        private DataSource dataSource;

        @Inject
        private ConverteerLo3NaarBrpService conversieService;

        @Inject
        private StamtabelRepository<Partij, Short, Integer> partijRepository;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "abonnement";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // LOG.info("Verwerk bericht: " + bericht.getInhoud());
            try {
                final List<Lo3Autorisatie> lo3Autorisaties = autorisatieReader.read(new ByteArrayInputStream(bericht.getInhoud().getBytes()));
                LOG.info("Verwerk {} autorisaties.", lo3Autorisaties.size());

                final Integer aantalThreads = bericht.getTestBericht().getTestBerichtPropertyAsInteger("autorisatie.threads");

                final AtomicLong failures = new AtomicLong(0);

                final ExecutorService executor = Executors.newFixedThreadPool(aantalThreads == null ? 8 : aantalThreads.intValue());
                for (final Lo3Autorisatie lo3Autorisatie : lo3Autorisaties) {
                    final Runnable worker = new Runnable() {
                        @Override
                        public void run() {
                            final String partijCode = lo3Autorisatie.getAfnemersindicatie();
                            LOG.debug("Verwerk autorisatie {}", lo3Autorisatie);
                            LOG.debug("Autorisatie voor partijcode: {} ", partijCode);

                            if (!"false".equalsIgnoreCase(bericht.getTestBericht().getTestBerichtProperty("autorisatie.partij.aanmaken"))) {
                                final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

                                List<Short> partijIds =
                                        jdbcTemplate.queryForList("select id from kern.partij where code = ?", Short.class, partijCode);
                                if (partijIds.isEmpty()) {
                                    LOG.info("Partij {} aanmaken", partijCode);
                                    jdbcTemplate.update("insert into kern.partij(code, naam) values (?, ?)", partijCode, "test" + partijCode);
                                    partijIds = jdbcTemplate.queryForList("select id from kern.partij where code = ?", Short.class, partijCode);
                                    jdbcTemplate.update(
                                            "insert into kern.his_partij(partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) values (?, ?, ?, ?," +
                                                    " ?)",
                                            partijIds.get(0),
                                            Timestamp.from(Instant.now()),
                                            "test" + partijCode,
                                            2012_01_01,
                                            false);
                                }
                            }

                            Logging.initContext();
                            try {
                                // Verversen cache ivm handmatig toegevoegde partijen.
                                partijRepository.clear();
                                LOG.debug("Converteren autorisatie.");
                                final BrpAutorisatie brpAutorisatie = conversieService.converteerLo3Autorisatie(lo3Autorisatie);
                                LOG.debug("Persisteren autorisatie.");
                                autorisatieService.persisteerAutorisatie(brpAutorisatie);
                                LOG.info("Autorisatie verwerkt voor partijcode: {} ", partijCode);
                            } catch (final Exception e) {
                                LOG.error("Onverwacht probleem met verwerken autorisatie voor partijcode: {}", partijCode, e);
                                failures.incrementAndGet();
                            } finally {
                                Logging.destroyContext();
                            }
                        }
                    };
                    executor.execute(worker);
                }

                executor.shutdown();
                while (!executor.isTerminated()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (final InterruptedException e) {
                        // Ignore
                    }
                }

                if (failures.get() > 0) {
                    throw new KanaalException("Niet alle autorisaties correct geconverteerd.");
                }

            } catch (final IOException e) {
                throw new KanaalException("Probleem met lezen autorisatie uit bericht", e);
            }
        }
    }
}
