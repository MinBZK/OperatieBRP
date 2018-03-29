/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ZoekPersoonRepository.
 */
@Repository
public final class GeefDetailsPersoonJpaRepositoryImpl implements GeefDetailsPersoonRepository {

    @Inject
    private DataSource masterDataSource;

    private GeefDetailsPersoonJpaRepositoryImpl() {
    }

    @Override
    public List<Long> zoekIdsPersoonMetBsn(final String bsn) {
        final String sql = "SELECT id FROM kern.pers persoon WHERE persoon.bsn = :bsn AND persoon.srt = 1";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>(1, 1);
        parameters.put("bsn", bsn);
        return jdbcTemplate.queryForList(sql, parameters, Long.class);
    }

    @Override
    public List<Long> zoekIdsPersoonMetAnummer(final String anr) {
        final String sql = "SELECT id FROM kern.pers persoon WHERE persoon.anr = :anr AND persoon.srt = 1";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>(1, 1);
        parameters.put("anr", anr);
        return jdbcTemplate.queryForList(sql, parameters, Long.class);
    }
}
