/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Delete alle 'nieuwe'partjien kanaal.
 */
public final class DeletePartijenKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public DeletePartijenKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-brp.xml",
            "classpath:infra-jta.xml",
            "classpath:infra-db-sync.xml",
            "classpath:infra-em-sync.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        @Named("brpDataSource")
        private DataSource brpDataSource;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "deletePartijen";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(brpDataSource);

            final Integer vanafPartijId = jdbcTemplate.queryForObject("SELECT id FROM partij WHERE code = 999999", Integer.class);

            jdbcTemplate.update("DELETE FROM actie WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM admhnd WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM authenticatiemiddel WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM autorisatiebesluit WHERE autoriseerder > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM autorisatiebesluitpartij WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM bijhautorisatie WHERE geautoriseerdepartij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM certificaat WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM convrnideelnemer WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM doc WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM gem WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM his_bijhautorisatie WHERE geautoriseerdepartij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM his_doc WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM his_partij WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM his_persbijhouding WHERE bijhpartij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM his_perspk WHERE gempk > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM partijipadres WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM partijonderzoek WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM partijrol WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM pers WHERE bijhpartij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM pers WHERE gempk > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM persafnemerindicatie WHERE afnemer > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM persverificatie WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM persverstrbeperking WHERE gemverordening > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM persverstrbeperking WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM stapelvoorkomen WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM terugmelding WHERE bijhgem > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM terugmelding WHERE terugmeldendepartij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM toegangabonnement WHERE intermediair > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM toegangabonnement WHERE partij > ?", vanafPartijId);
            jdbcTemplate.update("DELETE FROM uitgeslotene WHERE uitgeslotenpartij > ?", vanafPartijId);

            jdbcTemplate.update("DELETE FROM partij WHERE id > ?", vanafPartijId);
        }

    }
}
