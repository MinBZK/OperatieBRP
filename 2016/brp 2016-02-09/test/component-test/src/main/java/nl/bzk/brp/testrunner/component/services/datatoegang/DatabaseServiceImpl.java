/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.datatoegang;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final DatabaseVerzoekUitvoerder uitvoerder = new DatabaseVerzoekUitvoerder() {
        @Override
        public void voerUit(final VerzoekMetJdbcTemplate verzoek) {
            verzoek.voerUitMet(new JdbcTemplate(lezenSchrijvenDataSource));
        }

        @Override
        public void voerUit(final VerzoekMetEntityManager verzoek) {
            verzoek.voerUitMet(entityManagerLezenEnSchrijven);
        }
    };

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager entityManagerLezenEnSchrijven;

    @Inject
    @Named("lezenSchrijvenDataSource")
    private DataSource lezenSchrijvenDataSource;

    @Override
    public void voerUit(final DatabaseVerzoek verzoek) {
        verzoek.voerUit(uitvoerder);
    }

    @Override
    @Transactional
    public void voerUitMetTransactie(final DatabaseVerzoek verzoek) {
        verzoek.voerUit(uitvoerder);
    }


}
